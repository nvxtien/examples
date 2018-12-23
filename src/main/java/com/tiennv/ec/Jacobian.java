package com.tiennv.ec;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.*;

public class Jacobian {

    private BigInteger x, y, z;
    private EllipticCurve curve;

    // E/Fp: Y^2 = X^3 + aXZ^4 + bZ^6

    public Jacobian(EllipticCurve curve, BigInteger x, BigInteger y, BigInteger z) {
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Jacobian fromAffine(EllipticCurve curve, BigInteger ax, BigInteger ay) {
        this.curve = curve;
        this.x = ax;
        this.y = ay;
        this.z = BigInteger.ONE;
        return this;
    }

    public Point toAffine() {
        BigInteger invz = this.z.modInverse(this.curve.getP());
        BigInteger invz2 = invz.modPow(BigInteger.valueOf(2), this.curve.getP());
        BigInteger invz3 = invz.multiply(invz2).mod(this.curve.getP());

        return Point.newPoint(curve, this.x.multiply(invz2).mod(this.curve.getP()), this.y.multiply(invz3).mod(this.curve.getP()));
    }

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian.html#addition-add-2007-bl
     *
     * Z1Z1 = Z1^2
     * Z2Z2 = Z2^2
     * U1 = X1*Z2Z2
     * U2 = X2*Z1Z1
     * S1 = Y1*Z2*Z2Z2
     * S2 = Y2*Z1*Z1Z1
     * H = U2-U1
     * I = (2*H)^2
     * J = H*I
     * r = 2*(S2-S1)
     * V = U1*I
     * X3 = r^2-J-2*V
     * Y3 = r*(V-X3)-2*S1*J
     * Z3 = ((Z1+Z2)^2-Z1Z1-Z2Z2)*H
     *
     */
    public Jacobian add(Jacobian that) {

        BigInteger z1z1 = this.z.pow(2);
        BigInteger z2z2 = that.getZ().pow(2);

        BigInteger u1 = this.x.multiply(z2z2);
        BigInteger u2 = that.x.multiply(z1z1);

        BigInteger s1 = this.y.multiply(that.z).multiply(z2z2);
        BigInteger s2 = that.y.multiply(this.z).multiply(z1z1);

        BigInteger h = u2.subtract(u1);
        BigInteger i = BigInteger.valueOf(2).multiply(h).pow(2);
        BigInteger j = h.multiply(i);

        BigInteger r = BigInteger.valueOf(2).multiply(s2.subtract(s1));
        BigInteger v = u1.multiply(i);

        BigInteger x3 = r.pow(2).subtract(j).subtract(BigInteger.valueOf(2).multiply(v));
        BigInteger y3 = r.multiply(v.subtract(x3)).subtract(BigInteger.valueOf(2).multiply(s1).multiply(j));
        BigInteger z3 = h.multiply(this.z.add(that.z).pow(2).subtract(z1z1).subtract(z2z2));

        return new Jacobian(this.curve, x3, y3, z3);
    }

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2009-l
     *
     * A = X1^2
     * B = Y1^2
     * C = B^2
     *
     * D = 2*((X1+B)^2-A-C)
     * E = 3*A
     * F = E^2
     *
     * X3 = F-2*D
     * Y3 = E*(D-X3)-8*C
     * Z3 = 2*Y1*Z1
     *
     * three-operand code
     *
     * A = X1^2
     * B = Y1^2
     * C = B^2
     * t0 = X1+B
     * t1 = t0^2
     * t2 = t1-A
     * t3 = t2-C
     * D = 2*t3
     * E = 3*A
     * F = E^2
     * t4 = 2*D
     * X3 = F-t4
     * t5 = D-X3
     * t6 = 8*C
     * t7 = E*t5
     * Y3 = t7-t6
     * t8 = Y1*Z1
     * Z3 = 2*t8
     *
     *
     * @return
     */
    public Jacobian doubling2() {

        BigInteger p = this.curve.getP();

        /*BigInteger a = this.x.pow(2);
        BigInteger b = this.y.pow(2);
        BigInteger c = b.pow(2);

        BigInteger d = TWO.multiply((this.x.add(b)).pow(2).subtract(a).subtract(c));
        BigInteger e = THREE.multiply(a);
        BigInteger f = e.pow(2);

        BigInteger X3 = f.subtract(TWO.multiply(d));
        BigInteger Y3 = (e.multiply(d.subtract(X3))).subtract(EIGHT.multiply(c));
        BigInteger Z3 = TWO.multiply(this.y).multiply(this.z);

        System.out.println(this.x);*/

        BigInteger A = this.x.pow(2);
        BigInteger B = this.y.pow(2);
        BigInteger C = B.pow(2);

        BigInteger t0 = this.x.add(B);
        BigInteger t1 = t0.pow(2);// t0^2
        BigInteger t2 = t1.subtract(A);// t1-A
        BigInteger t3 = t2.subtract(C);// t2-C
        BigInteger D = TWO.multiply(t3);//2*t3
        BigInteger E = THREE.multiply(A);//3*A
        BigInteger F = E.pow(2);//E^2
        BigInteger t4 = TWO.multiply(D);//2*D
        BigInteger X3 = F.subtract(t4);// F-t4
        BigInteger t5 = D.subtract(X3);// D-X3
        BigInteger t6 = EIGHT.multiply(C);// 8*C
        BigInteger t7 = E.multiply(t5);// E*t5
        BigInteger Y3 = t7.subtract(t6);// t7-t6
        BigInteger t8 = this.y.multiply(this.z);// Y1*Z1
        BigInteger Z3 = TWO.multiply(t8);// 2*t8

        BigInteger x = X3.mod(p);
        BigInteger y = Y3.mod(p);
        BigInteger z = Z3.mod(p);

        return new Jacobian(this.curve, x, y, z);
    }

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2007-bl
     *
     * @return
     */
    public Jacobian doubling() {

        BigInteger p = this.curve.getP();
        /*
        // Efficient elliptic curve exponentiation
        //https://pdfs.semanticscholar.org/9d61/ae363a68fc3403173e29e333388f8c0fe0b5.pdf

        BigInteger a = this.x.pow(2);
        BigInteger b = this.y.pow(2);

        BigInteger S = FOUR.multiply(this.x).multiply(b).mod(p);


        BigInteger M = THREE.multiply(a).add(this.curve.getA().multiply(this.z.pow(4))).mod(p);
        BigInteger T = TWO.negate().multiply(S).add(M.pow(2)).mod(p);


        BigInteger x3 = T;
        System.out.println("x3: " + x3);


        BigInteger y3 = M.multiply(S.subtract(T)).subtract(EIGHT.multiply(b.pow(2))).mod(p);
        System.out.println("y3: " + y3);


        BigInteger z3 = TWO.multiply(this.y).multiply(this.z).mod(p);
        System.out.println("z3: " + z3);*/
        BigInteger y1 = this.y;
        BigInteger z1 = this.z;

        BigInteger XX = this.x.pow(2);
        BigInteger YY = y1.pow(2);
        BigInteger YYYY = YY.pow(2);
        BigInteger ZZ = this.z.pow(2);
        BigInteger S = TWO.multiply(this.x.add(YY).pow(2).subtract(XX).subtract(YYYY));
        BigInteger M = THREE.multiply(XX).add(this.curve.getA().multiply(ZZ.pow(2)));
        BigInteger T = M.pow(2).subtract(TWO.multiply(S));
        BigInteger X3 = T;

        BigInteger Y3 = M.multiply(S.subtract(T)).subtract(EIGHT.multiply(YYYY));
        BigInteger Z3 = y1.add(z1).pow(2).subtract(YY).subtract(ZZ);

        BigInteger x = X3.mod(p);
        BigInteger y = Y3.mod(p);
        BigInteger z = Z3.mod(p);

        return new Jacobian(this.curve, x, y, z);
    }

    /**
     * https://eprint.iacr.org/2011/338.pdf
     * Algorithm 3 Montgomery ladder
     *
     * @param k
     * @return
     */
    public Jacobian scalarMultiply(BigInteger k) {
        Jacobian r0 = new Jacobian(this.curve, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
        Jacobian r1 = this;
        int n = k.bitLength();
        for (int i=n-1; i>=0; i--) {
            BigInteger b = k.and(BigInteger.ONE.shiftRight(i));
            if (b.compareTo(BigInteger.ONE) == 0) {
                r0 = r0.add(r1);
                r1 = r1.doubling();
            } else {
                r1 = r1.add(r0);
                r0 = r0.doubling();
            }
        }
        return r0;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Jacobian{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", curve=" + curve +
                '}';
    }
}
