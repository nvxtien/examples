package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class DAATest {
    @Test
    public void scalarMultiplyDouble() {
        BigInteger p = BigInteger.valueOf(31);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(9);
        BigInteger yr = BigInteger.valueOf(10);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("r: " + r.toString() + r.isOnCurve());

        System.out.println("expect to DAA");

        DAA daa = new DAA();

        Point expected = daa.scalarMultiply(BigInteger.valueOf(2), r);
        System.out.println("expected: " + expected + expected.isOnCurve());

        BigInteger xe = BigInteger.valueOf(18);
        BigInteger ye = BigInteger.valueOf(29);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void scalarMultiply3() {
        BigInteger p = BigInteger.valueOf(31);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(9);
        BigInteger yr = BigInteger.valueOf(10);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("r: " + r.toString() + r.isOnCurve());

        System.out.println("expect to DAA");

        DAA daa = new DAA();

        Point expected = daa.scalarMultiply(BigInteger.valueOf(3), r);
        System.out.println("expected: " + expected + expected.isOnCurve());

        BigInteger xe = BigInteger.valueOf(23);
        BigInteger ye = BigInteger.valueOf(19);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void scalarMultiply4() {
        BigInteger p = BigInteger.valueOf(31);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(9);
        BigInteger yr = BigInteger.valueOf(10);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("r: " + r.toString() + r.isOnCurve());

        System.out.println("expect to DAA");

        DAA daa = new DAA();

        Point expected = daa.scalarMultiply(BigInteger.valueOf(4), r);
        System.out.println("expected: " + expected + expected.isOnCurve());

        BigInteger xe = BigInteger.valueOf(4);
        BigInteger ye = BigInteger.valueOf(22);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }

    @Test
    public void scalarMultiply25() {
        BigInteger p = BigInteger.valueOf(31);
        BigInteger a = BigInteger.valueOf(1);
        BigInteger b = BigInteger.valueOf(13);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(9);
        BigInteger yr = BigInteger.valueOf(10);
        Point r = Point.newPoint(ellipticCurve, xr, yr);
        System.out.println("r: " + r.toString() + r.isOnCurve());

        System.out.println("expect to DAA");

        DAA daa = new DAA();

        Point expected = daa.scalarMultiply(BigInteger.valueOf(25), r);
        System.out.println("expected: " + expected + expected.isOnCurve());

        BigInteger xe = BigInteger.valueOf(16);
        BigInteger ye = BigInteger.valueOf(23);

        Point actual = Point.newPoint(ellipticCurve, xe, ye);
        assertEquals(expected, actual);
    }
}
