package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class PairingsTest {

    @Test
    public void pairTest() {
        BigInteger a = Utils.generateKey(256);
        BigInteger b = Utils.generateKey(256);

        G1 g1_a = G1.multiplyBaseScalar(a);
        G2 g2_a = G2.newG2(TwistPoint.POINT_INFINITY);
        g2_a = g2_a.multiplyScalar(g2_a, b);
        g2_a.print();

        GT gt_a = Pairings.pair(g1_a, g2_a);
        gt_a.print();
        assertEquals(gt_a.getOptimalAte(), GFp12.ONE);

        G1 g1_b = G1.newG1(CurvePoint.POINT_INFINITY);
        g1_b = g1_b.multiplyScalar(g1_b, b);
        G2 g2_b = G2.multiplyBaseScalar(b);

        GT gt_b = Pairings.pair(g1_b, g2_b);
        assertEquals(gt_b.getOptimalAte(), GFp12.ONE);
    }
}
