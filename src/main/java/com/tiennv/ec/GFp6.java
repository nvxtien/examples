package com.tiennv.ec;

/**
 * Cubic over quadratic
 * Multiplication and Squaring on Pairing-Friendly Fields
 * Section 6.2
 * https://eprint.iacr.org/2006/471.pdf
 *
 * Fp2 = Fp[X]/(X^2 − β)
 * Fp6 = Fp2 [Y]/(Y^3 − γ)
 * γ = √β (β=γ^2)
 *
 * ξ = i + 3
 * Fp6 = Fp2(τ) with τ^3 = ξ
 * https://cryptojedi.org/papers/dclxvi-20100714.pdf
 * Section 3.1
 *
 * Fp6 = Fp2[Y]/(Y^3 − ξ)
 * a = a0 + a1Y + a2Y^2
 * ai ∈ Fp2
 */
public class GFp6 {
    private final GFp2 x, y, z;

    public GFp6(GFp2 x, GFp2 y, GFp2 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     * The Karatsuba method for computing the product c = ab
     *
     * https://eprint.iacr.org/2006/471.pdf
     * Section 4
     *
     * @param a
     * @param b
     * @return
     */
    public GFp6 multiply(final GFp6 a, final GFp6 b) {
        // precomputing the values v0 = a0b0, v1 = a1b1, v2 = a2b2
        GFp2 a0 = a.getZ();
        GFp2 a1 = a.getY();
        GFp2 a2 = a.getX();

        GFp2 b0 = b.getZ();
        GFp2 b1 = b.getY();
        GFp2 b2 = b.getX();

        GFp2 v0 = a0.multiply(b0);
        GFp2 v1 = a1.multiply(a1);
        GFp2 v2 = a2.multiply(b2);

        // c0 = v0 + ξ((a1 + a2)(b1 + b2) − v1 − v2)
        GFp2 c0 = v0.add(a1.add(a2).multiply(b1.add(b2)).subtract(v1).subtract(v2).multiplyXi());

        // c1 = (a0 + a1)(b0 + b1) − v0 − v1 + ξv2
        GFp2 c1 = a0.add(a1).multiply(b0.add(b1)).subtract(v0).subtract(v1).add(v2.multiplyXi());

        // c2 = (a0 + a2)(b0 + b2) − v0 + v1 − v2
        GFp2 c2 = a0.add(a2).multiply(b0.add(b2)).subtract(v0).add(v1).subtract(v2);

        return new GFp6(c2, c1, c0);
    }

    /**
     * Adapting the
     * Karatsuba formula to compute the square c = a^2
     * @return
     */
    public GFp6 square() {
        GFp2 a0 = this.getZ();
        GFp2 a1 = this.getY();
        GFp2 a2 = this.getX();

        GFp2 v0 = a0.square();
        GFp2 v1 = a1.square();
        GFp2 v2 = a2.square();

        // c0 = v0 + ξ((a1 + a2)^2 − v1 − v2)
        GFp2 c0 = v0.add(a1.add(a2).square().subtract(v1).subtract(v2).multiplyXi());

        // c1 = (a0 + a1)^2 − v0 − v1 + ξv2
        GFp2 c1 = a0.add(a1).square().subtract(v0).subtract(v1).add(v2.multiplyXi());

        // c2 = (a0 + a2)^2 − v0 + v1 − v2
        GFp2 c2 = a0.add(a2).square().subtract(v0).add(v1).subtract(v2);

        return new GFp6(c2, c1, c0);
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
}
