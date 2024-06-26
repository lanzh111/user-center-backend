package com.lan.userCenter.utills;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA256Util {
    public static final String KEY_MAC = "Hmacsha256";   //HmacMD5  更换加密方式;

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    static String encodeBase64(byte[] source) throws Exception {
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static String encryptHMAC2String(String data, String key) throws Exception {
        byte[] b = encryptHMAC(data.getBytes("UTF-8"), key);
        return byteArrayToHexString(b);
    }

    public  static  String encrypt(String data,String key){
        String encryptHMAC2String =null;
        try {
             encryptHMAC2String = HmacSHA256Util.encryptHMAC2String(data, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptHMAC2String;
    }
}
