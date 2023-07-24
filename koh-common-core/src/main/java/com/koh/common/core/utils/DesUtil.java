package com.koh.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * 算法：DES  模式：ECB算法模式   填充：PKCS5Padding（自动填充）
 * @author Qiang.zhang
 * @version 1.0.0
 * @see
 */
public class DesUtil {

    private static Logger logger = LoggerFactory.getLogger(DesUtil.class);

    //DESede   DES   YYYYYYYYYYYYYYYYYYYYYYYY
    private final static String DES = "DES";
    private final static String ENCODE = "utf-8";
    private final static String OPERMODE = "DES/CBC/PKCS5Padding";

    public static void main(String[] args) {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("A", "111");
        map.put("B", "111");
        System.out.println(map);
        String S1 = encrypt(map.toString(), "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println(S1);
        Map<String,Object> map1 = new LinkedHashMap<>();
        map1.put("B", "111");
        map1.put("A", "111");
        System.out.println(map1);
        String S2 = encrypt(map1.toString(), "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println(S2);
        System.out.println(S1.equals(S2));
    }
    /**
     * 使用 默认key 加密
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:46:43
     */
    public static String encrypt(String data,String key) {
        byte[] bt = new byte[0];
        String encodeData = "";
        try {
            bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
            //字节转化为16进制字符串
            encodeData = byteToHexString(bt);
        } catch (Exception e) {
            logger.error("----des encrypt error",e);
        }

        return encodeData;
    }

    /**
     * 使用 默认key 解密
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午02:49:52
     */
    public static String decrypt(String data,String key) {
        String decryptString = "";
        try {
            byte[] bt = decrypt(hexStringToBytes(data), key.getBytes(ENCODE));
            decryptString = new String(bt, ENCODE);
        } catch (Exception e) {
            logger.error("---decrypt error",e);
        }
        return decryptString;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

       /* // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);*/

        Cipher cipher = Cipher.getInstance(OPERMODE);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, new IvParameterSpec(dks.getKey()));


        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(OPERMODE);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, new IvParameterSpec(dks.getKey()));

/*
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);*/

        return cipher.doFinal(data);
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * JAVA获得0-9,a-z,A-Z范围的随机数
     *
     * @param length 随机数长度
     * @return String
     */

    public static String getRandomChar(int length) {

        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',

                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        Random random = new Random();

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {

            buffer.append(chr[random.nextInt(62)]);

        }
        return buffer.toString();
    }
}
