package com.example.demo.Controller;

import com.example.demo.utils.MD5Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by snsoft on 14/6/2019.
 */
@RestController
public class HttpController {
    @RequestMapping("/test")
    public String forwardTest(){
        return "hi,test";
    }

    public static void main(String[] args) {

        String sign = "account=huiyuan01&amount=1&orderno=222111333789&tradeno=123456&tradestatus=success&87654321";
        try{

            System.out.println("md5加密结果:" + MD5Utils.md5(sign, "UTF-8"));
        }catch(Exception e){
            System.out.println("md5加密失败");
        }

    }
}
