package com.test;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import org.web3j.crypto.ECKeyPair;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by mawenjing on 2017/10/25.
 */
public class ECCSample {
    public static final String KEY_ALGORITHM="EC";

    public static byte[] getSecretKey(byte[] publicKey,byte[] privateKey) throws Exception{


        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);

        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);

        //初始化私钥
        //密钥材料转换
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);
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
    //本地密钥算法，即对称加密算法。可选des，aes，desede
    public static final String SECRET_ALGORITHM="AESCBC";
    public static final String PRIVATE_KEY_HEADER =
            "303E020100301006072A8648CE3D020106052B8104000A042730250201010420";
    public static final String PUBLIC_KEY_HEADER =
            "3056301006072A8648CE3D020106052B8104000A03420004";
    public static final String BOB_PRIVATE_KEY_STRING =
            "09a11f0a52a073ae647c25530d4fff4b7dc856c1881e2e4eaa9e48d0fdc31902";
    static final String CANDY_PUBLIC_KEY_STRING =
            "131a091ded6054c6d7163d9ae63fa93dbe9f6c1f2747dda4353c087d8ca88f97e12ffc8a4a43f89c8d482137ca81a392f2c20de014b718148f6ea71fdf02b593";
    static final byte[] TEST_MESSAGE="This is an example of a signed message.".getBytes();
    public static void main(String[] args) throws InterruptedException , Exception{
        byte[] privateKey = Numeric.hexStringToByteArray(BOB_PRIVATE_KEY_STRING);
        System.out.println("PRIVATE_KEY_STRING: "+BOB_PRIVATE_KEY_STRING);//原始字符串
        System.out.println("privateKey:"+ new BigInteger(privateKey).toString(16));  //16进制输出private byte[]
        BigInteger priKey = Numeric.toBigInt(BOB_PRIVATE_KEY_STRING);//new BigInteger(privateKey)
        System.out.println(priKey);

/* publickey*/
        BigInteger pubKeyFromPriv = Sign.publicKeyFromPrivate(priKey);
        System.out.println(pubKeyFromPriv);
        System.out.println("pubKeyFromPriv:"+ pubKeyFromPriv.toString(16));

        ECKeyPair KEY_PAIR = new ECKeyPair(priKey, pubKeyFromPriv);
        /****************签名********************/
        Sign.SignatureData signatureData = Sign.signMessage(TEST_MESSAGE, KEY_PAIR);
        System.out.println("signatureData:");
        System.out.println(signatureData);



        byte[] privateByteAlice = Numeric.hexStringToByteArray(PRIVATE_KEY_HEADER +BOB_PRIVATE_KEY_STRING);
        byte[] publicByteBob = Numeric.hexStringToByteArray(PUBLIC_KEY_HEADER +CANDY_PUBLIC_KEY_STRING);
        byte[] secretByte1= getSecretKey(publicByteBob,privateByteAlice);
        System.out.println("secret:"+new BigInteger(1,secretByte1).toString(16));
        /*byte[] secretByte2= getSecretKey(publicByteAlice,privateByteBob);
        System.out.println("secret:"+new BigInteger(1,secretByte2).toString(16));
*/

    }
}
