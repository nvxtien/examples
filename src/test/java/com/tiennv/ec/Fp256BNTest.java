package com.tiennv.ec;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static com.tiennv.ec.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Fp256BNTest {

    @Test
    public void calculateParamsTest() {
        // p = 36u^4 + 36u^3 + 24u^2 + 6u + 1, where u = v^3 and v = 1868033.
        // n = 36u^4 + 36u^3 + 18u^2 + 6u + 1
        // n = p - 6u^2
        BigInteger v = new BigInteger("1868033");
        System.out.println(v);
        BigInteger u = v.pow(3);
        BigInteger u2 = u.pow(2);
        BigInteger u3 = u2.multiply(u);
        BigInteger u4 = u3.multiply(u);
        BigInteger p = THREE_SIX.multiply(u4).add(THREE_SIX.multiply(u3)).add(TWO_FOUR.multiply(u2)).add(SIX.multiply(u)).add(BigInteger.ONE);

        System.out.println(p.toString());
        // 65000549695646603732796438742359905742825358107623003571877145026864184071783

        BigInteger n = p.subtract(SIX.multiply(u2));
        // 65000549695646603732796438742359905742570406053903786389881062969044166799969

        System.out.println(n.toString());

        EllipticCurve curve = new EllipticCurve(p, BigInteger.ZERO, THREE);
        Point point = Point.newPoint(curve, BigInteger.valueOf(1), BigInteger.valueOf(2));
        point.print();


//        point = point.add(point);
//        point.print();

        point = point.scalarMultiply(BigInteger.valueOf(3));
        point.print();
    }

    @Test
    public void calculateParamsJacobianTest() {
        // p = 36u^4 + 36u^3 + 24u^2 + 6u + 1, where u = v^3 and v = 1868033.
        // n = 36u^4 + 36u^3 + 18u^2 + 6u + 1
        // n = p - 6u^2
        // m = 6u + 2
        BigInteger v = new BigInteger("1868033");
        System.out.println(v);
        BigInteger u = v.pow(3);
        System.out.println("u: " + u);

        BigInteger u2 = u.pow(2);
        BigInteger u3 = u2.multiply(u);
        BigInteger u4 = u3.multiply(u);
        BigInteger p = THREE_SIX.multiply(u4).add(THREE_SIX.multiply(u3)).add(TWO_FOUR.multiply(u2)).add(SIX.multiply(u)).add(BigInteger.ONE);

        System.out.println(p.toString());
        //       65000549695646603732796438742359905742825358107623003571877145026864184071783
        // sage: 65000549695646603732796438742359905742825358107623003571877145026864184071783

        BigInteger n = p.subtract(SIX.multiply(u2));
        //       65000549695646603732796438742359905742570406053903786389881062969044166799969
        // sage: 65000549695646603732796438742359905742570406053903786389881062969044166799969

        System.out.println(n.toString());

        EllipticCurve curve = new EllipticCurve(p, BigInteger.ZERO, THREE);
        Jacobian jacobian = new Jacobian(curve, BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.ONE);
        jacobian.print();

        jacobian = jacobian.scalarMultiply(BigInteger.valueOf(3));
        jacobian.print();

        Point point = jacobian.toAffine();
        point.print();

        BigInteger x = new BigInteger("48419212889656543083010101969160929129928422574580449077538991002917239022572");
        BigInteger y = new BigInteger("49774692246582904768430779909185191228141052052136201120196689689648825794038");
        Point expected = Point.newPoint(curve, x, y);

        Assert.assertEquals(expected, point);

        jacobian = jacobian.scalarMultiply(n);
        jacobian.print();
        assertTrue(jacobian.isInfinity());

        point = jacobian.toAffine();
        point.print();
        assertTrue(point.isInfinity());

        jacobian = new Jacobian(curve, BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.ONE);
        Jacobian jacobianNminusOne = jacobian.scalarMultiply(n.subtract(BigInteger.ONE));
        jacobianNminusOne.print();

        point = jacobianNminusOne.toAffine();
        point.print();

        x = new BigInteger("1");
        y = new BigInteger("65000549695646603732796438742359905742825358107623003571877145026864184071781");
        expected = Point.newPoint(curve, x, y);
        assertEquals(expected, point);

        Jacobian inv = jacobianNminusOne.negate();

        inv.print();
        jacobian.print();

        assertEquals(jacobian.toAffine(), inv.toAffine());

        BigInteger m = SIX.multiply(u).add(BigInteger.valueOf(2));
        System.out.println("6u2 NAF: " + m);
        List<BigInteger> naf = Utils.NAF(m);
        System.out.println("6u2 NAF: " + naf);
        System.out.println("6u2 NAF size: " + naf.size());

    }
}
