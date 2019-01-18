package com.tiennv.ec;

import java.math.BigInteger;

import static com.tiennv.ec.TwistPoint.GENERATOR;

public class G2 {

    private TwistPoint twistPoint;

    private G2(final TwistPoint p) {
        twistPoint = p;
    }

    public static G2 newG2(final TwistPoint p) {
        return new G2(p);
    }

    public static G2 multiplyBaseScalar(BigInteger k) {
        TwistPoint curve = GENERATOR.multiplyScalar(k);
        return new G2(curve);
    }

    public static G2 add(final G2 a, final G2 b) {
        return new G2(a.getTwistPoint().add(b.getTwistPoint()));
    }

    public static G2 multiplyScalar(final G2 g2, final BigInteger k) {
        return new G2(g2.getTwistPoint().multiplyScalar(k));
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
