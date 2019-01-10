package com.tiennv.ec;

import java.math.BigInteger;
import java.util.Objects;

/**
 * Fp2 = Fp[X]/(X^2 − β), where β is a quadratic non-residue in Fp.(β = −1)
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


    public static final GFp2 ONE = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ZERO));
    public static final GFp2 ZERO = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ONE));

    private static final BigInteger BETA = BigInteger.valueOf(-1);

    private GFp x, y; // xu + y


    /**
     * We construct a quadratic extension as Fp2 = Fp[X]/(X^2 − β), where β is a
     * quadratic non-residue in Fp. An element α ∈ Fp2 is represented as α1X + α0,
     * where αi ∈ Fp.
     *
     * @param x
     * @param y
     */
    public GFp2(final GFp x, final GFp y) {
        this.x = x;
        this.y = y;
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
        GFp v0 = this.y.multiply(that.y);
        GFp v1 = this.x.multiply(that.x);
        GFp c0 = v0.add(v1.multiplyScalar(BETA));
        // example: (0 + 1)(1 + 3) - 3 - 0 = 1
        GFp c1 = this.x.add(this.y).multiply(that.x.add(that.y)).subtract(v0).subtract(v1);

        return new GFp2(c1, c0);
    }

    /**
     * https://eprint.iacr.org/2010/354.pdf
     * Algorithm 7
     * @param b
     * @return
     */
    public GFp2 multiplyScalar(final BigInteger b) {
        GFp c0 = this.y.multiplyScalar(b);
        GFp c1 = this.x.multiplyScalar(b);
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
        GFp v0 = this.y.multiply(this.x);
        GFp c0 = this.y.add(this.x).multiply(this.y.subtract(this.x));
        GFp c1 = v0.multiplyScalar(BigInteger.valueOf(2));
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
        GFp t0 = this.y.square();
        GFp t1 = this.x.square();
        t0 = t0.subtract(t1.multiplyScalar(BETA));
        t1 = t0.inverse();
        GFp c0 = this.y.multiply(t1);
        GFp c1 = this.x.multiply(t1).negate();
        return new GFp2(c1, c0);
    }

    public GFp2 add(final GFp2 that) {
        return new GFp2(this.x.add(that.x), this.y.add(that.y));
    }

    public GFp2 subtract(final GFp2 that) {
        return new GFp2(this.x.subtract(that.x), this.y.subtract(that.y));
    }

    public GFp2 negate() {
        return new GFp2(this.x.negate(), this.y.negate());
    }

    /**
     * Faster Explicit Formulas for Computing Pairings over Ordinary Curves
     * Section 3
     * https://www.iacr.org/archive/eurocrypt2011/66320047/66320047.pdf
     *
     * ξ = i + 3
     * aξ = (xi+y)(i+3) = xi^2 + 3xi + yi + 3y = (3x+y)i + (3y-x)
     * @return
     */
    public GFp2 multiplyXi() {
        GFp a = this.x.multiplyScalar(BigInteger.valueOf(3)).add(this.y);
        GFp b = this.y.multiplyScalar(BigInteger.valueOf(3)).subtract(this.x);
        return new GFp2(a, b);
    }

    @Override
    public String toString() {
        return "GFp2{" +
                "x=" + x.getValue() +
                ", y=" + y.getValue() +
                '}';
    }

    public GFp2 exp(BigInteger k) {

        GFp2 r0 = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ONE));
        GFp2 r1 = this;

        int n = k.bitLength();
        for (int i=n-1; i>=0; i--) {
            BigInteger b = k.shiftRight(i).and(BigInteger.ONE);
            if (b.equals(BigInteger.ONE)) {
                r0 = r0.multiply(r1);
                r1 = r1.square();
            } else {
                r1 = r1.multiply(r0);
                r0 = r0.square();
            }
        }

        /*GFp2 r0 = this;
        GFp2 r1 = this.square();

        int n = k.bitLength();
        for (int i=n-2; i>=0; i--) {
            BigInteger b = k.shiftRight(i).and(BigInteger.ONE);
            if (b.equals(BigInteger.ONE)) {
                // x1=x1*x2; x2=x2^2
                r0 = r0.multiply(r1);
                r1 = r1.square();
            } else {
                // x2=x1*x2; x1=x1^2
                r1 = r1.multiply(r0);
                r0 = r0.square();
            }
        }*/

        return r0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GFp2 gFp2 = (GFp2) o;
        return x.equals(gFp2.x) &&
                y.equals(gFp2.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void setOne() {
        this.x.setZero();
        this.y.setOne();
    }

    public void setZero() {
        this.x.setZero();
        this.y.setZero();
    }

    public GFp getX() {
        return x;
    }

    public GFp getY() {
        return y;
    }

    public void print() {
        System.out.println(toString());
    }

    /**
     * x^2 = β
     * pi(ax + b) = pi(a)pi(x) + pi(b)
     *            = a.x^p + b
     *            = a.x.x^2(p-1)/2 + b
     *            = a.x.β^(p-1)/2 + b
     *            = a.x.(-1) + b // -1 = β^(p-1)/2 (mod p)
     *            = conjugate(ax + b)
     * @return
     */
    public GFp2 frobeniusP() {
        return this.conjugate();
    }

    public GFp2 conjugate() {
        return new GFp2(this.x.negate(), this.y);
    }
}
