package com.example.demo.utils;
import com.example.demo.Service.PlatformDepositService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.HashMap;
import java.util.Map;

import static javax.swing.text.html.HTML.Attribute.DATA;


public class DESUtil {

    static final Logger logger = LoggerFactory.getLogger(PlatformDepositService.class);

    //String value(加密前字串), String orderNo (訂單號)
    public static String desEncrypt(String value, String orderNo) {
        String result = "";
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            orderNo = trancateRight(orderNo, 8);
            result = toHexString(encrypt(value, orderNo)).toUpperCase();
        } catch (Exception ex) {
            logger.error("des encrypt is error: ", ex);
            return "";
        }
        return result;
    }

    private static byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("ASCII"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("ASCII"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }


    public static String trancateRight(String value, int len){
        if (value == null || len < 1){
            return "";
        }
        if (value.length() < len){
            return value;
        }
        return StringUtils.substring(value, 0, len);
    }

    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }


    protected static String md5(String text, String inputCharset) throws Exception{
        return DigestUtils.md5Hex(text.getBytes(inputCharset));
    }

    //Test
    public static void main(String[] args) throws Exception {
        Map map = new HashMap<String, Object>();
        map.put("account", "test456");                                                      //中博所属系统下的会员账号
        map.put("orderno", "gfdgrdyty6556");                                                       //平台订单号
        map.put("amount", "10.1");                                              //实际金额
        map.put("tradeno", "201906181050622848118670966627111.00");                                                 //外链订单号,此次交易中外链系统的订单ID
        map.put("tradestatus","success");                    //订单结果
        map.put("actionType", "outreachpay");                                                           //回调参数:outreachpay
        String sign = QfpayUtil.mapACSIIrank2(map, "8bb4bf843e284fc8b602f5faba77f29f");

//        String data = "account=huiyuan01&amount=1&orderno=222111333789&tradeno=123456&tradestatus=success&87654321";
        String desKey = "12345678";

        String desEncrypt = desEncrypt(sign,desKey);
        System.out.println(DATA + ">>>DES 对称加密>>>" + desEncrypt);
        String signMd5 = md5(desEncrypt, "UTF-8");
        System.out.println(DATA + ">>>DES 加密结果>>>" + signMd5);


    }
}
