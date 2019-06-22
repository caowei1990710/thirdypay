package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.BankcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by snsoft on 14/6/2019.
 */
@RestController
public class DepositController {
    @Autowired
    BankcardService bankcardService;

    @RequestMapping(value = "/Depositlist", method = {RequestMethod.POST})
    public Result addDepositlist(@RequestBody DepositList depositList) {
//        try {
        ArrayList<Deposit> recordItems = depositList.getDepositRecords();
        return bankcardService.saveDepositList(depositList);
//        } catch (Exception e) {
//            throw new GirlException("添加失败", -1);
//        }

    }

    @RequestMapping(value = "/depositlist", method = {RequestMethod.GET})
    public Result getDepositlist() {
//        try {
//        ArrayList<Deposit> recordItems = depositList.getDepositRecords();
        return bankcardService.getDepositList();
//        } catch (Exception e) {
//            throw new GirlException("添加失败", -1);
//        }

    }

    @RequestMapping(value = "/addbank", method = {RequestMethod.POST})
    public Result addUserList(UserList userList) {
        return bankcardService.addUserList(userList);
    }

    @RequestMapping(value = "/getbank", method = {RequestMethod.GET})
    public Result getUserList(String userName) {
        return bankcardService.getUserList(userName);
    }
}
