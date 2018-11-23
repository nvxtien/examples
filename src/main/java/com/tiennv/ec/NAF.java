package com.tiennv.ec;

import java.math.BigInteger;
import java.util.List;

public class NAF extends EFp implements Computation {

    /**
     * INPUT: Positive integer k, P ∈ E(Fq).
     * OUTPUT: k ⋅ P.
     *        Based on previous algorithm compute NAF(k) =∑(l−1)(i=0)ki⋅2i.
     *        Q←∞.
     *        For i from l−1 down to 0 do
     *              Q←2Q.
     *              If ki  = 1 then Q←Q+P.
     *              If ki  = −1 thenQ←Q−P.
     * Return(Q).
     *
     * @param k
     * @param r
     */
    @Override
    public Point scalarMultiply(BigInteger k, Point r) {
        List<BigInteger> naf = Utils.NAF(k);
        int size = naf.size();
        Point q = Point.POINT_INFINITY;
        for (int i = size -1; i >= 0; i--) {
            q = doubling(q);
            if (naf.get(i).compareTo(BigInteger.ONE) == 0) {
                q = add(q, r);
            }

            if (naf.get(i).compareTo(BigInteger.valueOf(-1)) == 0) {
                q = add(q, inverse(r));
            }
        }

        return q;
    }
}
