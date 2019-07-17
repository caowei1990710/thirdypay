package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Repository.PlatformDepositRespositpory;
import com.example.demo.Repository.UserListRespositpory;
import com.example.demo.utils.DateUitil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Deposit;

import java.util.List;

@Service
public class DepositService {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);

    @Autowired
    private DepositRespositpory depositRepository;

    @Autowired
    private PlatformDepositService platformDepositService;

    @Autowired
    private PlatformDepositRespositpory platformDepositRespositpory;

    @Autowired
    private UserListRespositpory userListRespositpory;

    public Result toMatch(String depositNumber) {
        if (depositMatchToMatch(depositNumber)){
            return ResultUtil.success("订单匹配成功");
        }else {
            return ResultUtil.error(401, "订单匹配失败");
        }
    }

    public Boolean depositMatchToMatch(String depositNumber) {
        logger.info("toMatch depositNumber:{}",depositNumber);
        Deposit deposit = depositRepository.findByDepositnumber(depositNumber);
        logger.info("toMatch deposit:{}",deposit);
        if (deposit != null) {
            UserList userList = userListRespositpory.getUserListByRealname(deposit.getPayAccount());
            logger.info("toMatch userList:{}",userList);
            try{
                depositRepository.save(depositMatch(userList,deposit));
                logger.info("toMatch depositSave:{}:",deposit);
                if (platformDepositService.depositCallBack(deposit)) {
                    deposit.setState("SUCCESS");
                    depositRepository.save(deposit);
                    platformDepositService.updateByOrderno(deposit);
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            logger.info("订单匹配失败没有找到相应收款记录");
            return false;
        }
        logger.info("depositMatch success");
        return true;
    }

    public Deposit depositMatch (UserList userList,Deposit deposit){
        Deposit depositNew = deposit;
        if (userList != null) {
            List<PlatformDeposit> platformDepositLsit = platformDepositRespositpory.getPlatformDepositByAmountAccount(String.valueOf(deposit.getAmount()),deposit.getPayAccount(), DateUitil.rollMinute(deposit.getCreatTime(),-15),DateUitil.rollMinute(deposit.getCreatTime(),15));
            logger.info("订单匹配 platformDepositLsit:{}",platformDepositLsit);
            if (platformDepositLsit.size() == 0){
                return depositNew;
            }else {
                depositNew.setUserName(userList.getUserName());
                depositNew.setPayBankCard(userList.getBankCard());
                PlatformDeposit platformDeposit = platformDepositLsit.get(0);
                depositNew.setCallUrl(platformDeposit.getCallbackurl());
                depositNew.setOrderno(platformDeposit.getOrderno());
                logger.info("最终完整的回调订单deposit:{}" + depositNew);
                return depositNew;
            }
        }else{
            logger.info("订单匹配失败 userList为空");
            return depositNew;
        }
    }

}
