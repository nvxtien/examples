package com.tiennv.ec;

import java.math.BigInteger;
import java.util.List;

import static com.tiennv.ec.Constants.*;
import static com.tiennv.ec.Fp256BN.gamma12;
import static com.tiennv.ec.Fp256BN.gamma13;

/**
 * https://eprint.iacr.org/2010/354.pdf
 */
public class OptimalAtePairing {

    public static final GFp2 ZERO = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ZERO));
    public static final GFp2 ONE = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ONE));

    public static final BigInteger U = new BigInteger("6518589491078791937");
    public static final BigInteger SIX_U_TWO = new BigInteger("39111536946472751624");
    public static final List<BigInteger> NAF = Utils.NAF(SIX_U_TWO);

    /**
     * Algorithm 27 Point addition and line evaluation
     * l_(R,Q)(P)
     * T = Q + R
     * @param q
     * @param r
     * @param p
//     * @param r2
     */
    public LineFuncReturn lineFuncAdd(TwistPoint r, TwistPoint q, CurvePoint p) {

        GFp2 xq = q.getX();
        GFp2 yq = q.getY();
        GFp2 zq = q.getZ();

        GFp2 xr = r.getX();
        GFp2 yr = r.getY();
        GFp2 zr = r.getZ();

        p.transformAffine();
        GFp xp = p.getX();
        GFp yp = p.getY();

        GFp2 t0 = q.getX().multiply(r.getZ().square());
        GFp2 t1 = (q.getY().add(r.getZ())).square().subtract(yq.square()).subtract(zr.square());
        t1 = t1.multiply(zr.square());

        GFp2 t2 = t0.subtract(xr);
        GFp2 t3 = t2.square();
        GFp2 t4 = t3.multiplyScalar(BigInteger.valueOf(4));
        GFp2 t5 = t4.multiply(t2);
        GFp2 t6 = t1.subtract(yr.multiplyScalar(BigInteger.valueOf(2)));

        GFp2 t9 = t6.multiply(xq);
        GFp2 t7 = xr.multiply(t4);

        GFp2 xt = t6.square().subtract(t5).subtract(t7.multiplyScalar(BigInteger.valueOf(2)));
        GFp2 zt = (zr.add(t2)).square().subtract(zr.square()).subtract(t3);

        GFp2 t10 = yq.add(zt);
        GFp2 t8 = t7.subtract(xt).multiply(t6);

        t0 = yr.multiply(t5).multiplyScalar(BigInteger.valueOf(2));

        GFp2 yt = t8.subtract(t0);

        t10 = t10.square().subtract(yq.square()).subtract(zt.square());
        t9 = t9.multiplyScalar(BigInteger.valueOf(2)).subtract(t10);

        // 19
        t10 = zt.multiplyScalar(yp.getValue()).multiplyScalar(BigInteger.valueOf(2));

        t6 = t6.negate();
        t1 = t6.multiplyScalar(xp.getValue()).multiplyScalar(BigInteger.valueOf(2));

        // 22
//        GFp6 l0, l1;
//        GFp2 a2 = ZERO;
//        GFp2 a1 = ZERO;
//        GFp2 a0 = t10;

        GFp2 zero = GFp2.ZERO;

        GFp6 l0 = new GFp6(zero, zero, t10);
        GFp6 l1 = new GFp6(zero, t9, t1);
        GFp12 l = new GFp12(l1, l0);

        TwistPoint t = new TwistPoint(xt, yt, zt);

//        t.setX(xt);
//        t.setY(yt);
//        t.setZ(zt);

        return new LineFuncReturn(t, l);
    }

    /**
     * Algorithm 26 Point doubling and line evaluation
     * @param q
     * @param p
     */
    public  LineFuncReturn lineFuncDouble(TwistPoint q, CurvePoint p) {

        GFp2 xq = q.getX();
        GFp2 yq = q.getY();
        GFp2 zq = q.getZ();

        p.transformAffine();
        GFp xp = p.getX();
        GFp yp = p.getY();

        GFp2 t0 = xq.square();
        GFp2 t1 = yq.square();
        GFp2 t2 = t1.square();
        GFp2 t3 = (t1.add(xq)).square().subtract(t0).subtract(t2);
        t3 = t3.multiplyScalar(BigInteger.valueOf(2));

        GFp2 t4 = t0.multiplyScalar(BigInteger.valueOf(3));
        GFp2 t6 = xq.add(t4);
        GFp2 t5 = t4.square();

        GFp2 xt = t5.subtract(t3.multiplyScalar(BigInteger.valueOf(2)));
        GFp2 zt = (yq.add(zq)).square().subtract(t1).subtract(zq.square());
        GFp2 yt = (t3.subtract(xt)).multiply(t4).subtract(t2.multiplyScalar(BigInteger.valueOf(8)));

        // 12
        t3 = t4.multiply(zq.square()).multiplyScalar(BigInteger.valueOf(2)).negate();
        t3 = t3.multiplyScalar(xp.getValue());

        t6 = t6.square().subtract(t0).subtract(t5).subtract(t1.multiplyScalar(BigInteger.valueOf(4)));
        t0 = zt.multiply(zq.square()).multiplyScalar(BigInteger.valueOf(2));
        t0 = t0.multiplyScalar(yp.getValue());

        GFp2 zero = GFp2.ZERO;
        GFp6 l0 = new GFp6(zero, zero, t0);
        GFp6 l1 = new GFp6(zero, t6, t3);
        GFp12 l = new GFp12(l1, l0);

        TwistPoint t = new TwistPoint(xt, yt, zt);
//        t.setX(xt);
//        t.setY(yt);
//        t.setZ(zt);

        return new LineFuncReturn(t, l);
    }

    /**
     * Algorithm 21 Multiplication by B = b0 + b1w, where b0 ∈ Fp2 and b1 =b10 + b11v + 0v^2
     *
     * Require: A = a0 + a1w ∈ Fp12 and B = b0 + b1w ∈ Fp12 , with b0 = b00 + 0v + 0v^2
     * and b1 = b10 + b11v + 0v^2
     * .
     * Ensure: C = c0 + c1w = A · B ∈ Fp12 .
     *
     * 1. t0 ← a0 · b0; {Algorithm 14}
     * 2. t1 ← a1 · b1; {Algorithm 15}
     * 3. c0 ← t0 + t1 · γ;
     * 4. t2 ← (b0 + b10)v + b11v + 0v^2;
     * 5. c1 ← (a0 + a1) · t2; {Algorithm 15}
     * 6. c1 ← c1 − t0 − t1;
     * 7. return C = c0 + c1w;
     *
     *
     * @param f
     */
    public GFp12 mulLine(GFp12 f, GFp12 line) {
        GFp6 t0 = f.getY().multiply(line.getY());
        GFp6 t1 = f.getX().multiply(line.getX());

        GFp6 c0 = t0.add(t1.multiplyGamma());

        GFp2 b0 = line.getY().getZ();
        GFp2 b10 = line.getX().getZ();
        GFp2 b11 = line.getX().getY();
        GFp2 zero = GFp2.ZERO;

        GFp6 t2 = new GFp6(zero, b11, b0.add(b10));

        GFp6 c1 = f.getY().add(f.getX()).multiply(t2);
        c1 = c1.subtract(t0).subtract(t1);

        return new GFp12(c1, c0);
    }

    /**
     * Algorithm 1 Optimal ate pairing over Barreto–Naehrig curves.
     *
     * @param q
     * @param p
     * @return
     */
    public GFp12 miller(final TwistPoint q, final CurvePoint p) {

        q.transformAffine();
        p.transformAffine();

        // 2. T ← Q, f ← 1;
        TwistPoint t = q;
        GFp12 f = GFp12.ONE;

        TwistPoint minusQ = q.negate();

        LineFuncReturn ret;
        for (int i = NAF.size()-2; i>=0; i--) {

            // 4. f ← f^2 · l_(T ,T) (P); T ← 2T;
            f = f.square();

            ret = lineFuncDouble(t, p); // l_(T ,T) (P)
            t = ret.getT(); // 2T
//            f = f.multiply(ret.getLine());
            f = mulLine(f, ret.getLine());

            if (NAF.get(i).equals(BigInteger.valueOf(-1))) {
                // 6. f ← f · l_(T ,−Q)(P); T ← T − Q;
                ret = lineFuncAdd(t, minusQ, p);
                t = ret.getT();
//                f = f.multiply(ret.getLine());
                f = mulLine(f, ret.getLine());


            } else if (NAF.get(i).equals(BigInteger.valueOf(1))) {
                // 8. f ← f · l-(T ,Q)(P); T ← T + Q;
                ret = lineFuncAdd(t, q, p);
                t = ret.getT();
//                f = f.multiply(ret.getLine());
                f = mulLine(f, ret.getLine());

            }

        }

        // 11. Q1 ← πp(Q); Q2 ← πp2 (Q);

        EllipticCurve twist;

        // (xω^2)^p=x^p.ω^2p=x^p.ω^2.ω^(2p-2)
        // ξ^6 = ω
        // (xω^2)^p=x^p.ω^2p=x^p.ω^2.ω^(2p-2)
        //                  = x^p.ω^2.ξ^(p-1)/3
        GFp2 x = q.getX().conjugate().multiply(gamma12);
//        GFp2 x = q.getX().conjugate().multiply(XI_PMinus1_Over3);


        // (yω^3)^p=y^p.ω^3p=y^p.ω^3.ω^(3p-3)
        // ξ^6 = ω
        // (yω^3)^p=y^p.ω^3p=y^p.ω^3.ξ^(p-1)/2
        GFp2 y = q.getY().conjugate().multiply(gamma13);
//        GFp2 y = q.getY().conjugate().multiply(XI_PMinus1_Over2);

        GFp2 z = ONE;
        TwistPoint q1 = new TwistPoint(x, y, z);

        // (xω^2)^p^2=x^p.ω^2p^2=x^p.ω^2.ω^(2p^2-2)
        // ξ^6 = ω
        // (xω^2)^p=x^p.ω^2p^2=x^p.ω^2.ω^(2p^2-2)
        //                  = x^p.ω^2.ξ^(p^2-1)/3
        x = q.getX().conjugate().multiply(XI_PSquaredMinus1_Over3);

        // (yω^3)^p^2=y^p.ω^3p^2=y^p.ω^3.ω^(3p^2-3)
        // ξ^6 = ω
        // (yω^3)^p=y^p.ω^3p^2=y^p.ω^3.ξ^(p^2-1)/2
//        y = q.getY().conjugate().multiply(XI_PSquaredMinus1_Over2).negate();
        // equals y, so we can ignore compute y, just reuse it

        TwistPoint minusQ2 = new TwistPoint(x, y, z);

        // 12. f ← f · l_(T ,Q1)(P); T ← T + Q1;
        ret = lineFuncAdd(t, q1, p);
        t = ret.getT();
        f = mulLine(f, ret.getLine());

        // 13. f ← f · l_(T ,−Q2)(P); T ← T − Q2;
        ret = lineFuncAdd(t, minusQ2, p);
//        t = ret.getT();
        f = mulLine(f, ret.getLine());

//        f = finalExponentiation(f);

        // x^p^2 =
        // xv^2p^2

        return f;
    }

    /**
     * Algorithm 31 Final Exponentiation
     *
     * @param inp
     */
    public GFp12 finalExponentiation(GFp12 inp) {

        GFp12 f;

        // 1) f1 ← ¯f
        GFp12 f1 = inp.conjugate();

        // 2) f2 ← f^−1
        GFp12 f2 = inp.inverse();

        // 3) f ← f1 · f2
        f = f1.multiply(f2);

        // 4) f ← frobeniusP2(f) · f;
        f = f.frobeniusP2().multiply(f);

        // 5) ft1 ← f^t
        BigInteger v = new BigInteger("1868033");
        BigInteger t = v.pow(3);
        GFp12 ft1 = f.exp(t);

        // 6
        GFp12 ft2 = ft1.exp(t);
        // 7
        GFp12 ft3 = ft2.exp(t);

        // 8
        GFp12 fp1 = f.frobeniusP();
        // 9
        GFp12 fp2 = f.frobeniusP2();
        // 10
        GFp12 fp3 = fp2.frobeniusP();
        // 11
        GFp12 y0 = fp1.multiply(fp2).multiply(fp3);
        // 12
        GFp12 y1 = f1;
        // 13
        GFp12 y2 = ft2.frobeniusP2();
        // 14
        GFp12 y3 = ft1.frobeniusP();
        // 15
        y3 = y3.conjugate();
        // 16
        GFp12 y4 = ft2.frobeniusP().multiply(ft1);
        // 17
        y4 = y4.conjugate();
        // 18
        GFp12 y5 = ft2.conjugate();
        // 19
        GFp12 y6 = ft3.frobeniusP().multiply(ft3);
        // 20
        y6 = y6.conjugate();
        // 21
        GFp12 t0 = y6.square().multiply(y4).multiply(y5);
        // 22
        GFp12 t1 = y3.multiply(y5).multiply(y6);
        // 23
        t0 = t0.multiply(y2);
        // 24
        t1 = t1.square().multiply(t0).square();
        // 25
        t0 = t1.multiply(y1);
        // 26
        t1 = t1.multiply(y0);
        // 27
        t0 = t0.square();
        // 28
        f = t1.multiply(t0);

        return f;
    }

    public GFp12 optimalAte(TwistPoint a, CurvePoint b) {
        GFp12 e = miller(a, b);
        GFp12 ret = finalExponentiation(e);

        if (a.isInfinity() || b.isInfinity()) {
            ret = GFp12.ONE;
        }

        return ret;
    }

    private class LineFuncReturn {
        private TwistPoint t;
        private GFp12 line;

        public LineFuncReturn(TwistPoint t, GFp12 line) {
            this.t = t;
            this.line = line;
        }

        public TwistPoint getT() {
            return t;
        }

        public GFp12 getLine() {
            return line;
        }
    }
}
