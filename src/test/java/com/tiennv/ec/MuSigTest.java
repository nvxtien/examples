package com.tiennv.ec;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MuSigTest {
    @Test
    public void multisetOfPublicKeysTest() {

        List<Point> pubKeys = new ArrayList<>();

        PrivateKey privateKey1 = Secp256k1.generateKeyPair(256);
        pubKeys.add(privateKey1.getPoint());

        PrivateKey privateKey2 = Secp256k1.generateKeyPair(256);
        pubKeys.add(privateKey2.getPoint());

        byte[] L = pubKeys.stream().map(Point::toString).collect(Collectors.joining()).getBytes();

        System.out.println(L.toString());
        System.out.println(L.length);
    }
}
