package com.tiennv.ec;

import java.math.BigInteger;

/**
 * Fp2 = Fp[X]/(X^2 − β), where β is a quadratic non-residue in Fp.(β = −5)
 * An element A = a0 + a1X ∈ Fp2
 * B = b0 + b1X ∈ Fp2
 *
 * BN128 is Barreto-Naehrig pairing friendly curve providing 128 bits of security defined as: y^2=x^3+b
 * over the prime field 𝔽q where:
 * q = 21888242871839275222246405745257275088696311157297823662689037894645226208583
 * r = 21888242871839275222246405745257275088548364400416034343698204186575808495617
 * b = 3
 *
 * This curve choice admits a pairing construction over the two groups 𝔾1 and 𝔾2 where
 * 𝔾1 is a Barreto-Naehrig curve over 𝔽q
 * 𝔾2 is a the subgroup of order r in the sextic twist of 𝔾2 over 𝔽q2 with equation y^2=x^3+b/ζ
 * 𝔾T is the subgroup of the rth roots of unity in 𝔽q12
 *
 * With the bilinear pairing e with:
 * e:𝔾1×𝔾2→𝔽q12
 *
 * https://eprint.iacr.org/2006/471.pdf
 */
public class GFp2 {
    private final BigInteger x;
    private final BigInteger y;
    private static final BigInteger beta = BigInteger.valueOf(-1); // why?

    /**
     * We construct a quadratic extension as Fp2 = Fp[X]/(X^2 − β), where β is a
     * quadratic non-residue in Fp. An element α ∈ Fp2 is represented as α1X + α0,
     * where αi ∈ Fp.
     *
     * @param x
     * @param y
     */
    public GFp2(final BigInteger x, final BigInteger y) {
        this.x = x.mod(BN256.p);
        this.y = y.mod(BN256.p);
    }

    /**
     * The Karatsuba method for computing the product c = ab ∈ Fp2 proceeds by
     * precomputing v0 = a0b0, v1 = a1b1 and then
     * c0 = v0 + βv1 // a0b0 - a1b1
     * c1 = (a0 + a1)(b0 + b1) − v0 − v1.
     *
     * https://eprint.iacr.org/2006/471.pdf
     * Section 3
     *
     * @param that
     * @return GFp2
     */
    public GFp2 multiply(final GFp2 that) {
        BigInteger v0 = this.y.multiply(that.y);
        BigInteger v1 = this.x.multiply(that.x);
        BigInteger c0 = v0.add(beta.multiply(v1));
        BigInteger c1 = this.x.add(this.y).multiply(that.x.multiply(that.y)).subtract(v0).subtract(v1);
        return new GFp2(c1, c0);
    }

    /**
     * There is a well-known squaring formula for complex arithmetic
     * that computes the square c0 + c1i = (a0 + a1i)^2 as
     * c0 = (a0 + a1)(a0 − a1)
     * c1 = 2a0a1.
     *
     * This is actually a special case of a squaring formula that we refer to as complex
     * squaring. We precompute v0 = a0a1, and then the square c = a^2
     * is computed as
     * c0 = (a0 + a1)(a0 + βa1) − v0 − βv0
     * c1 = 2v0
     *
     * https://eprint.iacr.org/2006/471.pdf
     * Section 3
     *
     * @return GFp2
     */
    public GFp2 square() {
        BigInteger v0 = this.y.multiply(this.x);
        BigInteger c0 = this.y.add(this.x).multiply(this.y.subtract(this.x));
        BigInteger c1 = v0.multiply(BigInteger.valueOf(2));
        return new GFp2(c1, c0);
    }

    /**
     * Algorithm 8 Inverse in Fp2 = Fp[u]/(u^2 − β)
     *
     * Require: A = a0 + a1u ∈ Fp2
     * Ensure: C = c0 + c1u = A^−1 ∈ Fp2
     * 1. t0 ← a0^2;
     * 2. t1 ← a1^2;
     * 3. t0 ← t0 − β · t1;
     * 4. t1 ← t0^−1;
     * 5. c0 ← a0 · t1;
     * 6. c1 ← −1 · a1 · t1;
     * 7. return C = c0 + c1u;
     *
     *  https://eprint.iacr.org/2010/354.pdf
     *
     * @return
     */
    public GFp2 inverse() {
        BigInteger t0 = this.y.pow(2);
        BigInteger t1 = this.x.pow(2);
        t0 = t0.subtract(t1);
        t1 = t0.mod(BN256.p);
        BigInteger c0 = this.y.multiply(t1);
        BigInteger c1 = this.x.multiply(t1).negate();
        return new GFp2(c1, c0);
    }

    public GFp2 add(final GFp2 that) {
        return new GFp2(this.x.add(that.x), this.y.add(that.y));
    }

    public GFp2 subtract(final GFp2 that) {
        return new GFp2(this.x.subtract(that.x), this.y.subtract(that.y));
    }

    /**
     * ξ = i + 3
     * aξ = (xi+y)(i+3) = (3x+y)i+(3y-x)
     * @return
     */
    public GFp2 multiplyXi() {
        return new GFp2(BigInteger.valueOf(3).multiply(this.x).add(this.y), BigInteger.valueOf(3).multiply(this.y).subtract(this.x));
    }
}
