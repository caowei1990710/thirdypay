package com.example.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Model.*;
import com.example.demo.Service.BankcardService;
import com.example.demo.Service.PlatformDepositService;
import com.example.demo.utils.GoogleAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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



    @GetMapping(value = "/getQr")
    public Result getQr(String value) {
        return bankcardService.setQr(value);
    }

    @GetMapping(value = "/deleteuserid")
    public Result deleteuserid(String id) {
        return bankcardService.deleteUserlist(id);
    }

    @GetMapping(value = "/getAuthictor")
    public Result getCtor(@RequestParam("code") String code, @RequestParam("secret") String secret) {
        long t = System.currentTimeMillis();
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(5);
        boolean r = ga.check_code(secret, Long.parseLong(code), t);
        System.out.println("检查code是否正确？" + r);
        if (r)
            return ResultUtil.success(200, "验证成功");
        else
            return ResultUtil.error(400, "验证失败");
    }

    @RequestMapping(value = "/getbanklist", method = {RequestMethod.GET})
    public Result addUserList() {
        return bankcardService.getAllUserlist();
    }

    @RequestMapping(value = "/addbank", method = {RequestMethod.POST})
    public Result addUserList(UserList userList) {
        return bankcardService.addUserList(userList);
    }

    @RequestMapping(value = "/addmorebank", method = {RequestMethod.POST})
    public Result addMoreUserList(UserList userList) {
        return bankcardService.addMoreUserList(userList);
    }

    //    @RequestMapping(value = "/updatemorebank", method = {RequestMethod.POST})
//    public Result updateMoreUserList(UserList userList) {
//        return bankcardService.addMoreUserList(userList);
//    }
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

    /**
     * 创建日期:2018年4月6日<br/>
     * 代码创建:黄聪<br/>
     * 功能描述:通过request的方式来获取到json数据<br/>
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/json/setTurnOn", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result getByJSON(@RequestBody JSONObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toJSONString());

//        // 将获取的json数据封装一层，然后在给返回
//        JSONObject result = new JSONObject();
//        result.put("msg", "ok");
//        result.put("method", "json");
//        result.put("data", jsonParam);
//        jsonParam.get("remark");
//        jsonParam.get("depositNumber");
        String depositNumber = "", remark = "", result = "";
        if (jsonParam.get("depositNumber") != null)
            depositNumber = jsonParam.get("depositNumber").toString();
        if (jsonParam.get("remark") != null)
            remark = jsonParam.get("remark").toString();
        if (jsonParam.get("result") != null)
            result = jsonParam.get("result").toString();
        return bankcardService.setNewStringTurn(depositNumber, remark, result);
    }

    @RequestMapping(value = "/getTurnOnin", method = {RequestMethod.GET})
    public Result seturnon(@RequestParam("depositNumber") String depositNumber, @RequestParam("remark") String remark) {
        return bankcardService.setStringTurn(depositNumber, remark);
    }

    @RequestMapping(value = "/setProposal", method = {RequestMethod.POST})
    public Result receiveDepositr(PayProposal payProposal) {
        return bankcardService.setProposal(payProposal);
    }

    @RequestMapping(value = "/getProposal", method = {RequestMethod.GET})
    public Result getDepositr() {
        return bankcardService.getProposal();
    }

    @RequestMapping(value = "/getProposalid", method = {RequestMethod.GET})
    public Result getDepositrid(@RequestParam("proposalId") String proposalId) {
        return bankcardService.getProposalitem(proposalId);
    }

    @RequestMapping(value = "/updateProposal", method = {RequestMethod.GET})
    public Result updateDepositr(@RequestParam("remark") String remark, @RequestParam("proposalId") String proposalId) {
        return bankcardService.updateProposal(remark, proposalId);
    }

    @RequestMapping(value = "/receiveDepositr", method = {RequestMethod.POST})
    public Result receiveDepositr(TurnOn turnOn) {
        return bankcardService.setTurnOn(turnOn);
    }

    @RequestMapping(value = "/postNotice", method = {RequestMethod.POST})
    public Result postNotice(Notice notice) {
        return bankcardService.postNotice(notice);
    }

    @RequestMapping(value = "/getNotice", method = {RequestMethod.GET})
    public Result getNotice(@RequestParam("title") String title) {
        return bankcardService.getNotice(title);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public Result login(User user) {
        return bankcardService.login(user.getUserName(), user.getPassWord());
    }
}
