package com.tiennv.ec;

import java.math.BigInteger;
import java.util.List;

/**
 * An elliptic curve over a prime field is a set of points (x,y) on the curve defined by
 * the equation y^2 ≡ x^3 + ax + b (mod p), where x, y, a, and b are elements of GF(p) for some prime p ≠ 3.
 * The points (x,y) along with point at infinity O form an Abelian group
 * with point addition operator + if 4a^3+27b^2 ≠ 0.
 */
public final class EllipticCurve {

    public static final BigInteger TWO = BigInteger.valueOf(2);
    public static final BigInteger THREE = BigInteger.valueOf(3);

    private final BigInteger p;
    private final BigInteger a;
    private final BigInteger b;

    public EllipticCurve(final BigInteger p, final BigInteger a, final BigInteger b) {

        BigInteger delta = BigInteger.valueOf(4).multiply(a.pow(3)).add(BigInteger.valueOf(27).multiply(b.pow(2))).mod(p);
        assert delta.compareTo(BigInteger.ZERO) != 0  : "invalid elliptic curve";

        this.p = p;
        this.a = a;
        this.b = b;
    }

    public Point inverse(Point p) {
        return new Point(p.getAffineX(), p.getAffineY().negate());
    }

    /**
     * If there is a point P = (xp,yp) with yp ≠ 0 of an elliptic curve modulo the prime p,
     * then point R on the elliptic curve, i.e. R = 2P has the following coordinates xr = s^2-2xp (mod p)
     * and yr = -yp+s(xp-xr) (mod p) where s = (3xp^2+a)/(2yp) (mod p).
     * s = (3xp^2+a)/(2yp) (mod p) is equivalent to s = (3xp^2+a)*((2yp)^-1) (mod p)
     *
     * If yp = 0, then 2P = O.
     *
     * @param r
     * @return 2r
     */
    public Point doubling(Point r) {

        if (r.isInfinity()) {
            return Point.POINT_INFINITY;
        }

        BigInteger yr = r.getAffineY();

        if (yr.compareTo(BigInteger.ZERO) == 0) {
            return Point.POINT_INFINITY;
        }

        BigInteger xr = r.getAffineX();
        BigInteger yrr = THREE.multiply(xr.pow(2)).add(a).mod(p);
        BigInteger xrr = TWO.multiply(yr).modInverse(p);
        BigInteger s = yrr.multiply(xrr).mod(p);

        BigInteger x2r;
        BigInteger y2r;

        x2r = s.pow(2).subtract(BigInteger.valueOf(2).multiply(xr)).mod(p);
        y2r = yr.negate().add(s.multiply(xr.subtract(x2r))).mod(p);

        return new Point(x2r, y2r);
    }

    /**
     * P + Infinity = P
     *
     * P + (-P) = Infinity
     *
     * If there are two distinct points P(xp,yp) and Q(xq,yq) on the curve such that P is not –Q,
     * then R = (xr,yr), where s = (yp-yq)/(xp-xq) (mod p), xr = s^2-xp-xq (mod p),
     * and yr = -yp + s(xp-xR) (mod p).
     * s = (yp-yq)/(xp-xq) is equivalent to s = (yp-yq)*((xp-xq)^-1).
     *
     * @param p1
     * @param p2
     * @return Point
     */
    public Point add(Point p1, Point p2) {

        if (p1.isInfinity()) {
            return p2;
        }

        if (p2.isInfinity()) {
            return p1;
        }

        if (p1.equals(this.inverse(p2))) {
            return Point.POINT_INFINITY;
        }

        if (p1.equals(p2)) {
            return doubling(p1);
        }

        BigInteger xr = null;
        BigInteger yr = null;

        Point invOfp2 = this.inverse(p2);
        if (p1 != invOfp2 ) {

            //yp-yq mod p
            BigInteger yp = p1.getAffineY();
            BigInteger yq = p2.getAffineY();
            BigInteger ypq = yp.subtract(yq).mod(p);

            //((xp-xq)^-1) mod p
            BigInteger xp = p1.getAffineX();
            BigInteger xq = p2.getAffineX();
            BigInteger xpq = xp.subtract(xq).modInverse(p);

            BigInteger s = ypq.multiply(xpq).mod(p);

            xr = s.pow(2).subtract(xp).subtract(xq).mod(p);
            yr = yp.negate().add(s.multiply(xp.subtract(xr))).mod(p);
        }

        return new Point(xr, yr);
    }

    public Point scalarMultiply(BigInteger k, Point r) {
        return doubleAndadd(k, r);
    }

    /**
     * given k=bn-1||bn-2||...||b0 the binary representation of k
     *
     * Input: P, k=bn-1||bn-2||...||b0
     *
     * Output: Q = kP
     *    Q=0
     *    For i from 0 to n-1
     *        Q=2Q
     *        If bi=1 then Q=Q+P
     *    Return Q
     *
     * @param k
     * @param r
     * @return
     */
    public Point doubleAndadd(BigInteger k, final Point r) {

        Point q = Point.POINT_INFINITY;

        /**
         * Explanation (in bits):
         *
         * n
         * 100010101011101010 (example)
         * n >> 5
         * 000001000101010111 (all bits are moved over 5 spots, therefore
         * &                   the bit you want is at the end)
         * 000000000000000001 (0 means it will always be 0,
         * =                   1 means that it will keep the old value)
         * 1
         */
        int size = k.bitLength();
        for (int i = 0; i <= size-1; i++) {
            BigInteger bit = k.shiftRight(i).and(BigInteger.ONE);
            q = doubling(q);
            if (bit.compareTo(BigInteger.ONE) == 0) {
                q = add(q, r);
            }
        }

        return q;
    }


    /**
     * INPUT: Positive integer k, P ∈ E(Fq).
     * OUTPUT: k ⋅ P.
     *        Based on previous algorithm compute NAF(k) =∑(l−1)(i=0)ki⋅2i.
     *        Q←∞.
     *        For i from l−1 down to 0 do
     *              Q←2Q.
     *              If ki  = 1 then Q←Q+P.
     *              If ki  = −1 thenQ←Q−P.
     * Return(Q).
     *
     * @param k
     * @param r
     */
    public Point NAFMultiply(BigInteger k, Point r) {
        List<BigInteger> naf = Multiplication.NAF(k);
        int size = naf.size();
        Point q = Point.POINT_INFINITY;
        for (int i = size -1; i >= 0; i--) {
            q = doubling(q);
            if (naf.get(i).compareTo(BigInteger.ONE) == 0) {
                q = add(q, r);
            }

            if (naf.get(i).compareTo(BigInteger.valueOf(-1)) == 0) {
                q = add(q, inverse(r));
            }
        }

        return q;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getA() {
        return this.a;
    }

    public BigInteger getB() {
        return this.b;
    }

    @Override
    public String toString() {
        return "EllipticCurve{" +
                "p=" + p +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
