package com.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

/**
 * MD5加密处理工具类
 *
 * @author Administrator
 */

public class MD5Utils {
    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Utils.class.getName()
                    + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    /**利用MD5进行加密*/
//  public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//    //确定计算方法
//    MessageDigest md5=MessageDigest.getInstance("MD5");
//    BASE64Encoder base64en = new BASE64Encoder();
//    //加密后的字符串
//    String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//    return newstr;
//  }

    /**
     * 生成字符串的md5校验值
     *
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配
     *
     * @param password  要校验的字符串
     * @param md5PwdStr 已知的md5校验码
     * @return
     */
    public static boolean isEqualsToMd5(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 生成文件的md5校验值
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 生成字节数组的md5校验值
     *
     * @param
     * @return
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 将源字符串使用MD5加密为字节数组
     *
     * @param source
     * @return
     */
    public static byte[] encode2bytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("GB2312"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将源字符串使用MD5加密为32位16进制数
     *
     * @param source
     * @return
     */
    public static String encode2hex(String source) {
        byte[] data = encode2bytes(source);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * 验证字符串是否匹配
     *
     * @param unknown 待验证的字符串
     * @param okHex   使用MD5加密过的16进制字符串
     * @return 匹配返回true，不匹配返回false
     */
    public static boolean validate(String unknown, String okHex) {
        return okHex.equals(encode2hex(unknown));
    }

    public static String buildSignStrFromParams(Map<String, Object> params, boolean filterNull) {
        if (params == null) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer("");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                String value = params.get(key) == null ? "" : params.get(key).toString();
                if (StringUtils.isBlank(value) && filterNull) {// 过滤掉空参数，不将其拼接到目标字符串中
                    continue;
                } else {
                    buffer.append(key).append("=").append(value).append("&");
                }
            }
            buffer.deleteCharAt(buffer.length() - 1);
            return buffer.toString();
        }
    }

    public static String md5(String text, String inputCharset) throws Exception {
        return DigestUtils.md5Hex(text.getBytes(inputCharset));
    }


}