package com.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.web3j.utils.*;
import org.web3j.crypto.*;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import java.security.KeyFactory;
import java.security.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.ec.ECPublicKeyImpl;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;


public class Test {
    //非对称密钥算法  
            public static final String KEY_ALGORITHM="EC";
                  
            //本地密钥算法，即对称加密算法。可选des，aes，desede
            public static final String SECRET_ALGORITHM="AES";

    private static byte[] getSecretKey(String publicKey, String privateKey) {
        byte[] secretKey = null;
        try {
            // 初始化公钥

            System.out.println("publicKey: " + publicKey);
            byte[] publicBytes = (new BASE64Decoder()).decodeBuffer(publicKey);
            System.out.println("publicBytes:"+publicBytes);
            System.out.println("publicBytes:"+new BigInteger(publicBytes).toString(16));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            PublicKey localPublicKey = factory.generatePublic(keySpec);
            System.out.println("localPublicKey: " + localPublicKey);
            // 初始化私钥
            System.out.println("privateKey: " + privateKey);
            byte[] privateBytes = (new BASE64Decoder()).decodeBuffer(privateKey);
            System.out.println("privateBytes:"+privateBytes);
            System.out.println("privateBytes:"+new BigInteger(privateBytes).toString(16));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateBytes);
            PrivateKey localPrivateKey = factory.generatePrivate(spec);
            System.out.println("localPrivateKey: " + localPrivateKey);

            KeyAgreement agreement = KeyAgreement.getInstance("ECDH");
            agreement.init(localPrivateKey);
            agreement.doPhase(localPublicKey, true);
            // 生成本地密钥
            secretKey = agreement.generateSecret();
            System.out.println("Secret computed by U: 0x" +
                    (new BigInteger(1, secretKey).toString(16)).toUpperCase());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }
    public static byte[] getSecretKey(byte[] publicKey,byte[] privateKey) throws Exception{


        System.out.println("publicKey: " + publicKey);

        System.out.println("privateKey: " + privateKey);
        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);

        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);

        //初始化私钥
        //密钥材料转换  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        //产生私钥  
        PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //实例化  
        KeyAgreement keyAgree=KeyAgreement.getInstance("ECDH");
        //初始化  
        keyAgree.init(priKey);  
        keyAgree.doPhase(pubKey, true);
        //生成本地密钥  
        byte[] secretKey=keyAgree.generateSecret();
        return secretKey;
    }  

    public static final String PRIVATE_KEY_STRING =
            "09a11f0a52a073ae647c25530d4fff4b7dc856c1881e2e4eaa9e48d0fdc31902";
    static final String PUBLIC_KEY_STRING =
            "0xb058a08fa24c2db7535694b4edf4a65dce38654c11a66f94f46f887a6759d56a"
                    + "738810f4008401065301ccd9782e2df5564f2bd6929f2c90e796f67bd8e3b67b";
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp192k1");
    private static final ECDomainParameters CURVE = new ECDomainParameters(
            CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());
    public static void main(String[] args) throws InterruptedException , Exception{
        System.out.println("hello world!");
        byte[] privateKey = Numeric.hexStringToByteArray(PRIVATE_KEY_STRING);
        System.out.println("PRIVATE_KEY_STRING: "+PRIVATE_KEY_STRING);//原始字符串
        System.out.println("privateKey:"+ new BigInteger(privateKey).toString(16));  //16进制输出private byte[]
        BigInteger priKey = Numeric.toBigInt(privateKey);//new BigInteger(privateKey)
        System.out.println(priKey);

/* publickey*/
        BigInteger pubKeyFromPriv = Sign.publicKeyFromPrivate(priKey);
        System.out.println(pubKeyFromPriv);
        System.out.println("pubKeyFromPriv:"+ pubKeyFromPriv.toString(16));


        System.out.println("CURVE:"+CURVE);
        System.out.println("CURVE_PARAMS:["+CURVE_PARAMS.getCurve()+"]["+CURVE_PARAMS.getG()+"]["+CURVE_PARAMS.getN()+"]["+CURVE_PARAMS.getH()+"]");
        if (priKey.bitLength() > CURVE.getN().bitLength()) {
            priKey = priKey.mod(CURVE.getN());
        }
        ECPoint point =  new FixedPointCombMultiplier().multiply(CURVE.getG(), priKey);
        System.out.println(point);

        String publicStr = "MEAwEAYHKoZIzj0CAQYFK4EEAAEDLAAEAv4TwFN7vBGsqgfXk95ObV5clO7oAokHD7BdOP9YMh8ugAU21TjM2qPZ";
        String privateStr = "MDICAQAwEAYHKoZIzj0CAQYFK4EEAAEEGzAZAgEBBBTYJsR3BN7TFw7JHcAHFkwNmfil7w==";
        byte[] secretResult= getSecretKey(publicStr,privateStr);
        System.out.println("secret:"+secretResult);


        byte[] privateByteAlice = Numeric.hexStringToByteArray("303E020100301006072A8648CE3D020106052B8104000A04273025020101042009a11f0a52a073ae647c25530d4fff4b7dc856c1881e2e4eaa9e48d0fdc31902");
        byte[] publicByteAlice = Numeric.hexStringToByteArray("3056301006072A8648CE3D020106052B8104000A03420004b058a08fa24c2db7535694b4edf4a65dce38654c11a66f94f46f887a6759d56a738810f4008401065301ccd9782e2df5564f2bd6929f2c90e796f67bd8e3b67b");
        byte[] privateByteBob = Numeric.hexStringToByteArray("303E020100301006072A8648CE3D020106052B8104000A042730250201010420789b0040a50fa658ed7a3ada3497baa655a6d57e4ed80a1884d2bb07b99dffd5");
        byte[] publicByteBob = Numeric.hexStringToByteArray("3056301006072A8648CE3D020106052B8104000A03420004131a091ded6054c6d7163d9ae63fa93dbe9f6c1f2747dda4353c087d8ca88f97e12ffc8a4a43f89c8d482137ca81a392f2c20de014b718148f6ea71fdf02b593");
        byte[] secretByte1= getSecretKey(publicByteBob,privateByteAlice);
        System.out.println("secret:"+new BigInteger(1,secretByte1).toString(16));
        byte[] secretByte2= getSecretKey(publicByteAlice,privateByteBob);
        System.out.println("secret:"+new BigInteger(1,secretByte2).toString(16));

    }

}
