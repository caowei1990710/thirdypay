package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Repository.UserListRespositpory;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by snsoft on 16/6/2019.
 */
@Service
public class BankcardService {
    @Autowired
    private DepositRespositpory depositRepository;
    @Autowired
    private UserListRespositpory userListRespositpory;

    public Result saveDepositList(DepositList depositList) {
        for (int i = 0; i < depositList.getDepositRecords().size(); i++) {
            if (depositList.getDepositRecords() != null) {
                Deposit deposit = depositList.getDepositRecords().get(i);

//                deposit.setIp(list.get(0).getIp());
//                    if (list.get(0).getWechatId().equals("9")) {
//                        WechatItem wechatItem = wechatitemRepository.findbyName(deposit.getWechatName(), deposit.getNote());
//                        deposit.setNickName(wechatItem.getNickName());
//                        wechatItem.setNickName("default");
//                        wechatItem.setLastUsetime(new Date());
//                        wechatItem.setOverTime(new Date());
//                        wechatitemRepository.save(wechatItem);
//                    }
                if (depositRepository.findByDepositnumber(deposit.getDepositNumber()) == null) {
                    List<UserList> userList = userListRespositpory.findByRealName(deposit.getPayAccount());
//                    String username = "";
                    if (userList.size() > 0){
                        deposit.setUserName(userList.get(0).getUserName());
                        deposit.setPayBankCard(userList.get(0).getBankCard());
                    }
//                        username = userList.get(0).getUserName();
                    depositRepository.save(deposit);
                }
            }
        }
        return ResultUtil.success("创建成功");
    }

    public Result getDepositList() {
        return ResultUtil.success(depositRepository.findByDepositList());
    }

    public Result addUserList(UserList userList) {
        //userListRespositpory
        List<UserList> itemuser = userListRespositpory.findByBankCard(userList.getBankCard());
        if (itemuser.size() == 0)
            return ResultUtil.success(userListRespositpory.save(userList));
        else
            return ResultUtil.error(401, "卡号已绑定");
    }

    public Result getUserList(String userName) {
        return ResultUtil.success(userListRespositpory.findByUserName(userName));
    }
}
