package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.*;
import static com.tiennv.ec.Constants.XI_P_Minus1_Over2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwistPointTest {

    @Test
    public void generatorTest() {

        GFp2 x = new GFp2(new GFp(new BigInteger("21167961636542580255011770066570541300993051739349375019639421053990175267184")), new GFp(new BigInteger("64746500191241794695844075326670126197795977525365406531717464316923369116492")));
        GFp2 y = new GFp2(new GFp(new BigInteger("20666913350058776956210519119118544732556678129809273996262322366050359951122")), new GFp(new BigInteger("17778617556404439934652658462602675281523610326338642107814333856843981424549")));
        GFp2 z = new GFp2(new GFp(new BigInteger("0")), new GFp(new BigInteger("1")));

        TwistPoint twistPoint = new TwistPoint(x, y, z);
        assertTrue(twistPoint.isOnCurve());

        GFp2 bXIminusOne = XI.inverse().multiplyScalar(BigInteger.valueOf(3));
        bXIminusOne.print();

        x = x.conjugate().multiply(XI_P_Minus1_Over3);
        y = y.conjugate().multiply(XI_P_Minus1_Over2);
        z = GFp2.ONE;

        TwistPoint q1 = new TwistPoint(x, y, z);
        q1.print();
        assertTrue(q1.isOnCurve());

        TwistPoint q2 = q1.scalarMultiply(Fp256BN.n);
        assertTrue(q2.isInfinity());
        q2.print();

        TwistPoint a = TwistPoint.GENERATOR.scalarMultiply(Fp256BN.n);
        assertTrue(a.isInfinity());


        TwistPoint a1 = TwistPoint.GENERATOR.scalarMultiply(Fp256BN.n.subtract(BigInteger.ONE));
        TwistPoint a2 = TwistPoint.GENERATOR.scalarMultiply(BigInteger.ONE);
        TwistPoint a3 = a1.add(a2);
        assertTrue(a3.isInfinity());
        assertEquals(a1, a2.negate());
        a1.print();
        a2.negate().print();
    }
}
