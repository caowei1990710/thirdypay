package com.example.demo.Controller;

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
}
