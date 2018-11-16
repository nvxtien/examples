package com.tiennv.ec;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class EllipticCurveTest {

    @Test
    public void addOfTwoInfinityShouldReturnInfinity() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        Point r = new Point(null, null);
        System.out.println("p1: " + r.toString());

        Point q = new Point(null, null);
        System.out.println("p2: " + q.toString());

        Point expected = ellipticCurve.add(r, q);
        System.out.println("expected: " + expected);

        Point actual = new Point(null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void addOfFirstInfinityShouldReturnSecond() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        Point r = new Point(null, null);
        System.out.println("p1: " + r.toString());

        BigInteger xq = BigInteger.valueOf(0);
        BigInteger yq = BigInteger.valueOf(3);
        Point q = new Point(xq, yq);
        System.out.println("p2: " + q.toString());

        Point expected = ellipticCurve.add(r, q);
        System.out.println("expected: " + expected);

        assertEquals(expected, q);
    }

    @Test
    public void addOfSecondInfinityShouldReturnOne() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(2);
        BigInteger yr = BigInteger.valueOf(4);
        Point r = new Point(xr, yr);
        System.out.println("p1: " + r.toString());

        Point q = new Point(null, null);
        System.out.println("p2: " + q.toString());

        Point expected = ellipticCurve.add(r, q);
        System.out.println("expected: " + expected);

        assertEquals(expected, r);
    }

    @Test
    public void addOfZeroIntegersShouldReturnZero() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(2);
        BigInteger yr = BigInteger.valueOf(4);
        Point r = new Point(xr, yr);
        System.out.println("p1: " + r.toString());

        BigInteger xq = BigInteger.valueOf(0);
        BigInteger yq = BigInteger.valueOf(3);
        Point q = new Point(xq, yq);
        System.out.println("p2: " + q.toString());

        Point expected = ellipticCurve.add(r, q);
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(2);
        BigInteger ye = BigInteger.valueOf(1);

        Point actual = new Point(xe, ye);
        //"R(2, 4) + Q(0, 3) must be E(2, 1)"
        assertEquals(expected, actual);
    }
}
