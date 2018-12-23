package com.tiennv.ec;

import java.math.BigInteger;

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
     * http://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2009-l
     *
     * A = X1^2
     * B = Y1^2
     * C = B^2
     *
     * D = 2*((X1+B)^2-A-C)
     * E = 3*A
     * F = E^2
     * X3 = F-2*D
     * Y3 = E*(D-X3)-8*C
     * Z3 = 2*Y1*Z1
     *
     * @return
     */
    public Jacobian doubling() {

        BigInteger p = this.curve.getP();

        BigInteger a = this.x.pow(2);
        BigInteger b = this.y.pow(2);
        BigInteger c = b.pow(2);

        BigInteger d = BigInteger.valueOf(2).multiply( (this.x.add(b)).pow(2).subtract(a).subtract(c) );
        System.out.println("dddd: " + d);



        BigInteger e = BigInteger.valueOf(3).multiply(a);
        BigInteger f = e.pow(2);

        BigInteger x3 = f.subtract(BigInteger.valueOf(2).multiply(d)).mod(p);
        System.out.println("x3: " + x3);


        BigInteger y3 = ( e.multiply(d.subtract(x3)) ).subtract(BigInteger.valueOf(8).multiply(c)).mod(p);
        System.out.println("y3: " + y3);


        BigInteger z3 = BigInteger.valueOf(2).multiply(this.y).multiply(this.z).mod(p);
        return new Jacobian(this.curve, x3, y3, z3);
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
