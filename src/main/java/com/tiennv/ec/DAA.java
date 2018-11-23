package com.tiennv.ec;

import java.math.BigInteger;

/**
 * https://eprint.iacr.org/2011/338.pdf
 */
public class DAA extends EFp implements Computation {

    private final String algorithm;

    public DAA() {
        this.algorithm = "Right2Left";
    }

    public DAA(String algorithm) {
        this.algorithm = algorithm;
    }
    
    /**
     * given k=bn-1||bn-2||...||b0 the binary representation of k
     *
     * Input: P, k=bn-1||bn-2||...||b0
     *
     * Output: Q = kP
     *    Q=0, D=P
     *    For i from 0 to n-1
     *        If bi=1 then Q=Q+N
     *        D=2D
     *    Return Q
     *
     * @param k
     * @param r
     * @return
     */
    private Point Right2Left(BigInteger k, Point r) {
        Point q = Point.POINT_INFINITY;
        Point d = Point.newPoint(r.getEC(), r.getAffineX(), r.getAffineY());

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
        int n = k.bitLength();
        for (int i = 0; i <= n - 1; i++) {
            BigInteger bit = k.shiftRight(i).and(BigInteger.ONE);
            if (bit.compareTo(BigInteger.ONE) == 0) {
                q = add(q, d);
            }
            d = doubling(d);
        }

        return q;
    }

    /**
     * given k=bn-1||bn-2||...||b0 the binary representation of k
     *
     * Input: P, k=bn-1||bn-2||...||b0
     *
     * Output: Q = kP
     *    Q=P, D=P
     *    For i from n-2 to 0
     *        Q=2Q
     *        If bi=1 then Q=Q+N
     *    Return Q
     *
     * @param k
     * @param r
     * @return
     */
    private Point Left2Right(BigInteger k, Point r) {
        Point q = Point.newPoint(r.getEC(), r.getAffineX(), r.getAffineY());

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
        int n = k.bitLength();
        for (int i = n - 2; i>= 0; i--) {
            q = doubling(q);
            BigInteger bit = k.shiftRight(i).and(BigInteger.ONE);
            if (bit.compareTo(BigInteger.ONE) == 0) {
                q = add(q, r);
            }
        }

        return q;
    }

    @Override
    public Point scalarMultiply(BigInteger k, Point r) {
        Point q;
        switch (this.algorithm) {
            case "Left2Right":
                q = Left2Right(k, r);
                break;
            default:
                q = Right2Left(k, r);
        }
        return q;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
