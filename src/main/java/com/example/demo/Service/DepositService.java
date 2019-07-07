package com.example.demo.Service;

import com.example.demo.Model.Deposit;
import com.example.demo.Model.Result;
import com.example.demo.Model.ResultUtil;
import com.example.demo.Model.UserList;
import com.example.demo.Repository.DepositRespositpory;
import com.example.demo.Repository.UserListRespositpory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Deposit;

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
    private UserListRespositpory userListRespositpory;

    public Result toMatch(String depositNumber) {
        logger.info("toMatch depositNumber:{}:",depositNumber);
        Deposit deposit = depositRepository.findByDepositnumber(depositNumber);
        logger.info("toMatch deposit:{}:",deposit);
        if (deposit != null) {
            UserList userList = userListRespositpory.getUserListByRealname(deposit.getPayAccount());
            logger.info("toMatch userList:{}:",userList);
            try{
                if (userList != null) {
                        deposit.setUserName(userList.getUserName());
                        deposit.setPayBankCard(userList.getBankCard());
                        deposit.setCallUrl(userList.getCallbackurl());
                        deposit.setOrderno(userList.getOrderno());
                }else{
                    return ResultUtil.error(401, "订单匹配失败");
                }
                depositRepository.save(deposit);
                logger.info("toMatch depositSave:{}:",deposit);
                if (platformDepositService.depositCallBack(deposit)) {
                    deposit.setState("SUCCESS");
                    depositRepository.save(deposit);
                    platformDepositService.updateByOrderno(deposit);
                }
            }catch (Exception e){
                return ResultUtil.error(401, "订单匹配失败");
            }
        }else {
            return ResultUtil.error(401, "订单匹配失败");
        }
        logger.info("toMatch success");
        return ResultUtil.success("订单匹配成功");
    }

}
