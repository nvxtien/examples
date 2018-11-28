package com.tiennv.ec;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Commitment {
    private final Point R;
    private final byte[] t;


    public Commitment() {
        SecureRandom random = new SecureRandom();
        BigInteger r = new BigInteger(160, random);
        this.R = Secp256k1.G.scalarMultiply(r);
        this.t = hash(this.R.toString().getBytes());
    }

    public Point getR() {
        return R;
    }

    public byte[] getT() {
        return t;
    }

    public byte[] hash(byte[] input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest.digest(input);
    }
}
