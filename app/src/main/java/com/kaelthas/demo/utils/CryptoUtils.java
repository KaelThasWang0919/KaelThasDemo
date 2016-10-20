package com.kaelthas.demo.utils;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by KaelThas.Wang on 2016/10/20.
 * E_mail KaelThas.Wang0919@gmail.com
 *
 *
 * 字符串加密类
 */

public class CryptoUtils {

    private static byte[] _key;
    private static byte[] _iv;

    /**
     * 得到当前所使用的密钥，如未指定，则返回默认值("mstarc")
     *
     * @return
     */
    public static byte[] getKey() {
        if (_key == null)
            _key = new byte[]{109, 115, 116, 97, 114, 99};
        return _key; // default value is: "mstarc"
    }

    /**
     * 设置加解密所使用的密钥，为16字节（128位）
     *
     * @param key 128位的密钥
     */
    public static void setKey(byte[] key) {
        _key = key;
    }

    /**
     * 设置加解密所使用的初始向量，为16字节（等同于加密的块大小）
     *
     * @param iv 16字节的初始向量
     */
    public static void setIV(byte[] iv) {
        _iv = iv;
    }

    /**
     * 得到当前所使用的IV，如未指定，则返回默认值(16字节0)
     *
     * @return
     */
    public static byte[] getIV() {
        if (_iv == null)
            _iv = new byte[]{(byte) 255, (byte) 222, (byte) 128, 10, 6, 5, 8, (byte) 159, 11, 45, 87, 78, 34, 67, 93,
                    62};
        return _iv;
    }

    /**
     * 加密一段字符串,
     *
     * @param cleartext 明文字符串
     * @return 加密后的数据
     * @throws Exception 加密时的异常
     */
    public static byte[] encryptString(String cleartext) throws Exception {
        byte[] result = encrypt(cleartext.getBytes());
        return result;
    }

    /**
     * 解密一段数据并以字符串形式返回
     *
     * @param encrypted 密文
     * @return 解密后的文本
     * @throws Exception 发生的异常
     */
    public static String decryptToString(byte[] encrypted) throws Exception {

        byte[] result = decrypt(encrypted);
        return new String(result);
    }

    /**
     * 加密一段数据
     *
     * @param clear 待加密的数据
     * @return 加密后的结果
     * @throws Exception 加密时出现的异常
     */
    public static byte[] encrypt(byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(getKey(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(getIV()));



        byte[] finalblock = cipher.doFinal(clear, 0, clear.length);

        return finalblock;
    }

    /**
     * 解密一段密文数据
     *
     * @param encryptedData 密文数据
     * @return 解密后的明文
     * @throws Exception 发生的异常
     */
    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(getKey(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        // Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(getIV()));



        byte[] finalblock = cipher.doFinal(encryptedData, 0, encryptedData.length);
        return finalblock;
    }

    /**
     * 文本转为16进制字符串
     *
     * @param txt
     * @return
     */
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    /**
     * 16进制字符串转为文本
     *
     * @param hex
     * @return
     */
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    /**
     * 16进制字符串转为数组
     *
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    /**
     * 数组转为16进制字符串
     *
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    /**
     * 数组转为16进制字符串,做定长输出，用来记录LOG
     *
     * @param buf
     * @param length
     * @return String
     */
    public static String toHex(byte[] buf, int length) {
        if (buf == null)
            return "";
        if (buf.length < length)
            return "";

        StringBuffer result = new StringBuffer(2 * length);
        for (int i = 0; i < length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

	/*
     * private static byte[] getRawKey(byte[] seed) throws Exception {
	 * KeyGenerator kgen = KeyGenerator.getInstance("AES"); SecureRandom sr =
	 * SecureRandom.getInstance("SHA1PRNG"); sr.setSeed(seed); kgen.init(128,
	 * sr); // 192 and 256 bits may not be available SecretKey skey =
	 * kgen.generateKey(); byte[] raw = skey.getEncoded(); return raw; }
	 */

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * @category MD5加密
     */
    @SuppressLint("DefaultLocale")
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString().toUpperCase(Locale.ENGLISH);
    }

    @SuppressLint("DefaultLocale")
    public static String md5Low(String string) {
        return md5(string).toLowerCase(Locale.ENGLISH);
    }
}
