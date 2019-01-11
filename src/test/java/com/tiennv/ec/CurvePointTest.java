package com.tiennv.ec;

import org.junit.Test;

import java.math.BigInteger;

public class CurvePointTest {

    @Test
    public void generatorTest() {

        EllipticCurve ellipticCurve = new EllipticCurve(Fp256BN.p, Fp256BN.a, Fp256BN.b);
        System.out.println(ellipticCurve.toString());

        BigInteger jx = BigInteger.valueOf(1);
        BigInteger jy = BigInteger.valueOf(2);
        BigInteger jz = BigInteger.ONE;
        Jacobian r = new Jacobian(ellipticCurve, jx, jy, jz);
        System.out.println("Jacobian point: " + r.toString());

        GFp x = new GFp(new BigInteger("1"));
        GFp y = new GFp(new BigInteger("2"));
        GFp z = new GFp(new BigInteger("1"));

        CurvePoint GENERATOR = new CurvePoint(x, y, z);

        CurvePoint a = GENERATOR.scalarMultiply(Fp256BN.p.subtract(BigInteger.valueOf(3)));
        a.print();

        a = GENERATOR.scalarMultiply(Fp256BN.p);
        a.print();
    }
}
