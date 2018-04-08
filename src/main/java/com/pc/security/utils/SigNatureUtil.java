package com.pc.security.utils;

import sun.misc.BASE64Encoder;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;


public class SigNatureUtil {

    public static String ACCESSKEY_SECRET = "ddm2.0secret";

    public static String PC_PARAM_KEY = "YMJjZGVmZ2hpOmXsbW0TcHFyc6R1dnd3";

//    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
//        Mac mac = Mac.getInstance("HmacSHA1");
//        SecretKey secretKey = new SecretKeySpec(encryptKey.getBytes(), "HmacSHA1");
//        mac.init(secretKey);
//        byte[] text = encryptText.getBytes();
//        byte[] bytes = mac.doFinal(text);
//        return bytes;
//    }
//    private static String getSign(Map<String, String> params) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        int size = 1;
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//
//            //对每个参数和值进行encode，对字符进行转义
//            String key = URLEncoder.encode(entry.getKey(), "UTF-8");
//            String value = URLEncoder.encode(entry.getValue(), "UTF-8");
//
//            builder.append(key + "=" + value);
//            if (size != params.size()) {
//                builder.append("&");
//            }
//            size++;
//        }
//        String stringToSign = URLEncoder.encode(builder.toString(), "UTF-8");
//
//        byte[] bytes =HmacSHA1Encrypt(stringToSign, ACCESSKEY_SECRET + "&");
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(bytes);
//    }

    //    private static String getSign(String inputStr) throws Exception {
//        MessageDigest md5=null;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            return null;
//        }
//        StringBuilder builder = new StringBuilder(inputStr);
//        char[] charArray = inputStr.toCharArray(); //将字符串转换为字符数组
//        byte[] byteArray = new byte[charArray.length]; //创建字节数组
//
//        for (int i = 0; i < charArray.length; i++){
//            byteArray[i] = (byte) charArray[i];
//        }
//
//        //将得到的字节数组进行MD5运算
//        byte[] md5Bytes = md5.digest(byteArray);
//
//        StringBuffer md5Str= new StringBuffer();
//
//        for (int i = 0; i < md5Bytes.length; i++){
//            if (Integer.toHexString(0xFF & md5Bytes[i]).length() == 1)
//                md5Str.append("0").append(Integer.toHexString(0xFF & md5Bytes[i]));
//            else
//                md5Str.append(Integer.toHexString(0xFF & md5Bytes[i]));
//        }
//
//        return md5Str.toString();
//    }
    public static String encodeECB(String data)
            throws Exception {

        byte[] key = new BASE64Decoder().decodeBuffer(SigNatureUtil.PC_PARAM_KEY);
        return new BASE64Encoder().encode(des3EncodeECB(key, data.getBytes("UTF-8"))).replaceAll("[\\s*\t\n\r]", "");
    }

    public static String decodeECB(String data)
            throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(SigNatureUtil.PC_PARAM_KEY);
        return new String(SigNatureUtil.ees3DecodeECB(key, new BASE64Decoder().decodeBuffer(data)), "UTF-8");
    }

    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }


    public static byte[] ees3DecodeECB(byte[] key, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }
}