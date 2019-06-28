package com.example.demo.Controller;

import com.example.demo.Service.BankcardService;
import com.example.demo.Service.PlatformDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PlatformDepositController {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);


    @Autowired
    PlatformDepositService platformDepositService;

    @RequestMapping(value = "/receiveDepositrq", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiveDepositrq(HttpServletRequest request) {
        return platformDepositService.receiveDepositrq(request);
    }


}
