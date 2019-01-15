package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.*;
import static org.junit.Assert.assertEquals;

public class ConstantsTest {

    @Test
    public void constantsTest() {

        // γ1,i = u^(i·(p−1)/6)
        GFp2 gamma11 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(6)));
        gamma11.print();

        GFp2 gamma12 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(3)));
        gamma12.print();

        GFp2 gamma13 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)));
        gamma13.print();

        GFp2 gamma14 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(6)).multiply(BigInteger.valueOf(4)));
        gamma14.print();

        GFp2 gamma15 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(6)).multiply(BigInteger.valueOf(5)));
        gamma15.print();

        GFp2 gamma = XI.exp(Fp256BN.p);
        gamma.print();

        System.out.println("TWIST_B");
        GFp2 twistB = XI.inverse().multiplyScalar(BigInteger.valueOf(3));
        assertEquals(twistB, TWIST_B);

        // ξ^(p-1)/2
        System.out.println("XI_P_Minus1_Over2");
        GFp2 xi_P_Minus1_Over2 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(TWO));
        assertEquals(xi_P_Minus1_Over2, XI_P_Minus1_Over2);

        GFp2 xiToPSquaredMinus1Over3 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).subtract(BigInteger.ONE).divide(THREE));
        xiToPSquaredMinus1Over3.print();

        // ξ^(p^2-1)/2
        // XI_PSquared_Minus1_Over2
        // GFp2{x=0, y=65000549695646603732796438742359905742825358107623003571877145026864184071782}
        System.out.println("XI_PSquared_Minus1_Over2");
        GFp2 XI_PSquared_Minus1_Over2 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)));
        XI_PSquared_Minus1_Over2.print();

        XI_PSquared_Minus1_Over2 = XI_PSquared_Minus1_Over2.negate();
        XI_PSquared_Minus1_Over2.print();

        // ξ^(p-1)/6
        // XI_P_Minus1_Over6
        System.out.println("XI_P_Minus1_Over6");
        GFp2 XI_P_Minus1_Over6 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(6)));
        XI_P_Minus1_Over6.print();

        // ξ^(2p^2-2)/3
        // XI_2PSquared_Minus2_Over3
        System.out.println("XI_2PSquared_Minus2_Over3");
        GFp2 XI_2PSquared_Minus2_Over3 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(2)).divide(BigInteger.valueOf(3)));
        XI_2PSquared_Minus2_Over3.print();

        // ξ^(2p^2-1)/3
        // XI_2PSquared_Minus1_Over3
        System.out.println("XI_2PSquared_Minus1_Over3");
        GFp2 XI_2PSquared_Minus1_Over3 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(3)));
        XI_2PSquared_Minus1_Over3.print();

        // ξ^(p^2-1)/3
        // XI_PSquared_Minus1_Over3
        System.out.println("XI_PSquared_Minus1_Over3");
        GFp2 XI_PSquared_Minus1_Over3 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(3)));
        XI_PSquared_Minus1_Over3.print();

        // XI_2P_Minus2_Over3
        // ξ^(2p-2)/3
        System.out.println("XI_2P_Minus2_Over3");
        GFp2 XI_2P_Minus2_Over3 = XI.exp(Fp256BN.p.multiply(TWO).subtract(TWO).divide(BigInteger.valueOf(3)));
        XI_2P_Minus2_Over3.print();

        // XI_P_Minus1_Over3
        // ξ^(p-1)/3
        System.out.println("XI_P_Minus1_Over3");
        GFp2 XI_P_Minus1_Over3 = XI.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(3)));
        XI_P_Minus1_Over3.print();

        // XI_2PSquared_Minus1_Over6
        // ξ^(p^2-1)/6
        System.out.println("XI_2PSquared_Minus1_Over6");
        GFp2 XI_2PSquared_Minus1_Over6 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(6)));
        XI_2PSquared_Minus1_Over6.print();

        // XI_PSquaredMinus1_Over2
        // ξ^(p^2-1)/2
        System.out.println("XI_PSquaredMinus1_Over2");
        GFp2 XI_PSquaredMinus1_Over2 = XI.exp(Fp256BN.p.multiply(Fp256BN.p).subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(2)));
        XI_PSquaredMinus1_Over2.negate().print();

        GFp2 cọnjugateXi = new GFp2(new GFp(new BigInteger("-1")), new GFp(new BigInteger("3")));
        cọnjugateXi.print();
    }
}