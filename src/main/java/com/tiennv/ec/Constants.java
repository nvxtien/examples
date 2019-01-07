package com.tiennv.ec;

import java.math.BigInteger;

public class Constants {
    public static final BigInteger TWO = BigInteger.valueOf(2);
    public static final BigInteger THREE = BigInteger.valueOf(3);
    public static final BigInteger FOUR = BigInteger.valueOf(4);
    public static final BigInteger EIGHT = BigInteger.valueOf(8);

    public static final BigInteger SIX = BigInteger.valueOf(6);
    public static final BigInteger TWO_FOUR = BigInteger.valueOf(24);
    public static final BigInteger THREE_SIX = BigInteger.valueOf(36);

    public static final GFp2 XI = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));

    public static final GFp2 XI_2PMinus2_Over3 = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));
    public static final GFp2 XI_PMinus1_Over3 = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));
    public static final GFp2 XI_PMinus1_Over6 = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));

    //xi To 2PSquared Minus2 Over3
    public static final GFp2 XI_2PSquaredMinus2_Over3 = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));
    public static final GFp2 XI_2PSquaredMinus1_Over3 = new GFp2(new GFp(BigInteger.ONE), new GFp(BigInteger.valueOf(3)));

}
