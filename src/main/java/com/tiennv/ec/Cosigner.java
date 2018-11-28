package com.tiennv.ec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * https://eprint.iacr.org/2018/068.pdf
 *
 * Signing:
 * L = {Xi} multiset of all public keys
 * ai = Hagg(L,Xi) the signer computes with its public key Xi
 */
public class Cosigner {

    private RCommitment rCommitment;


    /*private byte[] aggH(final byte[] L, final Point pub) {
        return hash(concat(L, pub.toString().getBytes()));
    }*/

    /*private Point computeXa(final byte[] L, final Point pub) {
        byte[] a = aggH(L, pub);
        return pub.scalarMultiply(new BigInteger(a));
    }*/

    /*public Signature sign(List<Point> pubKeys, Point pubKey, List<Commitment> commitments, BigInteger r, byte[] a, PrivateKey priv, byte[] m) throws InvalidCommitment {
        byte[] L = multisetOfPublicKeys(pubKeys);

        Optional<Point> optPubKeys = pubKeys.stream().map(point -> computeXa(L, pubKey)).reduce((x, y) -> x.add(y));
        Point aggregatedPubKeys = optPubKeys.get();

        for (int i=0; i<commitments.size(); i++) {
            if (Arrays.equals(commitments.get(i).getT(), commitments.get(i).getR().toString().getBytes())) {
                throw new InvalidCommitment();
            }
        }

        Optional<Point> optR = commitments.stream().map(Commitment::getR).reduce((x, y) -> x.add(y));
        Point sumR = optR.get();

        byte[] XR = concat(aggregatedPubKeys.toString().getBytes(), sumR.toString().getBytes());

        byte[] XRm = concat(XR, m);

        byte[] c = hash(XRm);

        BigInteger s = r.add(new BigInteger(c).multiply(new BigInteger(a)).multiply(priv.getPrivateKey())).mod(Secp256k1.n);
        return null;
    }*/

    public Signature cosign() {

    }

    private byte[] comH(Point pR) {
        return hash(pR.toString().getBytes());
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

    /*public TCommitment compute(Point pub) {
        SecureRandom random = new SecureRandom();
        BigInteger r = new BigInteger(160, random);
        Point pR = Secp256k1.G.scalarMultiply(r);
        byte[] t = comH(pR);
        this.rCommitment = new RCommitment(pub, pR);
        return new TCommitment(pub, t);
    }*/

    private Point Xa(byte[] L, Point X) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( L );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write( X.toString().getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] LX = outputStream.toByteArray();
        System.out.println(LX);

        byte[] a = hash(LX);

        return X.scalarMultiply(new BigInteger(a));
    }


    private byte[] concat(final byte[] a, final byte[] b) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write( a );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write( b );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
