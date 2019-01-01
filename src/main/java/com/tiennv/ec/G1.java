package com.tiennv.ec;

import java.math.BigInteger;

/**
 * The order of a point P is defined
 * to be the smallest integer n such that [n]P = ∞. We let E(Fp)[n] be the
 * subgroup of E(Fp) consisting of points of order n
 */
public class G1 {

    private final EllipticCurve curve  = new EllipticCurve(Fp256BN.p, Fp256BN.a, Fp256BN.b);

    private BigInteger x, y, z;
    private Jacobian r;

    public G1(final BigInteger x, final BigInteger y, final BigInteger z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = new Jacobian(curve, this.x, this.y, this.z);
    }

    public G1 add(final G1 g) {
        Jacobian s = new Jacobian(curve, g.getX(), g.getY(), g.getZ());
        Jacobian t = r.add(s);
        return new G1(t.getX(), t.getY(), t.getZ());
    }

    public G1 subtract(final G1 g) {
        return this.add(g.negate());
    }

    public G1 doubling() {
        Jacobian t = r.doubling();
        return new G1(t.getX(), t.getY(), t.getZ());
    }

    public G1 multiply(final BigInteger k) {
        Jacobian t = r.scalarMultiply(k);
        return new G1(t.getX(), t.getY(), t.getZ());
    }

    public G1 negate() {
        return new G1(this.getX(), this.getY().negate(), this.getZ());
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
}
