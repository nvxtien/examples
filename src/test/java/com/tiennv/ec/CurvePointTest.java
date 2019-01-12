package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

import static com.tiennv.ec.Constants.XI;
import static org.junit.Assert.assertTrue;

public class CurvePointTest {

    @Test
    public void generatorTest() {

        assertTrue(CurvePoint.GENERATOR.isOnCurve());

        CurvePoint curvePoint = CurvePoint.GENERATOR.scalarMultiply(Fp256BN.n);
        curvePoint.print();
        assertTrue(curvePoint.isInfinity());

        CurvePoint curvePoint1 = CurvePoint.GENERATOR.scalarMultiply(Fp256BN.n.subtract(BigInteger.ONE));
        curvePoint1.print();
        assertTrue(curvePoint1.isOnCurve());

        CurvePoint curvePoint2 = CurvePoint.GENERATOR.scalarMultiply(BigInteger.ONE);
        curvePoint2.print();
        assertTrue(curvePoint2.isOnCurve());

        CurvePoint curvePoint3 = curvePoint1.add(curvePoint2);
        curvePoint3.print();
        assertTrue(curvePoint3.isInfinity());

        XI.inverse().print();

        XI.print();

        XI.square().print();

        XI.inverse().multiplyScalar(Fp256BN.b).print();

    }
}
