package com.tiennv.ec;

public class GFp12 {

    private GFp6 x;

    private GFp6 y;

    public GFp12() {
        this.x = null;
        this.y = null;
    }

    public GFp12(final GFp6 x, final GFp6 y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * Algorithm 20 Multiplication in Fp12 = Fp6 [w]/(w^2 − γ).
     *
     * https://eprint.iacr.org/2010/354.pdf
     *
     * Require: A = a0 + a1w ∈ Fp12 and B = b0 + b1w ∈ Fp12 .
     * Ensure: C = c0 + c1w = A · B ∈ Fp12 .
     * 1. t0 ← a0 · b0;
     * 2. t1 ← a1 · b1;
     * 3. c0 ← t0 + t1 · γ;
     * 4. c1 ← (a0 + a1) · (b0 + b1) − t0 − t1;
     * 5. return C = c0 + c1w;
     *
     * @param that
     */
    public GFp12 multiply (final GFp12 that) {
        GFp6 a0b0 = this.getY().multiply(that.getY());
        GFp6 a1b1 = this.getX().multiply(that.getX());

        GFp6 c0 = a0b0.add(a1b1.multiplyGamma());
        GFp6 c1 = this.getY().add(this.getX()).multiply(that.getY().add(that.getX())).subtract(a0b0).subtract(a1b1);

        return new GFp12(c1, c0);
    }

    public GFp6 getX() {
        return x;
    }

    public GFp6 getY() {
        return y;
    }

    public void setX(GFp6 x) {
        this.x = x;
    }

    public void setY(GFp6 y) {
        this.y = y;
    }

    public void setOne() {
        this.x.setZero();
        this.y.setOne();
    }

    public void setZero() {
        this.x.setZero();
        this.y.setZero();
    }
}
