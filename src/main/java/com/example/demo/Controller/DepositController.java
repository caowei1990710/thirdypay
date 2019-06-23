package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Service.BankcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/updatebank", method = {RequestMethod.PUT})
    public Result updateDeposit(Deposit deposit) {
        return bankcardService.updateUserList(deposit);
    }

    @RequestMapping(value = "/getbank", method = {RequestMethod.POST})
    public Result addUserList(@RequestParam("userName") String userList) {
        return bankcardService.getUserList(userList);
    }

    @RequestMapping(value = "/addbank", method = {RequestMethod.POST})
    public Result addUserList(UserList userList) {
        return bankcardService.addUserList(userList);
    }
    @RequestMapping(value = "/updateuser", method = {RequestMethod.PUT})
    public Result updateUserList(UserList userList) {
        return bankcardService.updateUserList(userList);
    }
    @RequestMapping(value = "/checkaccount", method = {RequestMethod.GET})
    public Result getUserList(@RequestParam("userName") String userName) {
        return bankcardService.checkStringAccount(userName);
    }
}
