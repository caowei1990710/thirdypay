package com.example.demo.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.Model.Deposit;
import com.example.demo.Model.PlatformDeposit;
import com.example.demo.Model.Result;
import com.example.demo.Model.ResultUtil;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Repository.PlatformDepositRespositpory;
import com.example.demo.utils.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PlatformDepositService implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(PlatformDepositService.class);

    private final String signKey1 = "8bb4bf843e284fc8b602f5faba77f29f" ; // 第四方1
    private final String signKey2 = "ba2feee447ab4c2da26347415057f133" ; // 第四方2
    private final String signKey3 = "2b92e24705d5421ca0fecd4edd338c76" ; // 第四方3

    private final String urlStr1 = "http://www.d1186.com/tranfrom.html" ; // 第四方1
    private final String urlStr2 = "http://xhcpzf1.com/tranfrom.html" ; // 第四方2
    private final String urlStr3 = "http://pay500cp.com/tranfrom.html" ; // 第四方3

    private String merchantno = null;

    @Autowired
    private PlatformDepositRespositpory platformDepositRespositpory;

    @Autowired
    private DepositRespositpory depositRepository;

    public String receiveDepositrq(HttpServletRequest request, HttpServletResponse response) {

        String strUrl = "";

        Map<String, String[]> maps = request.getParameterMap();
        Map<String, Object> dataMaps = new HashMap<>();
        for (Map.Entry<String, String[]> entry : maps.entrySet()) {
            dataMaps.put(entry.getKey(), Arrays.toString(entry.getValue()));
        }

        String banktype = String.valueOf(dataMaps.get("banktype")).replace("[", "").replace("]", "");
        String bankkey = String.valueOf(dataMaps.get("bankkey")).replace("[", "").replace("]", "");
        String account = String.valueOf(dataMaps.get("account")).replace("[", "").replace("]", "");
        merchantno = String.valueOf(dataMaps.get("merchantno")).replace("[", "").replace("]", "");
        String amount = String.valueOf(dataMaps.get("amount")).replace("[", "").replace("]", "");
        String orderno = String.valueOf(dataMaps.get("orderno")).replace("[", "").replace("]", "");
        String callbackurl = String.valueOf(dataMaps.get("callbackurl")).replace("[", "").replace("]", "");
        String sign = String.valueOf(dataMaps.get("sign")).replace("[", "").replace("]", "");

        PlatformDeposit platformDeposit = new PlatformDeposit();
        platformDeposit.setBanktype(banktype);
        platformDeposit.setBankkey(bankkey);
        platformDeposit.setAccount(account);
        platformDeposit.setMerchantno(merchantno);
        platformDeposit.setAmount(amount);
        platformDeposit.setOrderno(orderno);
        platformDeposit.setCallbackurl(callbackurl);
        platformDeposit.setSign(sign);
        platformDeposit.setAcceptDate(DateUitil.newDate());
        logger.info("platformDeposit:{}",platformDeposit.toString());

        List<PlatformDeposit> platformDepositList = platformDepositRespositpory.getPlatformDeposit(orderno);
        if (platformDepositList.size() > 0) {
            return "订单号已存在";
        }

        platformDeposit.setStatus("PENDING");
        platformDepositRespositpory.save(platformDeposit);

        try {
            String urlStr = urlChoice(merchantno);
            logger.info("url:" + urlStr);

            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            /**
             * 3.设置请求方式
             * 4.设施请求内容类型
             */
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);


            /**
             * 7.使用输入流接受数据
             */
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//此处可以用Stringbuffer等接收
            byte[] b = new byte[1024];
            int len = 0;
            while (true) {
                len = inputStream.read(b);
                if (len == -1) {
                    break;
                }
                byteArrayOutputStream.write(b, 0, len);
            }
//            request.getRequestDispatcher(byteArrayOutputStream.toString()).forward(request, response);
            strUrl = byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        strUrl = strUrl.substring(0,strUrl.lastIndexOf("id="));
        strUrl = strUrl + "id=" + account + ","+amount+","+orderno+","+callbackurl+"\"";
        strUrl = strUrl + "</script><head><meta charset=\"UTF-8\"><title>Title</title></head><body></body></html>";
        logger.info("strUrl:"+strUrl);
        return strUrl;
    }

    //第二方:回调接口
    public boolean depositCallBack(Deposit deposit) {
        Map map = new HashMap<String, Object>();
        map.put("account", deposit.getUserName());                                                      //中博所属系统下的会员账号
        map.put("orderno", deposit.getOrderno());                                                       //平台订单号
        map.put("amount", deposit.getAmount().toString());                                              //实际金额
        map.put("tradeno", deposit.getDepositNumber());                                                 //外链订单号,此次交易中外链系统的订单ID
        map.put("tradestatus", "1".equals(deposit.getSuccess()) ? "success" : "fail");                  //订单结果
        String key = keyChoice(merchantno);
        String sign = QfpayUtil.mapACSIIrank2(map, key);
        logger.info("签名原串:" + sign);
        sign = DESUtil.desEncrypt(sign,key);
        logger.info("对称加密:" + sign);
        try {
            String signMd5 = MD5Utils.md5(sign, "UTF-8");
            logger.info("Md5加密:" + signMd5);
            map.put("sign", signMd5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("actionType", "outreachpay");                                                           //回调参数:outreachpay

        String url = deposit.getCallUrl();
        logger.info("callbackurl:" + url);
        logger.info("queryParas" + map.toString());
        boolean issuccess =  false;
        try{
            issuccess = checksuccessful(HttpUtil.doPost(url, map));
        }catch (Exception e){
            e.printStackTrace();
        }
        return issuccess;
    }

    //第二方:平台订单成功，改变状态
    public void updateByOrderno(Deposit deposit) {
        List<PlatformDeposit> list = platformDepositRespositpory.getPlatformDeposit(deposit.getOrderno());
        if(list.size() > 0){
            for (PlatformDeposit platformDeposit:list) {
                platformDeposit.setStatus("SUCCESS");
                platformDepositRespositpory.save(platformDeposit);
                logger.info("platformDeposit:{}",platformDeposit);
            }
        }
    }

    //仅第二方使用
    private boolean checksuccessful(String result) {
        logger.info("result:" + result);
//        Map maps = (Map) JSON.parse(result);
        /*if ("TRUE".equals(maps.get("status").toString().toUpperCase())) {
            return true;
        }*/

        if (result.toLowerCase().contains("success")){
            return true;
        }
        return false;
    }


    public Result getPlatformDepositList() { return ResultUtil.success(platformDepositRespositpory.getPlatformDepositList()); }

    public Result platformDepositRetry(String orderno) {
        logger.info("platformDepositRetry orderno:{}:",orderno);
        Deposit deposit = depositRepository.getDepositByOrderno(orderno);
        logger.info("platformDepositRetry deposit:{}:",deposit);
        if (deposit == null)
            return ResultUtil.error(401, "重发失败，没有匹配到支付金额信息,请确认是否收到款");
        if (!depositCallBack(deposit)) {
            updateByOrderno(deposit);
            deposit.setState("SUCCESS");
            depositRepository.save(deposit);
            return ResultUtil.error(401, "重发失败，请确认该笔订单是否已成功");
        }else {
            return ResultUtil.success("重发成功");
        }
    }

    private String keyChoice(String merchantno){
        if ("1561293939922".equals(merchantno)){
            return signKey1;

        }else if ("1563108605588".equals(merchantno)){
            return signKey2;
        }else if ("1563199322562".equals(merchantno)){
            return signKey3;
        }
        return signKey1;
    }
    private String urlChoice(String merchantno){
        if ("1561293939922".equals(merchantno)){
            return urlStr1;

        }else if ("1563108605588".equals(merchantno)){
            return urlStr2;
        }else if ("1563199322562".equals(merchantno)){
            return urlStr3;
        }
        return urlStr1;
    }
}
