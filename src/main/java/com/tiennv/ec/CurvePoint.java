package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

import static com.tiennv.ec.Constants.*;

public class CurvePoint {

    public static final CurvePoint POINT_INFINITY = new CurvePoint();
    public static final CurvePoint GENERATOR = new CurvePoint(GFp.ONE, new GFp(BigInteger.valueOf(2)), GFp.ONE);


    private GFp x, y, z;
//    private EllipticCurve curve;

    // E/Fp: Y^2 = X^3 + aXZ^4 + bZ^6

    private CurvePoint() {
//        this.curve = null;
        this.x = GFp.ZERO;
        this.y = GFp.ONE;
        this.z = GFp.ZERO;
    }

    public CurvePoint(EllipticCurve curve, GFp x, GFp y, GFp z) {
//        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CurvePoint(GFp x, GFp y, GFp z) {
//        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CurvePoint fromAffine(EllipticCurve curve, GFp ax, GFp ay) {
//        this.curve = curve;
        this.x = ax;
        this.y = ay;
        this.z = GFp.ONE;
        return this;
    }

    /*public void transformAffine() {
        if (this.isInfinity()) {
            setInfinity();
        }
        GFp invz = this.z.inverse();
        GFp invz2 = invz.square();
        GFp invz3 = invz.multiply(invz2);

        this.x = this.x.multiply(invz2);
        this.y = this.y.multiply(invz3);
        this.z = GFp.ONE;


//        return Point.newPoint(curve, this.x.multiply(invz2).getValue(), this.y.multiply(invz3).getValue());
    }*/

    public CurvePoint negate() {
        return new CurvePoint(this.x, this.y.negate(), this.z);
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
    public CurvePoint add(CurvePoint that) {

        if (that.isInfinity()) {
            return this;
        }

        if (this.isInfinity()) {
            return that;
        }

        if (this.equals(that.negate())) {
            return CurvePoint.POINT_INFINITY;
        }

        if (this.equals(that)) {
            return this.doubling();
        }

        BigInteger p = Fp256BN.p;

        GFp z1z1 = this.z.square();
        GFp z2z2 = that.getZ().square();

        GFp u1 = this.x.multiply(z2z2);
        GFp u2 = that.x.multiply(z1z1);

        GFp s1 = this.y.multiply(that.z).multiply(z2z2);
        GFp s2 = that.y.multiply(this.z).multiply(z1z1);

        GFp h = u2.subtract(u1);
        GFp i = h.square().multiplyScalar(BigInteger.valueOf(2));
        GFp j = h.multiply(i);

        GFp r = s2.subtract(s1).multiplyScalar(BigInteger.valueOf(2));
        GFp v = u1.multiply(i);

        GFp x3 = r.square().subtract(j).subtract(v.multiplyScalar(BigInteger.valueOf(2)));
        GFp y3 = r.multiply(v.subtract(x3)).subtract(s1.multiply(j).multiplyScalar(BigInteger.valueOf(2)));
        GFp z3 = h.multiply(this.z.add(that.z).square().subtract(z1z1).subtract(z2z2));

        return new CurvePoint(x3, y3, z3);
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
    public CurvePoint doubling2() {

//        BigInteger p = this.curve.getP();

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

        GFp A = this.x.square();
        GFp B = this.y.square();
        GFp C = B.square();

        GFp t0 = this.x.add(B);
        GFp t1 = t0.square();// t0^2
        GFp t2 = t1.subtract(A);// t1-A
        GFp t3 = t2.subtract(C);// t2-C

        GFp D = t3.multiplyScalar(TWO);//2*t3

        GFp E = A.multiplyScalar(THREE);//3*A
        GFp F = E.square();//E^2
        GFp t4 = D.multiplyScalar(TWO);//2*D

        GFp X3 = F.subtract(t4);// F-t4
        GFp t5 = D.subtract(X3);// D-X3

        GFp t6 = C.multiplyScalar(EIGHT);// 8*C

        GFp t7 = E.multiply(t5);// E*t5
        GFp Y3 = t7.subtract(t6);// t7-t6
        GFp t8 = this.y.multiply(this.z);// Y1*Z1

        GFp Z3 = t8.multiplyScalar(TWO);// 2*t8

        GFp x = X3;
        GFp y = Y3;
        GFp z = Z3;

        return new CurvePoint(x, y, z);
    }

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2007-bl
     *
     * @return
     */
    public CurvePoint doubling() {

        if (this.isInfinity()) {
            return this;
        }

//        BigInteger p = Fp256BN.p;
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
        GFp y1 = this.y;
        GFp z1 = this.z;

        GFp XX = this.x.square();
        GFp YY = y1.square();
        GFp YYYY = YY.square();
        GFp ZZ = this.z.square();
        GFp S = this.x.add(YY).square().subtract(XX).subtract(YYYY).multiplyScalar(TWO);
        GFp M = XX.multiplyScalar(THREE).add(ZZ.square().multiplyScalar(Fp256BN.a));
        GFp T = M.square().subtract(S.multiplyScalar(TWO));
        GFp X3 = T;

        GFp Y3 = M.multiply(S.subtract(T)).subtract(YYYY.multiplyScalar(EIGHT));

        GFp Z3 = y1.add(z1).square().subtract(YY).subtract(ZZ);

        GFp x = X3;
        GFp y = Y3;
        GFp z = Z3;

        return new CurvePoint(x, y, z);
    }

    /**
     * https://eprint.iacr.org/2011/338.pdf
     * Algorithm 3 Montgomery ladder
     *
     * @param k
     * @return
     */
    public CurvePoint scalarMultiply(BigInteger k) {
        CurvePoint r0 = CurvePoint.POINT_INFINITY;

        CurvePoint r1 = this;
        r1.print();

        int n = k.bitLength();

        for (int i=n-1; i>=0; i--) {
            BigInteger b = k.shiftRight(i).and(BigInteger.ONE);
            if (b.equals(BigInteger.ONE)) {
                r0 = r0.add(r1);
                r1 = r1.doubling();
            } else {
                r1 = r1.add(r0);
                r0 = r0.doubling();
            }
        }

//        r0.transformAffine();

//        BigInteger p = this.curve.getP();
//        return new CurvePoint(r0.getX(), r0.getY(), r0.getZ());
        return r0;
    }

    public boolean isInfinity() {
        return this.z.equals(BigInteger.ZERO);
    }

    public GFp getX() {
        return x;
    }

    public GFp getY() {
        return y;
    }

    public GFp getZ() {
        return z;
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Jacobian{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurvePoint jacobian = (CurvePoint) o;
        return x.equals(jacobian.x) &&
                y.equals(jacobian.y) &&
                z.equals(jacobian.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public void setInfinity() {
//        this.curve = null;
        this.x = GFp.ZERO;
        this.y = GFp.ONE;
        this.z = GFp.ZERO;
//        return new CurvePoint();
    }

    public void transformAffine() {
        if (this.isInfinity()) {
            setInfinity();
        }
        GFp invz = this.z.inverse();
        this.y = this.y.multiply(invz);

        GFp invz2 = invz.square();
        this.y = this.y.multiply(invz2);
        this.x = this.x.multiply(invz2);
        this.z = GFp.ONE;
    }
}
