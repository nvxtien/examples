package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

public class TwistPointTest {

    @Test
    public void generatorTest() {

        GFp2 x = new GFp2(new GFp(new BigInteger("21167961636542580255011770066570541300993051739349375019639421053990175267184")), new GFp(new BigInteger("64746500191241794695844075326670126197795977525365406531717464316923369116492")));
        GFp2 y = new GFp2(new GFp(new BigInteger("20666913350058776956210519119118544732556678129809273996262322366050359951122")), new GFp(new BigInteger("17778617556404439934652658462602675281523610326338642107814333856843981424549")));
        GFp2 z = new GFp2(new GFp(new BigInteger("0")), new GFp(new BigInteger("1")));

        TwistPoint GENERATOR = new TwistPoint(x, y, z);

        TwistPoint a = GENERATOR.scalarMultiply(Fp256BN.n);
        a.print();

        TwistPoint a1 = GENERATOR.scalarMultiply(Fp256BN.n.subtract(BigInteger.ONE));
        a1.print();

        TwistPoint a2 = GENERATOR.scalarMultiply(BigInteger.ONE);
        a2.print();

        TwistPoint a3 = a1.add(a2);
        a3.print();

    }
}
