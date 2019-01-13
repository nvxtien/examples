package com.tiennv.ec;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.*;

/**
 * Cubic over quadratic
 * Multiplication and Squaring on OptimalAtePairing-Friendly Fields
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

    public static final GFp6 ONE = new GFp6(GFp2.ZERO, GFp2.ZERO, GFp2.ONE);
    public static final GFp6 ZERO = new GFp6(GFp2.ZERO, GFp2.ZERO, GFp2.ZERO);

    private final GFp2 x;
    private final GFp2 y;
    private final GFp2 z; // xv^2 + yv + z

    public GFp6(final GFp2 x, final GFp2 y, final GFp2 z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GFp6 add(GFp6 b) {
        return new GFp6(this.x.add(b.getX()), this.y.add(b.getY()), this.z.add(b.getZ()));
    }

    public GFp6 subtract(GFp6 b) {
        return new GFp6(this.x.subtract(b.getX()), this.y.subtract(b.getY()), this.z.subtract(b.getZ()));
    }

    /**
     *
     * The Karatsuba method for computing the product c = ab
     *
     * https://eprint.iacr.org/2006/471.pdf
     * Section 4
     *
     * @param b
     * @return
     */
    public GFp6 multiply(final GFp6 b) {
        // precomputing the values v0 = a0b0, v1 = a1b1, v2 = a2b2
        GFp2 a0 = this.getZ();
        GFp2 a1 = this.getY();
        GFp2 a2 = this.getX();

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

    /**
     * High-Speed Software Implementation of the
     * Optimal Ate OptimalAtePairing over Barreto–Naehrig Curves
     *
     * Algorithm 17 Inverse in Fp6 = Fp2 [v]/(v^3 − ξ).
     * Require: A = a0 + a1v + a2v^2 ∈ Fp6 .
     * Ensure: C = c0 + c1v + c2v^2 = A^−1 ∈ Fp6 .
     *
     * 1. t0 ← a0^2;
     * 2. t1 ← a1^2;
     * 3. t2 ← a2^2;
     *
     * 4. t3 ← a0 · a1;
     * 5. t4 ← a0 · a2;
     * 6. t5 ← a1 · a2;
     *
     * 7. c0 ← t0 − ξ · t5;
     * 8. c1 ← ξ · t2 − t3;
     * 9. c2 ← t1 - t4; // c2 ← t1 · t4; error???
     *
     * 10. t6 ← a0 · c0;
     * 11. t6 ← t6 + ξ · a2 · c1;
     * 12. t6 ← t6 + ξ · a1 · c2;
     *
     * 13. t6 ← t6^-1;
     * 14. c0 ← c0 · t6;
     * 15. c1 ← c1 · t6;
     * 16. c2 ← c2 · t6;
     * 17. return C = c0 + c1v + c2v^2;
     *
     * https://eprint.iacr.org/2010/354.pdf
     *
     *
     * @return
     */
    public GFp6 inverse() {
        GFp2 a00 = this.z.square();
        GFp2 a11 = this.y.square();
        GFp2 a22 = this.x.square();

        GFp2 a01 = this.z.multiply(this.y);
        GFp2 a02 = this.z.multiply(this.x);
        GFp2 a12 = this.y.multiply(this.x);

        GFp2 A = a00.subtract(a12.multiplyXi());
        GFp2 B = a22.multiplyXi().subtract(a01);
        GFp2 C = a11.subtract(a02);

        GFp2 F = this.z.multiply(A);
        F = F.add(this.x.multiply(B).multiplyXi());
        F = F.add(this.y.multiply(C).multiplyXi());

        F = F.inverse();

        GFp2 c0 = F.multiply(A);
        GFp2 c1 = F.multiply(B);
        GFp2 c2 = F.multiply(C);

        return new GFp6(c2, c1, c0);
    }

    /**
     * Algorithm 12 Multiplication by γ
     *
     * Require: A ∈ Fp6 , where A = a0 + a1v + a2v^2; ai ∈ Fp2 .
     * Ensure: C = A · γ, C ∈ Fp6 , where C = c0 + c1v + c2v^2; ci ∈ Fp2 .
     * 1. c0 ← a2 · ξ;
     * 2. return C ← c0 + a0v + a1v^2;
     *
     * Aγ = (a0 + a1v + a2v^2)γ
     *    = a0y + a1vγ + a2v^2γ
     *  y = v
     * Aγ = a0v + a1v^2 + a2v^3
     *  v^3 = ξ
     * Aγ = a0v + a1v^2 + a2ξ
     *    = a2ξ + a0v + a1v^2
     *
     * @return
     */
    public GFp6 multiplyGamma() {
        GFp2 c0 = this.getX().multiplyXi();
        return new GFp6(this.getY(), this.getZ(), c0);
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

    /*public void setOne() {
        this.x.setZero();
        this.y.setZero();
        this.z.setOne();
    }*/

    /*public void setZero() {
    }*/

    /**
     * v^3 = ξ
     * pi(xv^2 + y.v + z) = pi(x).v^2p + pi(y).v^p + p(z)
     *         pi(x).v^2p =  pi(x).v^2.v^3(2p-2)/3
     *         pi(x).v^2p =  pi(x).v^2.ξ^(2p-2)/3
     *
     *         pi(y).v^p =  pi(x).v.v^3(p-1)/3
     *         pi(y).v^p =  pi(x).v.ξ^(p-1)/3
     *
     * @return
     */
    public GFp6 frobeniusP() {
        GFp2 x = this.getX().frobeniusP().multiply(XI_2P_Minus2_Over3);
        GFp2 y = this.getY().frobeniusP().multiply(XI_P_Minus1_Over3);
        GFp2 z = this.getZ().frobeniusP();

        return new GFp6(x, y, z);
    }

    public GFp6 multiplyScalar(final GFp2 k) {
        GFp2 x = this.getX().multiply(k);
        GFp2 y = this.getY().multiply(k);
        GFp2 z = this.getZ().multiply(k);
        return new GFp6(x, y, z);
    }

    /**
     * v^3 = ξ
     * pi2(xv^2 + y.v + z) = pi2(x).v^2p^2 + pi2(y).v^p^2 + p2(z)
     *         pi2(x).v^2p^2 =  x.v^2.v^3(2p^2-2)/3 // pi(pi(x)) = conjugate(conjugate(x)) = x
     *         pi2(x).v^2p^2 =  x.v^2.ξ^(2p^2-2)/3
     *
     *         pi2(y).v^p^2 =  y.v.v^3(p^2-1)/3
     *         pi2(y).v^p^2 =  y.v.ξ^(p^2-1)/3
     *
     *               pi2(z) = z
     *
     * @return
     */
    public GFp6 frobeniusP2() {
        GFp2 x = this.x.multiplyScalar(XI_2PSquared_Minus2_Over3);
        GFp2 y = this.y.multiplyScalar(XI_PSquared_Minus1_Over3);
        return new GFp6(x, y, this.z);
    }

    public GFp6 negate() {
        return new GFp6(getX().negate(), getY().negate(), getZ().negate());
    }

    public GFp6 multiplyGFp(GFp k) {
        BigInteger b = k.getValue();
        return new GFp6(this.getX().multiplyScalar(b), this.getY().multiplyScalar(b), this.getZ().multiplyScalar(b));
    }

    @Override
    public String toString() {
        return "GFp6{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
