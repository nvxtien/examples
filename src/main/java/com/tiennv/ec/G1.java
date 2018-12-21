package com.tiennv.ec;

import java.math.BigInteger;

/**
 * The order of a point P is defined
 * to be the smallest integer n such that [n]P = ∞. We let E(Fp)[n] be the
 * subgroup of E(Fp) consisting of points of order n
 */
public class G1 {

    private final EllipticCurve curve;
    private final BigInteger n;

    private BigInteger x, y;

    public G1(final EllipticCurve curve, final BigInteger n) {
        this.curve = curve;
        this.n = n;
    }

    public G1 newPoint(final BigInteger x, final BigInteger y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public G1 add(final G1 g) {
        return this;
    }

    public G1 subtract(final G1 g) {
        return this.add(g.inverse());
    }

    public G1 doubling() {
        return this;
    }

    public G1 multiply(final BigInteger k) {
        return this;
    }

    public G1 inverse() {
        return this;
    }
}
