package com.example.demo.timedtask;

import com.example.demo.Service.BankcardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@ComponentScan(basePackages = "com.example.demo.timedtask")
public class HeartBeat {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);

//    每分钟启动
    @Scheduled(cron = "0 0/1 * * * ?")
    public void timerToNow(){
        String times = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("HeartBeat now time:" + times);
        logger.info("HeartBeat now time:" + times);
    }



}
