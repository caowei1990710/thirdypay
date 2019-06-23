package com.example.demo.test;

import com.alibaba.fastjson.JSON;
import com.example.demo.Model.Deposit;
import com.example.demo.Model.Result;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Service.BankcardService;
import com.example.demo.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloContorller {

    @Autowired
    private BankcardService bankcardService;

    @Autowired
    private DepositRespositpory depositRepository;

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(HelloContorller.class);


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String say() {
        Deposit deposit =new Deposit();
        deposit.setUserName("test456");
        deposit.setAmount(11.00);
        deposit.setDepositNumber("20191204095656228481186709966627151200.00");
//        String result = bankcardService.depositCallBack(deposit);
//        String result = bankcardService.checkAccount(deposit);
        return "Hello Fuck =" + bankcardService.depositCallBack(deposit);
    }

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello() {
        Map map = new HashMap<String, String>();
        map.put("name", "張三");
        map.put("age", "1034");
        String url = "http://localhost:8081/hello";
        logger.info("url:" + url);
        logger.info("queryParas:" + map.toString());
        String result = HttpUtil.get(url, map);
        return result;
    }

    @RequestMapping(value = "/helloWord", method = RequestMethod.GET)
    public String helloWord() {
        List list = depositRepository.getByDepositList();
        logger.info("list:" + list);
        return "Hello Word";
    }
}
