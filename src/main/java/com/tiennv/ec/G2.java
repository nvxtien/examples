package com.tiennv.ec;

import java.math.BigInteger;

import static com.tiennv.ec.TwistPoint.GENERATOR;

public class G2 {

    private TwistPoint twistPoint;

    public G2(final TwistPoint p) {
        this.twistPoint = p;
    }

    public static G2 multiplyBaseScalar(BigInteger k) {
        TwistPoint curve = GENERATOR.scalarMultiply(k);
        return new G2(curve);
    }

    public G2 add(final G2 a, final G2 b) {
        return new G2(a.getTwistPoint().add(b.getTwistPoint()));
    }

    public G2 multiply(final BigInteger k) {
        return new G2(this.twistPoint.scalarMultiply(k));
    }

    public TwistPoint getTwistPoint() {
        return twistPoint;
    }

    @Override
    public String toString() {
        return "G2{" +
                    "x=" + twistPoint.getX() +
                    ", y=" + twistPoint.getY() +
                    ", z=" + twistPoint.getZ() +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
