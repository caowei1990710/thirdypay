package com.example.demo.Service;

import com.example.demo.Model.Deposit;
import com.example.demo.Model.DepositList;
import com.example.demo.Model.Result;
import com.example.demo.Model.ResultUtil;
import com.example.demo.Repository.DepositRespositpory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by snsoft on 16/6/2019.
 */
@Service
public class BankcardService {
    @Autowired
    private DepositRespositpory depositRepository;
    public Result saveDepositList(DepositList depositList) {
        for (int i = 0; i < depositList.getDepositRecords().size(); i++) {
            if (depositList.getDepositRecords() == null) {
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
                depositRepository.save(deposit);
            }
        }
        return ResultUtil.success("创建成功");
    }
    public Result getDepositList(){
        return ResultUtil.success( depositRepository.findByDepositList());
    }
}
