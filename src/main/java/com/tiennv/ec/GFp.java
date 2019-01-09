package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

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
public class GFp {
    private BigInteger x;

    /**
     * @param x
     */
    public GFp(final BigInteger x) {
        this.x = x.mod(Fp256BN.p);
    }

    /**
     * @param that
     * @return GFp
     */
    public GFp multiply(final GFp that) {
        return new GFp(this.getValue().multiply(that.getValue()));
    }

    /**
     * https://eprint.iacr.org/2010/354.pdf
     * Algorithm 7
     * @param b
     * @return
     */
    public GFp multiplyScalar(final BigInteger b) {
        return new GFp(this.getValue().multiply(b));
    }

    /**
     * @return GFp
     */
    public GFp square() {
        return new GFp(this.getValue().multiply(this.getValue()));
    }

    /**
     * @return GFp
     */
    public GFp inverse() {
        return new GFp(this.getValue().modInverse(Fp256BN.p));
    }

    public GFp add(final GFp that) {
        return new GFp(this.getValue().add(that.getValue()));
    }

    public GFp subtract(final GFp that) {
        return new GFp(this.getValue().subtract(that.getValue()));
    }

    public GFp negate() {
        return new GFp(this.getValue().negate());
    }

    public BigInteger getValue() {
        return this.x;
    }

    public void setOne() {
        this.setX(BigInteger.ONE);
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public void setZero() {
        this.setX(BigInteger.ZERO);
    }

    @Override
    public String toString() {
        return "GFp{" +
                "value=" + x +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GFp gFp = (GFp) o;
        return x.equals(gFp.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    public void print() {
        System.out.println(toString());
    }
}
