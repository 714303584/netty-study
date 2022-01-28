package io.netty.example.uptime;

//
//import com.credits.duiba.service.BuildTool;
//import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RsaEncrypt {

    /**
     * 加密公钥
     * */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ/W78bFvzIQDvNEDnJhf7Y4U0U0zZcQhLEe7cf4NWGCdEpcCKe5RcCyVKgM2mY8pPR0HNLTqvXSCUSqASXjB03nowScjl6vDhkav/2AialJH/oXEb+yjESN4hHZxs8rBu8coc5f/iTt0gMzkokuGIsXUP0k8wlPlX0z8VZJujIQIDAQAB";

    /**
     * 解密私钥
     * */
    private static final String PRIVATE_KEY = "MIICegIBADANBgkqhkiG9w0BAQEFAASCAmQwggJgAgEAAoGBAIn9bvxsW/MhAO80QOcmF/tjhTRTTNlxCEsR7tx/g1YYJ0SlwIp7lFwLJUqAzaZjyk9HQc0tOq9dIJRKoBJeMHTeejBJyOXq8OGRq//YCJqUkf+hcRv7KMRI3iEdnGzysG7xyhzl/+JO3SAzOSiS4YixdQ/STzCU+VfTPxVkm6MhAgMBAAECgYASmjLTHryKm0Fn/fBd+Pm0rybu9effTGikzicYXKxU9+6bR4kcYiqO+gWt9I1EyCEm7OFHCrjlmTViKGktfUk0mkds6wsMwKVBAchc3VYgLrGIo0XMN3SzI02Gk/INqzj6nwdHvDK0MTxP5ZHfPTLpMKL2mUBOmMUMnphvn5q9vQJFANMK7AxW/HyJOdu7HweT1Wf6Z4U7LLPmUZxpayyacmZkVFAtL5rApHXFUkQkDE0NzjYkf/fYu8R+J7Pv9Rk1yzMdsbDTAj0Ap2KhHUkGdlimSTbSQuvMUmxhS7NwoB7v2oI1EdtKchj1jLgaK0hxbXkx55EmQhxLhbDxhciUF4g4LwO7AkQptHPXMG1u0tNrN7w3DCDVDmfcrUf1OSORjEeQpejLkTIUMgnBRCZ724WZT6Jaq8q/alEbkmnwt5ly9o1x8O556HlP3wI9AKS/NsSUM4AYENLO2nRzkU8uzTStEJUI3hq7PXCt3cAfQM+YBGnFi56sQqog+v7myo5zccSzfs8KjAH6MQJEVMekrPScswi03zcdb5FaCprC4izbpFCHc5eEwrwFqPNcwhx3iypRtPhPm9JbdfEzWsdxjSqj4P4tVFEVIG/wKaoW5PI=";
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    public static void main(String[] args) throws Exception {
        String ss2 = "FlR/doGGhzsry2zGDfaYWxIL5fmMiIvVIktAGSRgYSLpZ26IoXcYAm6rC1jGR3d3UJESFrsHz17lxujvZHAgKpfMziNw0ADWcney3AGVlQ0B7hW5jLSiE3q+NQ3xJokfmeT9XPHM0stHd4n+57jYKxrGFqjTsLzjNzl9+4wpxc0=";


//        System.out.println("解密= "+decrypt( ss2,PRIVATE_KEY ));
//        String encrypt(ss2,PUBLIC_KEY);
//        System.out.println();
//        System.out.println("解密= "+decrypt( ss2,PRIVATE_KEY ));

    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) {
        String result = "";
        try {
            // 将Base64编码后的公钥转换成PublicKey对象
            byte[] buffer = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey rsaPublic = keyFactory.generatePublic(keySpec);
            // 加密
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, rsaPublic);
            byte[] inputArray = str.getBytes();
            int inputLength = inputArray.length;
            System.out.println("加密字节数：" + inputLength);
            // 标识
            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            result = Base64.getEncoder().encodeToString(resultBytes);
        } catch (Exception e) {
            System.out.println("rsaEncrypt error:" + e.getMessage());
        }
        return result;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = inputByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(inputByte, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(inputByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        String result = out.toString();
        out.close();

        return result;
    }

}