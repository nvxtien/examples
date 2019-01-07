package com.tiennv.ec;

import static com.tiennv.ec.Constants.XI;
import static com.tiennv.ec.Constants.XI_PMinus1_Over6;

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

    /**
     * pi(xw + y) = pi(x).w^p + pi(y)
     *  pi(x).w^p = pi(x).w.w^(p-1)
     *            = pi(x).w.γ^(p-1)/2 // w^2 = γ
     *            = pi(x).w.ξ^(p-1)/6 // γ^3 = ξ
     *
     * @return
     */
    public GFp12 frobeniusP() {
        GFp6 x = this.getX().frobeniusP().multiplyScalar(XI_PMinus1_Over6);
        GFp6 y = this.getY().frobeniusP();

        return new GFp12(x, y);
    }

    public GFp12 frobeniusP2() {
        GFp6 x = this.getX().frobeniusP2().multiplyScalar(XI_PMinus1_Over6);
        GFp6 y = this.getY().frobeniusP2();

        return new GFp12(x, y);
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
