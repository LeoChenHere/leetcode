package com.test;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

import org.web3j.utils.Numeric;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.security.KeyFactory;
import sun.security.ec.ECPublicKeyImpl;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import org.apache.commons.codec.binary.Base64;



/**
 * Created by leo on 2017/10/23.
 */
public class ECCKeyAgreement {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("EC","SunEC");
        ECGenParameterSpec ecsp;

        ecsp = new ECGenParameterSpec("secp256k1");
        kpg.initialize(ecsp);
        System.out.println(ecsp);

        KeyPair kpU = kpg.genKeyPair();
        PrivateKey privKeyU = kpU.getPrivate();
        PublicKey pubKeyU = kpU.getPublic();
        byte[] privKeyByte = privKeyU.getEncoded();
        byte[] pubKeyByte = pubKeyU.getEncoded();
        System.out.println("User U privKeyU: " + privKeyU);
        System.out.println(privKeyByte);
        System.out.println(pubKeyByte);
        System.out.println("User U privKeyU: " + new BigInteger(1,privKeyByte).toString(16).toUpperCase());
        System.out.println("User U pubKeyU: " + new BigInteger(1,pubKeyByte).toString(16).toUpperCase());
        KeyPair kpV = kpg.genKeyPair();
        PrivateKey privKeyV = kpV.getPrivate();
        PublicKey pubKeyV = kpV.getPublic();
        System.out.println("User V privKeyV: " + privKeyV.toString() );
        System.out.println("User V privKeyV: " + new BigInteger(1,privKeyV.getEncoded()).toString(16).toUpperCase());
        System.out.println("User V pubKeyV: " + pubKeyV.toString());
        System.out.println("User V pubKeyV: " + new BigInteger(1,pubKeyV.getEncoded()).toString(16).toUpperCase());


        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        //byte[] pubKeyByte = Numeric.hexStringToByteArray("04b058a08fa24c2db7535694b4edf4a65dce38654c11a66f94f46f887a6759d56a738810f4008401065301ccd9782e2df5564f2bd6929f2c90e796f67bd8e3b67b");
        //String prublicKeyStr = new BASE64Encoder().encodeBuffer(pubKeyByte);
        String prublicKeyStr ="MEAwEAYHKoZIzj0CAQYFK4EEAAEDLAAEAv4TwFN7vBGsqgfXk95ObV5clO7oAokHD7BdOP9YMh8ugAU21TjM2qPZ";
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(prublicKeyStr);
        //String temp1 = new BASE64Encoder().encodeBuffer(keyBytes);
        //System.out.println(temp1);
        System.out.println("prublicKeyStr: " + prublicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        System.out.println("keySpec: " + keySpec);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        System.out.println("publicKey: " + publicKey);

/*
        byte[] privByte = Numeric.hexStringToByteArray("09a11f0a52a073ae647c25530d4fff4b7dc856c1881e2e4eaa9e48d0fdc31902");
        System.out.println("privByte: " + privByte);
        String privateKeyStr = new BASE64Encoder().encodeBuffer(privByte);
        System.out.println("privateKeyStr: " + privateKeyStr);
        byte[] privateBytes = new BASE64Decoder().decodeBuffer(privateKeyStr);
        System.out.println("privateBytes: " + privateBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privByte);
        PrivateKey localPrivateKey = keyFactory.generatePrivate(spec);
        System.out.println("localPrivateKey: " + localPrivateKey);

*/

        KeyAgreement ecdhU = KeyAgreement.getInstance("ECDH");
        ecdhU.init(privKeyU);
        ecdhU.doPhase(pubKeyV,true);
        KeyAgreement ecdhV = KeyAgreement.getInstance("ECDH");
        ecdhV.init(privKeyV);
        ecdhV.doPhase(pubKeyU,true);
        System.out.println(ecdhU);
        System.out.println(ecdhV);

        System.out.println("Secret computed by U: 0x" +
                (new BigInteger(1, ecdhU.generateSecret()).toString(16)).toUpperCase());
        System.out.println("Secret computed by V: 0x" +
                (new BigInteger(1, ecdhV.generateSecret()).toString(16)).toUpperCase());


    }
    
}
