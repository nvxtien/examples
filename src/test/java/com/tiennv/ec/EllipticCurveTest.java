package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

public class EllipticCurveTest {

    @Test
    public void TraceTest() {

        BigInteger p = BigInteger.valueOf(11);
        BigInteger a =  BigInteger.valueOf(3);
        BigInteger b = BigInteger.valueOf(0);

        EllipticCurve curve = new EllipticCurve(p, a, b);
        curve.setNumberOfPoints(BigInteger.valueOf(12));
        System.out.println("Trace: " + curve.getTrace());

        Point p1 = Point.newPoint(curve, BigInteger.ZERO, BigInteger.ZERO);
        System.out.println(p1.scalarMultiply(BigInteger.valueOf(2)));
    }
}
