package com.tiennv.ec;

import java.math.BigInteger;

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

    public G1 add(final G1 a, final G1 b) {
        return new G1(a.getCurvePoint().add(b.getCurvePoint()));
    }

    public G1 multiply(final BigInteger k) {
        return new G1(this.curvePoint.scalarMultiply(k));
    }

    public CurvePoint getCurvePoint() {
        return curvePoint;
    }
}
