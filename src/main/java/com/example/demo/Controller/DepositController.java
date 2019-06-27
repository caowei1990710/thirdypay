package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Service.BankcardService;
import com.example.demo.Service.DepositService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by snsoft on 14/6/2019.
 */
@RestController
public class DepositController {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);

    @Autowired
    BankcardService bankcardService;

    @Autowired
    DepositService depositService;

    @RequestMapping(value = "/Depositlist", method = {RequestMethod.POST})
    public Result addDepositlist(@RequestBody DepositList depositList) {
//        try {
        ArrayList<Deposit> recordItems = depositList.getDepositRecords();
        return bankcardService.saveDepositList(depositList);
//        } catch (Exception e) {
//            throw new GirlException("添加失败", -1);
//        }

    }

    @RequestMapping(value = "/setDefault", method = {RequestMethod.GET})
    public Result setDefaultbankcard() {
        return bankcardService.setDefault();
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

    @RequestMapping(value = "/updatebankitem", method = {RequestMethod.POST})
    public Result updateBankCard(BankCard bankcard) {
        return bankcardService.updateBankCard(bankcard);
    }

    @RequestMapping(value = "/getbankitem", method = {RequestMethod.GET})
    public Result getBankCard() {
        return bankcardService.getAllBankCard();
    }

    @RequestMapping(value = "/getbankitemstate", method = {RequestMethod.GET})
    public Result getBankCardState(String state) {
        return bankcardService.getStateBankCard(state);
    }

    //    @RequestMapping(value="/setdefault", method = {RequestMethod.GET} )
//    public Result setDefault(){
//        return bankcardService.
//    }
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

    @RequestMapping(value = "/depositresend", method = {RequestMethod.GET})
    public Result depositresend(@RequestParam("depositid") String depositid) {
        return bankcardService.depositresend(depositid);
    }

    @RequestMapping(value = "/getTurnOn", method = {RequestMethod.GET})
    public Result getturnon(String depositNumber) {
        return bankcardService.getTurnOn(depositNumber);
    }

    @RequestMapping(value = "/setTurnOn", method = {RequestMethod.POST})
    public Result seturnon(TurnOn turnOn) {
        return bankcardService.setTurnOn(turnOn);
    }

    @RequestMapping(value = "/receiveDepositr", method = {RequestMethod.POST})
    public Result receiveDepositr(TurnOn turnOn) {
        return bankcardService.setTurnOn(turnOn);
    }

    @RequestMapping(value = "/receiveDepositrq", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiveDepositrq(HttpServletRequest request) {
        return depositService.receiveDepositrq(request);
    }


}
