package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.TWO;
import static org.junit.Assert.assertEquals;

public class GFp2Test {

    @Test
    public void expTest() {
        GFp2 gFp2 = new GFp2(new GFp(BigInteger.valueOf(1)), new GFp(BigInteger.valueOf(3)));
        GFp2 expected = gFp2.exp(BigInteger.valueOf(1));
        expected.print();

        GFp2 actual = new GFp2(new GFp(BigInteger.valueOf(1)), new GFp(BigInteger.valueOf(3)));

        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(2));
        expected.print();
        actual = new GFp2(new GFp(BigInteger.valueOf(6)), new GFp(BigInteger.valueOf(8)));
        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(3));
        expected.print();
        actual = new GFp2(new GFp(BigInteger.valueOf(26)), new GFp(BigInteger.valueOf(18)));
        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(13));
        expected.print();
        actual = new GFp2(new GFp(BigInteger.valueOf(-2729024)), new GFp(BigInteger.valueOf(-1597632)));
        actual.print();
        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(43));
        expected.print();
        actual = new GFp2(new GFp(new BigInteger("3019264689115366424576")), new GFp(new BigInteger("940234405380429447168")));
        actual.print();
        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(43));
        expected.print();
        actual = new GFp2(new GFp(new BigInteger("3019264689115366424576")), new GFp(new BigInteger("940234405380429447168")));
        actual.print();
        assertEquals(actual, expected);

        expected = gFp2.exp(BigInteger.valueOf(44));
        expected.print();
        actual = new GFp2(new GFp(new BigInteger("9998028472726528720896")), new GFp(new BigInteger("65000549695646603732796438742359905742825358107623003571678583553890105988711")));
        actual.print();
        assertEquals(actual, expected);

        GFp2 actual45 = gFp2.exp(BigInteger.valueOf(45));
        System.out.println("++++++++");
        actual45.print();
        GFp2 expected45 = new GFp2(new GFp(new BigInteger("29795523945205508079616")), new GFp(new BigInteger("65000549695646603732796438742359905742825358107623003561283432135215421101671")));
        expected45.print();
        assertEquals(expected45, actual45);

        GFp2 actual1 = gFp2.exp(BigInteger.valueOf(1));
        System.out.println("++++++++");
        actual1.print();
        GFp2 expected1 = new GFp2(new GFp(new BigInteger("1")), new GFp(new BigInteger("3")));
        expected1.print();
        assertEquals(expected1, actual1);

        GFp2 actual46 = gFp2.exp(Fp256BN.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(3)));
        System.out.println("++++++++");
        actual46.print();
    }

    public void nullTest() {
        GFp2 gFp2 = GFp2.ONE;
        gFp2.setOne();
    }
}
