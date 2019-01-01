package com.tiennv.ec;

import java.math.BigInteger;
import java.util.List;

public class Pairing {

    public static final GFp2 ZERO = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ZERO));
    public static final GFp2 ONE = new GFp2(new GFp(BigInteger.ZERO), new GFp(BigInteger.ONE));

    public static final BigInteger U = new BigInteger("6518589491078791937");
    public static final BigInteger SIX_U_TWO = new BigInteger("39111536946472751624");
    public static final List<BigInteger> NAF = Utils.NAF(SIX_U_TWO);

    /**
     * Algorithm 27 Point addition and line evaluation
     * lR,Q(P)
     * T = Q + R
     * @param q
     * @param r
     * @param p
//     * @param r2
     */
    public void lineFuncAdd(TwistPoint q, TwistPoint r, CurvePoint p, TwistPoint t, GFp12 l) {

        GFp2 xq = q.getX();
        GFp2 yq = q.getY();
        GFp2 zq = q.getZ();

        GFp2 xr = r.getX();
        GFp2 yr = r.getY();
        GFp2 zr = r.getZ();

        GFp xp = new GFp(p.toAffine().getAffineX());
        GFp yp = new GFp(p.toAffine().getAffineY());

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
        GFp6 l0, l1;
        GFp2 a2 = ZERO;
        GFp2 a1 = ZERO;
        GFp2 a0 = t10;
        l0 = new GFp6(a2, a1, a0);
        l1 = new GFp6(a2, t9, t1);

        t.setX(xt);
        t.setY(yt);
        t.setZ(zt);

        l.setX(l1);
        l.setY(l0);
    }

    /**
     * Algorithm 26 Point doubling and line evaluation
     * @param q
     * @param p
     * @param t
     * @param l
     */
    public  void lineFuncDouble(TwistPoint q, CurvePoint p, TwistPoint t, GFp12 l) {

        GFp2 xq = q.getX();
        GFp2 yq = q.getY();
        GFp2 zq = q.getZ();

        GFp xp = new GFp(p.toAffine().getAffineX());
        GFp yp = new GFp(p.toAffine().getAffineY());

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

        GFp6 l0, l1;
        GFp2 zero = ZERO;
        l0 = new GFp6(zero, zero, t0);
        l1 = new GFp6(zero, t6, t3);

        t.setX(xt);
        t.setY(yt);
        t.setZ(zt);

        l.setX(l1);
        l.setY(l0);
    }

    public void mulLine(GFp12 r, GFp2 a, GFp2 b, GFp2 c) {

    }

    public GFp12 miller(final TwistPoint q, final CurvePoint p) {
        q.transformAffine();
        p.transformAffine();

        TwistPoint t = q;
        TwistPoint negq = q.negate();

        GFp12 f = new GFp12();
        f.setOne();


        for (int i = NAF.size()-2; i>=0; i--) {
            lineFuncDouble(q, p, t, f);

            if (NAF.get(i).equals(BigInteger.valueOf(-1))) {
                lineFuncAdd(q, t, p, t, f);
            } else if (NAF.get(i).equals(BigInteger.valueOf(1))) {
                lineFuncAdd(negq, t, p, t, f);
            }

        }

        // Q1 ← πp(Q); Q2 ← πp2 (Q);
        // Q1 ← (x^p, y^p)

        return f;
    }

    public void finalExponentiation(GFp12 inp) {

    }

    public void optimalAte(G2 a, G1 b) {

    }

    private class LineEval {
        private G2 t;
        private GFp12 l;

        public LineEval(G2 t, GFp12 l) {
            this.t = t;
            this.l = l;
        }

        public G2 getT() {
            return t;
        }

        public GFp12 getL() {
            return l;
        }
    }
}
