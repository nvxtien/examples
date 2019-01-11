package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

public class CurvePointTest {

    @Test
    public void generatorTest() {

        EllipticCurve ellipticCurve = new EllipticCurve(Fp256BN.p, Fp256BN.a, Fp256BN.b);
        BigInteger jx = BigInteger.valueOf(1);
        BigInteger jy = BigInteger.valueOf(-2);
        BigInteger jz = BigInteger.ONE;
        Jacobian r1 = new Jacobian(ellipticCurve, jx, jy, jz);
        r1 = r1.scalarMultiply(Fp256BN.n);
//        r1.print();

        Point r = r1.toAffine();
        r.print();

        CurvePoint curvePoint = CurvePoint.GENERATOR.scalarMultiply(Fp256BN.n);
        curvePoint.print();

        CurvePoint curvePoint1 = CurvePoint.GENERATOR.scalarMultiply(Fp256BN.n.subtract(BigInteger.ONE));
        curvePoint1.print();

        CurvePoint curvePoint2 = CurvePoint.GENERATOR.scalarMultiply(BigInteger.ONE);
        curvePoint2.print();

        CurvePoint curvePoint3 = curvePoint1.add(curvePoint2);
        curvePoint3.print();

    }
}
