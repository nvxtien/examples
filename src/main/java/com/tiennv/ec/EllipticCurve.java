package com.tiennv.ec;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * An elliptic curve over a prime field is a set of points (x,y) on the curve defined by
 * the equation y^2 ≡ x^3 + ax + b (mod p), where x, y, a, and b are elements of GF(p) for some prime p ≠ 3.
 * The points (x,y) along with point at infinity O form an Abelian group
 * with point addition operator + if 4a^3+27b^2 ≠ 0.
 */
public final class EllipticCurve {

    public static final BigInteger TWO = BigInteger.valueOf(2);

    private final BigInteger p;
    private final BigInteger a;
    private final BigInteger b;
//    private final BigInteger lambda;

    public EllipticCurve(final BigInteger p, final BigInteger a, final BigInteger b) {

        BigInteger delta = BigInteger.valueOf(4).multiply(a.pow(3)).add(BigInteger.valueOf(27).multiply(b.pow(2))).mod(p);
        assert delta.compareTo(BigInteger.ZERO) != 0  : "invalid elliptic curve";

        this.p = p;
        this.a = a;
        this.b = b;
    }

    public Point inverse(Point p) {
        return new Point(p.getX(), p.getY().negate());
    }

    /**
     * If there is a point P = (xp,yp) with yp ≠ 0 of an elliptic curve modulo the prime p,
     * then point R on the elliptic curve, i.e. R = 2P has the following coordinates xr = s^2-2xp (mod p)
     * and yr = -yp+s(xp-xr) (mod p) where s = (3xp^2+a)/(2yp) (mod p).
     * s = (3xp^2+a)/(2yp) (mod p) is equivalent to s = (3xp^2+a)*((2yp)^-1) (mod p)
     *
     * If yp = 0, then 2P = O.
     *
     * @param p
     * @return
     */
    public Point doubling(Point p) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (p.getY().compareTo(BigInteger.ZERO) == 0) {
            return new Point(null, null);
        }

        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("EC","SunEC");
        ECGenParameterSpec ecsp;

        ecsp = new ECGenParameterSpec("secp192k1");
        kpg.initialize(ecsp);

        KeyPair kpU = kpg.genKeyPair();
        PrivateKey privKeyU = kpU.getPrivate();
        PublicKey pubKeyU = kpU.getPublic();
        System.out.println("User U: " + privKeyU.toString());
        System.out.println("User U: " + pubKeyU.toString());
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
            return new Point(null, null);
        }

        BigInteger xr = null;
        BigInteger yr = null;

        Point invOfp2 = this.inverse(p2);
        if (p1 != invOfp2 ) {
            //yp-yq mod p
            BigInteger ypq = p1.getY().subtract(p2.getY()).mod(p);
            //((xp-xq)^-1) mod p
            BigInteger xpq = p1.getX().subtract(p2.getX()).modInverse(p);
            BigInteger s = ypq.multiply(xpq);
//            System.out.println(s);
            xr = s.pow(2).subtract(p1.getX()).subtract(p2.getX()).mod(p);
            yr = p1.getY().negate().add(s.multiply(p1.getX().subtract(xr))).mod(p);
        }

        return new Point(xr, yr);
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
