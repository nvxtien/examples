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

        Point r = Point.POINT_INFINITY;
        System.out.println("p1: " + r.toString());

        Point q = Point.POINT_INFINITY;
        System.out.println("p2: " + q.toString());

        Point expected = ellipticCurve.add(r, q);
        System.out.println("expected: " + expected);

        Point actual = Point.POINT_INFINITY;
        assertEquals(expected, actual);
    }

    @Test
    public void addOfFirstInfinityShouldReturnSecond() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        Point r = Point.POINT_INFINITY;
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

        Point q = Point.POINT_INFINITY;
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

    @Test
    public void doublingInfinity() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        Point r = Point.POINT_INFINITY;
        Point expected = ellipticCurve.doubling(r);
        System.out.println("expected: " + expected);

        Point actual = Point.POINT_INFINITY;
        //"R(2, 4) + Q(0, 3) must be E(2, 1)"
        assertEquals(expected, actual);
    }

    @Test
    public void doublingYZero() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(2); // just for testing only
        BigInteger yr = BigInteger.valueOf(0);
        Point r = new Point(xr, yr);

        Point expected = ellipticCurve.doubling(r);
        System.out.println("expected: " + expected);

        Point actual = Point.POINT_INFINITY;
        //"R(2, 4) + Q(0, 3) must be E(2, 1)"
        assertEquals(expected, actual);
    }

    @Test
    public void doubling() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(4);
        BigInteger yr = BigInteger.valueOf(4);
        Point r = new Point(xr, yr);
        System.out.println("p1: " + r.toString());

        BigInteger xq = BigInteger.valueOf(4);
        BigInteger yq = BigInteger.valueOf(4);
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

    @Test
    public void addTwoTheSame() {
        BigInteger p = BigInteger.valueOf(5);
        BigInteger a = BigInteger.valueOf(2);
        BigInteger b = BigInteger.valueOf(4);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(4);
        BigInteger yr = BigInteger.valueOf(4);
        Point r = new Point(xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = ellipticCurve.doubling(r);
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(2);
        BigInteger ye = BigInteger.valueOf(1);

        Point actual = new Point(xe, ye);
        //"R(2, 4) + Q(0, 3) must be E(2, 1)"
        assertEquals(expected, actual);
    }

    @Test
    public void iterateOverIndividualBitsOfInteger() {
        /**
         * Explanation (in bits):
         *
         * n
         * 100010101011101010 (example)
         * n >> 5
         * 000001000101010111 (all bits are moved over 5 spots, therefore
         * &                   the bit you want is at the end)
         * 000000000000000001 (0 means it will always be 0,
         * =                   1 means that it will keep the old value)
         * 1
         */
        int n = 9; // arbitrary integer
        System.out.println(Integer.toBinaryString(n)); // show full integer in binary
        int size = Integer.toBinaryString(n).length();
        for (int i = 0; i <= size-1; i++) {
            int s = n >> i;
            int bit = (s) & 1;
            System.out.println(Integer.toBinaryString(s));
            System.out.println( "Bit " + i + " : " + (bit == 1 ? "1" : "0"));
        }
    }

    @Test
    public void doubleAndadd() {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger a = BigInteger.valueOf(9);
        BigInteger b = BigInteger.valueOf(17);

        EllipticCurve ellipticCurve = new EllipticCurve(p, a, b);
        System.out.println(ellipticCurve.toString());

        BigInteger xr = BigInteger.valueOf(16);
        BigInteger yr = BigInteger.valueOf(5);
        Point r = new Point(xr, yr);
        System.out.println("p1: " + r.toString());

        Point expected = ellipticCurve.doubleAndadd(BigInteger.valueOf(9), r);
        System.out.println("expected: " + expected);

        BigInteger xe = BigInteger.valueOf(4);
        BigInteger ye = BigInteger.valueOf(5);

        Point actual = new Point(xe, ye);
        assertEquals(expected, actual);
    }
}
