package com.record.basic.core.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

//import org.apache.commons.codec.binary.Base64;

/**
 * AES 加密  16进制结果
 */
public class EncryptStrUtil {

    /**
     * KeyGenerator的方式，通过传入种子串生成key，进行加密
     * @param seed 生成key传入的种子串
     * @param toEncryptStr 要加密的字节数组
     * @return 返回 Base64 的加密字符串
     */
    public static String encrypt(String seed, byte[] toEncryptStr) {
        try {
            return Base64.getEncoder().encodeToString(getCipher(seed, Cipher.ENCRYPT_MODE).doFinal(toEncryptStr));  //此时使用的 Base64 编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据传入的二进制 key数组，返回AES的十六进制加密串
     * @param keyBytes
     * @param toEncryptStr
     * @return
     */
    public static String encrypt(byte[] keyBytes, byte[] toEncryptStr) {
        try {
            return BinHexSwitchUtil.bytesToHexString(getCipher(keyBytes, Cipher.ENCRYPT_MODE).doFinal(toEncryptStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * KeyGenerator的方式，通过传入种子串生成key，进行解密
     * @param seed 生成key传入的种子串
     * @param encryptedStr 要解密的字节数组，Base64加密的
     * @return 返回 解密的字节数组
     */
    public static byte[] decrypt(String seed, byte[] encryptedStr) {
        try {
            return getCipher(seed, Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(encryptedStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据传入的 二进制 key数组，将16进制加密串 解密
     * @param keyBytes
     * @param encryptedStr 要解密的字符串
     * @return 已解密的二进制数组
     */
    public static byte[] decrypt(byte[] keyBytes, String encryptedStr) {
        try {
            return getCipher(keyBytes, Cipher.DECRYPT_MODE).doFinal(BinHexSwitchUtil.hexStringTobytes(encryptedStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * KeyGenerator 的方式生成key，获取密码生成器
     * @param seed 传入的种子 字符串
     * @param encryptMode 传入加密模式、解密模式
     * @return 返回密码生成器
     * @throws Exception
     */
    private static Cipher getCipher(String seed, int encryptMode) throws Exception {
        //生成加密随机数
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        //并设置seed
        random.setSeed(seed.getBytes());
        //创建AES生产者
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        //初始化生产者，128位
        generator.init(128, random);

        Key key=generator.generateKey();
        // 返回基本编码格式的密钥(初始key)，如果此密钥不支持编码，则返回null。
//        byte[] enCodeFormat = generator.generateKey().getEncoded();
//
//        SecretKey key = new SecretKeySpec(enCodeFormat, "AES");//应该没有必要再次生成一次 SecretKey
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        //初始化解码器,这里根据是加密模式还是解码模式
        cipher.init(encryptMode, key);
        return cipher;
    }

    /**
     * SecretKey的方式生成key，根据传入的二进制数组 获取密码生成器
     * @param keyBytes
     * @param encryptMode
     * @return
     * @throws Exception
     */
    private static Cipher getCipher(byte[] keyBytes, int encryptMode) throws Exception {

        SecretKey key = new SecretKeySpec(keyBytes, "AES");//根据传入的二进制数组 生成SecretKey
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        //初始化解码器,这里根据是加密模式还是解码模式
        cipher.init(encryptMode, key);
        return cipher;
    }




    public static void main(String[] args) {
        String key="SE389C4750142R4ET2083368KEY15BB3";
        String str="nihaoma";
  /*      String miStr=EncryptStrUtil.encrypt(key.getBytes(),str.getBytes());
        System.out.println(miStr);
        byte[] noMiStr=EncryptStrUtil.decrypt(key.getBytes(),miStr);
        System.out.println(Arrays.toString(noMiStr));
        System.out.println(new String(noMiStr));*/
        String videoKey = "28efc526f11fe608116443f700daf957";
        String str2 = Base64.getEncoder().encodeToString("fe416990-8b16-4d63-8610-a4fd2dda4c4b_427B0DBC694CC92CFD52868CA1D47075".getBytes());
        String miStr2=EncryptStrUtil.encrypt(videoKey.getBytes(),str2.getBytes());
        System.out.println(miStr2);
    }
}