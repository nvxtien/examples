package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * We create an example tripartite Diffie-Helman key exchange.
 */
public class TripartiteDHTest {

    @Test
    public void gt1Test() {
        G1 g1 = G1.newG1(CurvePoint.GENERATOR);
//        assertEquals(g1.multiplyBaseScalar(Fp256BN.n).getCurvePoint(), CurvePoint.POINT_INFINITY);

        G2 g2 = G2.newG2(TwistPoint.GENERATOR);
//        assertEquals(g2.multiplyBaseScalar(Fp256BN.n).getTwistPoint(), TwistPoint.POINT_INFINITY);

        GT test = Pairings.pair(g1, g2);
        test = test.multiplyScalar(Fp256BN.n);
        test.print();
    }

    @Test
    public void gtOrderTest() {
        G1 g1 = G1.newG1(CurvePoint.GENERATOR);
        g1.multiplyBaseScalar(Fp256BN.n).print();
        assertEquals(g1.multiplyBaseScalar(Fp256BN.n).getCurvePoint(), CurvePoint.POINT_INFINITY);
        g1.print();

        G2 g2 = G2.newG2(TwistPoint.GENERATOR);
        g1.multiplyBaseScalar(Fp256BN.n).print();
        assertEquals(g2.multiplyBaseScalar(Fp256BN.n).getTwistPoint(), TwistPoint.POINT_INFINITY);
//        g1.print();

        GT test = Pairings.pair(g1, g2);
        test = test.multiplyScalar(Fp256BN.n);
        test.print();
    }

    @Test
    public void tripartiteTest() {

        BigInteger a = Utils.generateKey(256);
        BigInteger b = Utils.generateKey(256);
        BigInteger c = Utils.generateKey(256);

        G1 g1_a = G1.multiplyBaseScalar(a);
//        g1_a.print();
        G2 g2_a = G2.multiplyBaseScalar(a);
//        g2_a.print();

        G1 g1_b = G1.multiplyBaseScalar(b);
//        g1_b.print();
        G2 g2_b = G2.multiplyBaseScalar(b);
//        g2_b.print();

        G1 g1_c = G1.multiplyBaseScalar(c);
//        g1_c.print();
        G2 g2_c = G2.multiplyBaseScalar(c);
//        g2_c.print();

        GT gt_a = Pairings.pair(g1_b, g2_c);
        gt_a = gt_a.multiplyScalar(a);
        gt_a.print();

        GT gt_b = Pairings.pair(g1_c, g2_a);
        gt_b = gt_b.multiplyScalar(b);
        gt_b.print();

        GT gt_c = Pairings.pair(g1_a, g2_b);
        gt_c = gt_c.multiplyScalar(c);
        gt_c.print();

        assertEquals(gt_a, gt_b);
        assertEquals(gt_b, gt_c);
    }
}