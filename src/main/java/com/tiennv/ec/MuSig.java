package com.tiennv.ec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://eprint.iacr.org/2018/068.pdf
 *
 * Signing:
 * L = {Xi} multiset of all public keys
 * ai = Hagg(L,Xi) the signer computes with its public key Xi
 */
public class MuSig {
    public Signature sign(List<Point> pubKeys, Point pubKey) {
        byte[] L = multisetOfPublicKeys(pubKeys);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( L );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write( pubKey.toString().getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte a[] = outputStream.toByteArray( );
        System.out.println(a);

        return null;
    }

    private byte[] multisetOfPublicKeys(List<Point> pubKeys) {
        return pubKeys.stream().map(Point::toString).collect(Collectors.joining()).getBytes();
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
