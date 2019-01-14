package com.tiennv.ec;

import java.math.BigInteger;

import static com.tiennv.ec.CurvePoint.GENERATOR;

/**
 * The order of a point P is defined
 * to be the smallest integer n such that [n]P = ∞. We let E(Fp)[n] be the
 * subgroup of E(Fp) consisting of points of order n
 */
public class G1 {

    private CurvePoint curvePoint;

    public G1(final CurvePoint p) {
        this.curvePoint = p;
    }

    public static G1 multiplyBaseScalar(BigInteger k) {
        CurvePoint curve = GENERATOR.multiplyScalar(k);
        return new G1(curve);
    }

    public static G1 multiplyScalar(final G1 g1,  BigInteger k) {
        CurvePoint curve = g1.getCurvePoint().multiplyScalar(k);
        return new G1(curve);
    }

    public static G1 add(final G1 a, final G1 b) {
        return new G1(a.getCurvePoint().add(b.getCurvePoint()));
    }

    public CurvePoint getCurvePoint() {
        return curvePoint;
    }

    @Override
    public String toString() {
        return "G1{" +
                "x=" + curvePoint.getX() +
                ", y=" + curvePoint.getY() +
                ", z=" + curvePoint.getZ() +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
