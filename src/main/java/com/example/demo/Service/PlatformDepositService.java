package com.example.demo.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.Model.Deposit;
import com.example.demo.Model.PlatformDeposit;
import com.example.demo.Model.ResultUtil;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Repository.PlatformDepositRespositpory;
import com.example.demo.utils.DESUtil;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.MD5Utils;
import com.example.demo.utils.QfpayUtil;
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
import java.util.*;


@Service
public class PlatformDepositService implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(PlatformDepositService.class);

    @Autowired
    private PlatformDepositRespositpory platformDepositRespositpory;

    public String receiveDepositrq(HttpServletRequest request, HttpServletResponse response) {

        String strUrl = "";

        Map<String, String[]> maps = request.getParameterMap();
        Map<String, Object> dataMaps = new HashMap<>();
        for (Map.Entry<String, String[]> entry : maps.entrySet()) {
            dataMaps.put(entry.getKey(), Arrays.toString(entry.getValue()));
        }
        logger.info("receiveDepositrq maps：" + maps);
        System.out.println("receiveDepositrq maps:" + maps);

        List<NameValuePair> nvp = new ArrayList<NameValuePair>();
        nvp.add(new BasicNameValuePair("banktype", "sdgf"));

        logger.info("nvp:" + nvp.toString());
        String banktype = String.valueOf(dataMaps.get("banktype")).replace("[", "").replace("]", "");
        String bankkey = String.valueOf(dataMaps.get("bankkey")).replace("[", "").replace("]", "");
        String account = String.valueOf(dataMaps.get("account")).replace("[", "").replace("]", "");
        String merchantno = String.valueOf(dataMaps.get("merchantno")).replace("[", "").replace("]", "");
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

        List<PlatformDeposit> PlatformDeposit = platformDepositRespositpory.getPlatformDeposit(orderno);
        if (PlatformDeposit.size() > 0)
            return "订单号已存在";

        platformDepositRespositpory.save(platformDeposit);

        try {



            String data = "banktype=" + banktype + "&bankkey=" + bankkey + "&account=" + account + "&merchantno=" + merchantno + "&amount=" + amount + "&orderno=" + orderno + "&callbackurl=" + callbackurl + "&sign=" + sign;
            logger.info("url:" + "http://d1186.com/tranfrom.html?" + data);
            logger.info("url:" + "http://d1186.com/tranfrom.html?" + data);
//            URL url = new URL("http://d1186.com/bankDeposit.html?" + data);
            URL url = new URL("http://d1186.com/tranfrom.html?" + data);
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
        logger.info("====================================================");
        strUrl = strUrl.substring(0,strUrl.lastIndexOf("id="));
        strUrl = strUrl + "id=" + account + ","+amount+","+orderno+","+callbackurl+"\"";
        strUrl = strUrl + "</script><head><meta charset=\"UTF-8\"><title>Title</title></head><body></body></html>";
        logger.info("strUrl:"+strUrl);
        return strUrl;
    }

    public boolean depositCallBack(Deposit deposit) {
        Map map = new HashMap<String, Object>();
        map.put("account", deposit.getUserName());                                                      //中博所属系统下的会员账号
        map.put("orderno", deposit.getOrderno());                                                       //平台订单号
        map.put("amount", deposit.getAmount().toString());                                              //实际金额
        map.put("tradeno", deposit.getDepositNumber());                                                 //外链订单号,此次交易中外链系统的订单ID
        map.put("tradestatus", "1".equals(deposit.getSuccess()) ? "success" : "fail");                    //订单结果

        String sign = QfpayUtil.mapACSIIrank2(map, "8bb4bf843e284fc8b602f5faba77f29f");
        sign = DESUtil.desEncrypt(sign,"8bb4bf843e284fc8b602f5faba77f29f");
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
        logger.info("url:" + url);
        logger.info("queryParas" + map.toString());
        boolean issuccess =  false;
        try{
            issuccess = checksuccessful(HttpUtil.doPost(url, map));
        }catch (Exception e){
            e.printStackTrace();
        }
        return issuccess;
    }

    private boolean checksuccessful(String result) {
        logger.info("result:" + result);
        Map maps = (Map) JSON.parse(result);
        if ("TRUE".equals(maps.get("status").toString().toUpperCase())) {
            return true;
        }
        return false;
    }


}
