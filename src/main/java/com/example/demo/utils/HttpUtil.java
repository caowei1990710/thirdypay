package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

public class HttpUtil {

    static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    static final String charset = "UTF-8";


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
     * post请求
     */
    public static String doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    public static String doPost(String url, Map<String, Object> params) throws Exception {
        return doPost(url, params, null);
    }

    public static String doPost(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Post请求
            logger.debug(" protocol: POST");
            logger.debug("      url: " + url);
            HttpPost httpPost = new HttpPost(url.trim());
            // 设置参数
            logger.debug("   params: " + JSON.toJSONString(params));
            httpPost.setEntity(new UrlEncodedFormEntity(map2NameValuePairList(params), charset));
            // 设置Header
            if (header != null && !header.isEmpty()) {
                logger.debug("   header: " + JSON.toJSONString(header));
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPost);
        } catch (Exception e) {
            throw e;
        }
        logger.debug("   result: " + body);
        return body;
    }

    /**
     * postJson请求
     */
    public static String doPostJson(String url, Map<String, Object> params) throws Exception {
        return doPostJson(url, params, null);
    }

    public static String doPostJson(String url, Map<String, Object> params, Map<String, String> header) throws Exception {
        String json = null;
        if (params != null && !params.isEmpty()) {
            for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext(); ) {
                Entry<String, Object> entry = (Entry<String, Object>) it.next();
                Object object = entry.getValue();
                if (object == null) {
                    it.remove();
                }
            }
            json = JSON.toJSONString(params);
        }
        return postJson(url, json, header);
    }

    public static String doPostJson(String url, String json) throws Exception {
        return doPostJson(url, json, null);
    }

    public static String doPostJson(String url, String json, Map<String, String> header) throws Exception {
        return postJson(url, json, header);
    }

    private static String postJson(String url, String json, Map<String, String> header) throws Exception {
        String body = null;
        try {
            // Post请求
            logger.debug("      url: " + url);
            HttpPost httpPost = new HttpPost(url.trim());
            // 设置参数
            logger.debug("   params: " + json);
            httpPost.setEntity(new StringEntity(json, ContentType.DEFAULT_TEXT.withCharset(charset)));
            httpPost.setHeader(new BasicHeader("Content-Type", "application/json"));
            logger.debug("     type: JSON");
            // 设置Header
            if (header != null && !header.isEmpty()) {
                logger.debug("   header: " + JSON.toJSONString(header));
                for (Iterator<Entry<String, String>> it = header.entrySet().iterator(); it.hasNext(); ) {
                    Entry<String, String> entry = (Entry<String, String>) it.next();
                    httpPost.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                }
            }
            // 发送请求,获取返回数据
            body = execute(httpPost);
        } catch (Exception e) {
            throw e;
        }
        return body;
    }

    private static String execute(HttpRequestBase requestBase) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;
        try {
            CloseableHttpResponse response = httpclient.execute(requestBase);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    body = EntityUtils.toString(entity, charset);
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                throw e;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }
        return body;
    }

    private static List<NameValuePair> map2NameValuePairList(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (params.get(key) != null) {
                    String value = String.valueOf(params.get(key));
                    list.add(new BasicNameValuePair(key, value));
                }
            }
            return list;
        }
        return null;
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
