package com.tiennv.ec;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * m = Message
 * x = Private key
 * G = Generator point
 * X = Public key (X = x*G, public key = private key * generator point)
 *
 * H(x, y, z..) = Cryptographic Hashing function
 * R = random nonce * generator point
 * s = random nonce + Hash function(Users Public Key, Random point on Elliptic Curve, the message (transaction)) * Private Key
 * (R, s) = (r*G, r + H(X, R, m) * x)
 *
 * (R, s) = Signature (R is the x co-ordinate of a random value after multiplying by the generator point, s is the signature)
 *
 * Signature verification:
 * s*G = R + H(X,R,m) * X
 * = G*(r + H(X, R, m) * x) = r*G + H(X, R, m) * x * G = R + H(X, R, m) * X
 *
 */
public class SchnorrSignatures {

    public void create() {

        String m = "message";

        PrivateKey priv = Secp256k1.generateKeyPair(160);
        Point pub = priv.getPoint();

        SecureRandom secureRandom = new SecureRandom();
        BigInteger nonce = new BigInteger(160, secureRandom);
        Point R = Secp256k1.G.scalarMultiply(nonce);

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger msg = new BigInteger(m.getBytes());
        System.out.println(m);
        BigInteger sum = pub.getAffineX().or(R.getAffineX()).or(msg);

        byte[] encodedhash = digest.digest(sum.toByteArray());

        BigInteger H = new BigInteger(encodedhash);


        BigInteger s = nonce.add(H.and(priv.getPrivateKey()));


        BigInteger Hx = H.and(priv.getPrivateKey());
        Point HxG = Secp256k1.G.scalarMultiply(Hx);

        // (R, S)

        // Signature verification:
        // s*G = R + H(X,R,m) * X

//        Point HX =  pub.scalarMultiply(H);
//        EFp eFp = new EFp();

        //R + H(X,R,m) * X
        Point right = R.add(pub.scalarMultiply(H));

        Point left = Secp256k1.G.scalarMultiply(s);

        System.out.println(left.equals(right));

    }

}
