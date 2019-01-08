package com.tiennv.ec;

import java.math.BigInteger;
import java.util.List;

import static com.tiennv.ec.Constants.XI;
import static com.tiennv.ec.Constants.XI_PMinus1_Over6;

public class GFp12 {

    private GFp6 x;

    private GFp6 y;

    public GFp12() {
//        this.x = null;
//        this.y = null;
        setOne();
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

    public GFp12 conjugate() {
        return new GFp12(getX().negate(), getY());
    }

    /**
     * Algorithm 23 Inverse in Fp12 = Fp6 [w]/(w^2 − γ).
     *
     * Require: A = a0 + a1w ∈ Fp12 .
     * Ensure: C = c0 + c1w = A^−1 ∈ Fp12 .
     * 1. t0 ← a0^2;
     * 2. t1 ← a1^2;
     * 3. t0 ← t0 − γ · t1;
     * 4. t1 ← t0^−1;
     * 5. c0 ← a0 · t1;
     * 6. c1 ← −1 · a1 · t1;
     * 7. return C = c0 + c1w;
     *
     * @return
     */
    public GFp12 inverse() {

        GFp6 t0 = getY().square();
        GFp6 t1 = getX().square();

        t0 = t0.subtract(t1.multiplyGamma());
        t1 = t0.inverse();

        GFp6 c0 = getY().multiply(t1);
        GFp6 c1 = getX().multiply(t1).negate();

        return new GFp12(c1, c0);
    }

    /**
     * Algorithm 25 Exponentiation in Fp12 = Fp6 [w]/(w^2 − γ).
     * @param k
     * @return
     */
    public GFp12 exp(BigInteger k) {

        List<BigInteger> naf = Utils.NAF(k);
        int size = naf.size();

        GFp12 a = this;
        GFp12 c = a;

        for (int i = size -2; i >= 0; i--) {
            c = c.square();

            if (naf.get(i).equals(BigInteger.ONE)) {
                c = c.multiply(a);
            }

            if (naf.get(i).equals(BigInteger.valueOf(-1))) {
                c = c.multiply(a.conjugate());
            }
        }

        return c;
    }

    /**
     * Algorithm 22 Squaring in Fp12 = Fp6 [w]/(w^2 − γ).
     *
     * (y + xw)^2
     * = y^2 + 2xyw + x^2w^2
     * = y^2 + x^2.gamma + 2xyw
     * =  y^2 - gammayx - yx + gammax^2  + yx + gammayx + 2yxw
     * =  y(y-gammax) - x(y-gammax)  + yx + gammayx + 2yxw
     * = (y − x) · (y − gamma · x) + y · x + gamma · y · x + 2yxw
     *
     * Require: A = a0 + a1w ∈ Fp12 .
     * Ensure: C = c0 + c1w = A^2 ∈ Fp12 .
     * 1. c0 ← a0 − a1;
     * 2. c3 ← a0 − γ · a1;
     * 3. c2 ← a0 · a1;
     * 4. c0 ← c0 · c3 + c2;
     * 5. c1 ← 2c2;
     * 6. c2 ← γ · c2;
     * 7. c0 ← c0 + c2;
     * 8. return C = c0 + c1w;
     *
     * @return
     */
    private GFp12 square() {
        

    }
}
