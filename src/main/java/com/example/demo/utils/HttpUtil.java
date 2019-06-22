package com.example.demo.utils;

import org.apache.commons.lang.StringUtils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

public class HttpUtil {

    private HttpUtil() {
    }

    /**
     * 发送GET请求
     *
     * @param url        发送请求的URL
     * @param queryParas 参数集合
     * @return
     */
    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    /**
     * 发送GET请求
     *
     * @param url 发送请求的URL
     * @return 所代表远程资源的响应结果
     */
    public static String get(String url) {
        return get(url, null, null);
    }

    /**
     * 发送GET请求
     *
     * @param url        发送请求的URL
     * @param queryParas 参数集合
     * @param headers    头集合
     * @return 所代表远程资源的响应结果
     */
    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            //获取HttpURLConnection连接并设置参数
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
            // 建立HttpURLConnection实际的连接
            conn.connect();
            //返回  定义BufferedReader输入流来读取URL的响应
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 发送POST请求
     *
     * @param url     发送请求的URL
     * @param data    http请求包的包体
     * @param headers 头集合
     * @return 所代表远程资源的响应结果
     */
    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    /**
     * 发送POST请求
     *
     * @param url        发送请求的URL
     * @param queryParas 请求参数(写在url后面的参数)如http://www.baidu.com?state=1&data=233
     * @param data       http请求包的包体
     * @param headers    头集合
     * @return
     */
    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            //获取HttpURLConnection连接并设置参数
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            // 建立HttpURLConnection实际的连接
            conn.connect();
            // 获取URLConnection对象对应的输出流
            OutputStream out = conn.getOutputStream();
            // 发送请求参数
            out.write(data.getBytes("utf-8"));
            // flush输出流的缓冲
            out.flush();
            //关闭输出流
            out.close();
            //返回  定义BufferedReader输入流来读取URL的响应
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 获取HttpURLConnection连接并设置参数
     *
     * @param url     发送请求的URL
     * @param method  请求方式(POST/GET)
     * @param headers 请求头
     * @return 所代表远程资源的响应结果
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        //将url转变为URL对象
        URL _url = new URL(url);
        //打开URL连接
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        //设置请求方式
        conn.setRequestMethod(method);
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);

        //连接时长
        conn.setConnectTimeout(20000);
        //读取时长
        conn.setReadTimeout(60000);

        // 设置通用的请求属性
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    /**
     * 将参数和URL地址拼接
     *
     * @param url        发送请求的URL如http://www.baidu.com {"state":"1","data":"拼接Url"}
     * @param queryParas 传递的参数
     * @return
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        //判断URL是否有 ?
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }
        //遍历请求参数
        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                //对value进行转码
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    /**
     * 读取URL的响应
     *
     * @param conn HttpURLConnection对象
     * @return
     */
    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            //获取响应内容
            inputStream = conn.getInputStream();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 使用finally块来关闭输入流
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }


}
