package com.example.demo.Controller;

import com.example.demo.Model.Result;
import com.example.demo.Service.BankcardService;
import com.example.demo.Service.PlatformDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PlatformDepositController {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);


    @Autowired
    PlatformDepositService platformDepositService;

    @RequestMapping(value = "/receiveDepositrq.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiveDepositrq(HttpServletRequest request, HttpServletResponse response) {
        return platformDepositService.receiveDepositrq(request,response);
    }

    @RequestMapping(value = "/getPlatformDepositList", method = {RequestMethod.GET})
    public Result getPlatformDepositList() { return platformDepositService.getPlatformDepositList(); }

    @RequestMapping(value = "/platformDepositRetry", method = {RequestMethod.GET,RequestMethod.POST})
    public Result platformDepositRetry(@RequestParam("orderno") String orderno) {
        return platformDepositService.platformDepositRetry(orderno);
    }

}
