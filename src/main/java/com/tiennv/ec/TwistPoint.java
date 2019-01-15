package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

import static com.tiennv.ec.Constants.*;

public class TwistPoint {

    public static final TwistPoint POINT_INFINITY = new TwistPoint();
    /*public static final TwistPoint GENERATOR = new TwistPoint(
            new GFp2(new GFp(new BigInteger("14724174682622052940986165482626236747847319389687389512541395505603041913280")), new GFp(new BigInteger("37424606102024299561407771484254094594031112686590073402399442737767613131649"))),
            new GFp2(new GFp(new BigInteger("61038153163717895188228273712547317652324179401407967262561698487768888832017")), new GFp(new BigInteger("59551325687088189320634909215304067641249418107957154536830189592986703697967"))),
            new GFp2(new GFp(new BigInteger("0")), new GFp(new BigInteger("1"))));*/

    public static final TwistPoint GENERATOR = new TwistPoint(
            new GFp2(new GFp(new BigInteger("21167961636542580255011770066570541300993051739349375019639421053990175267184")), new GFp(new BigInteger("64746500191241794695844075326670126197795977525365406531717464316923369116492"))),
            new GFp2(new GFp(new BigInteger("20666913350058776956210519119118544732556678129809273996262322366050359951122")), new GFp(new BigInteger("17778617556404439934652658462602675281523610326338642107814333856843981424549"))),
            new GFp2(new GFp(new BigInteger("0")), new GFp(new BigInteger("1"))));

    private GFp2 x, y, z;

    private TwistPoint() {
        this.x = GFp2.ZERO;
        this.y = GFp2.ONE;
        this.z = GFp2.ZERO;
    }

    public TwistPoint(final GFp2 x, final GFp2 y, final GFp2 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TwistPoint negate() {
        return new TwistPoint(this.x, this.y.negate(), this.z);
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
    public TwistPoint add(TwistPoint that) {

        if (that.isInfinity()) {
            return this;
        }

        if (this.isInfinity()) {
            return that;
        }

        if (this.equals(that.negate())) {
            return TwistPoint.POINT_INFINITY;
        }

        if (this.equals(that)) {
            return this.doubling();
        }

        GFp2 z1z1 = this.z.square();
        GFp2 z2z2 = that.getZ().square();

        GFp2 u1 = this.x.multiply(z2z2);
        GFp2 u2 = that.x.multiply(z1z1);

        GFp2 s1 = this.y.multiply(that.z).multiply(z2z2);
        GFp2 s2 = that.y.multiply(this.z).multiply(z1z1);

        GFp2 h = u2.subtract(u1);
        GFp2 i = h.multiplyScalar(BigInteger.valueOf(2)).square();
        GFp2 j = h.multiply(i);

        GFp2 r = s2.subtract(s1).multiplyScalar(BigInteger.valueOf(2));
        GFp2 v = u1.multiply(i);

        GFp2 x3 = r.square().subtract(j).subtract(v.multiplyScalar(BigInteger.valueOf(2)));
        GFp2 y3 = r.multiply(v.subtract(x3)).subtract(s1.multiply(j).multiplyScalar(BigInteger.valueOf(2)));
        GFp2 z3 = h.multiply(this.z.add(that.z).square().subtract(z1z1).subtract(z2z2));

        return new TwistPoint(x3, y3, z3);
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
    /*public TwistPoint doubling2() {

        BigInteger p = this.curve.getP();

        *//*BigInteger a = this.x.pow(2);
        BigInteger b = this.y.pow(2);
        BigInteger c = b.pow(2);

        BigInteger d = TWO.multiply((this.x.add(b)).pow(2).subtract(a).subtract(c));
        BigInteger e = THREE.multiply(a);
        BigInteger f = e.pow(2);

        BigInteger X3 = f.subtract(TWO.multiply(d));
        BigInteger Y3 = (e.multiply(d.subtract(X3))).subtract(EIGHT.multiply(c));
        BigInteger Z3 = TWO.multiply(this.y).multiply(this.z);

        System.out.println(this.x);*//*

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

        return new TwistPoint(this.curve, x, y, z);
    }*/

    /**
     * https://hyperelliptic.org/EFD/g1p/auto-shortw-jacobian-0.html#doubling-dbl-2007-bl
     *
     * @return
     */
    public TwistPoint doubling() {

        if (this.isInfinity()) {
            return this;
        }

        GFp2 y1 = this.y;
        GFp2 z1 = this.z;

        GFp2 XX = this.x.square();
        GFp2 YY = y1.square();
        GFp2 YYYY = YY.square();
        GFp2 ZZ = this.z.square();
        GFp2 S = (this.x.add(YY).square().subtract(XX).subtract(YYYY)).multiplyScalar(TWO);

        GFp2 M = XX.multiplyScalar(THREE).add(ZZ.square().multiplyScalar(Fp256BN.a));
        GFp2 T = M.square().subtract(S.multiplyScalar(TWO));
        GFp2 X3 = T;

        GFp2 Y3 = M.multiply(S.subtract(T)).subtract(YYYY.multiplyScalar(EIGHT));
        GFp2 Z3 = y1.add(z1).square().subtract(YY).subtract(ZZ);

        GFp2 x = X3;
        GFp2 y = Y3;
        GFp2 z = Z3;

        return new TwistPoint(x, y, z);
    }

    /**
     * https://eprint.iacr.org/2011/338.pdf
     * Algorithm 3 Montgomery ladder
     *
     * @param k
     * @return
     */
    public TwistPoint scalarMultiply(BigInteger k) {
        TwistPoint r0 = TwistPoint.POINT_INFINITY;
        TwistPoint r1 = this;
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

        r0.transformAffine();

        return r0;
    }

    public boolean isInfinity() {
        return this.z.equals(GFp2.ZERO);
    }

    public boolean isOnCurve() {
        assert !this.isInfinity() : "The point must be on the elliptic curve";

        if (!this.z.equals(GFp2.ONE)) {
            transformAffine();
        }

        GFp2 x3 = this.x.square().multiply(this.x);
        GFp2 right = x3.add(TWIST_B);
        GFp2 left = this.y.square();

        return right.equals(left);

    }

    public void setInfinity() {
        this.x = GFp2.ZERO;
        this.y = GFp2.ONE;
        this.z = GFp2.ZERO;
    }

    public void transformAffine() {
        if (this.isInfinity()) {
            setInfinity();
            return;
        }
        GFp2 invz = this.z.inverse();
        this.y = this.y.multiply(invz);

        GFp2 invz2 = invz.square();
        this.y = this.y.multiply(invz2);
        this.x = this.x.multiply(invz2);
        this.z = GFp2.ONE;
    }

    public GFp2 getX() {
        return x;
    }

    public GFp2 getY() {
        return y;
    }

    public GFp2 getZ() {
        return z;
    }

    public void setX(GFp2 x) {
        this.x = x;
    }

    public void setY(GFp2 y) {
        this.y = y;
    }

    public void setZ(GFp2 z) {
        this.z = z;
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
        TwistPoint jacobian = (TwistPoint) o;
        return x.equals(jacobian.x) &&
                y.equals(jacobian.y) &&
                z.equals(jacobian.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
