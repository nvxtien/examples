package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

/**
 * We create an example tripartite Diffie-Helman key exchange.
 */
public class TripartiteDHTest {

    @Test
    public void tripartiteTest() {

        BigInteger a = new BigInteger("121");
        BigInteger b = new BigInteger("433");
        BigInteger c = new BigInteger("97");

        G1 pa = G1.multiplyBaseScalar(a);
        pa.print();

        G2 qa = G2.multiplyBaseScalar(a);
        qa.print();

//    qa = [a]P = (694, 1049)
//
//
//
//            , Qb = [b]P = (764, 140), Qc = [c]P = (18, 84).
    }
}
