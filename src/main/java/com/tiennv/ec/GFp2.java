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
 */
public class GFp2 {
    private final BigInteger x;
    private final BigInteger y;
    private static final BigInteger beta = BigInteger.valueOf(-1); // why?

    public GFp2(final BigInteger x, final BigInteger y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The Karatsuba method for computing the product c = ab ∈ Fp2 proceeds by
     * precomputing v0 = a0b0, v1 = a1b1 and then
     * c0 = v0 + βv1
     * c1 = (a0 + a1)(b0 + b1) − v0 − v1.
     *
     * @param that
     * @return
     */
    public GFp2 multiply(final GFp2 that) {
        BigInteger v0 = this.x.multiply(that.x);
        BigInteger v1 = this.y.multiply(that.y);
        BigInteger c0 = v0.add(beta.multiply(v1));
        BigInteger c1 = this.x.add(this.y).multiply(that.x.multiply(that.y)).subtract(v0).subtract(v1);
        return new GFp2(c0, c1);
    }
}
