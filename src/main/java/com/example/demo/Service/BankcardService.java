package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.example.demo.utils.GoogleAuthenticator;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.MD5Utils;
import com.example.demo.utils.QfpayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by snsoft on 16/6/2019.
 */
@Service
public class BankcardService {

    /**
     * Logger实例
     */
    static final Logger logger = LoggerFactory.getLogger(BankcardService.class);

    @Autowired
    private DepositRespositpory depositRepository;
    @Autowired
    private UserListRespositpory userListRespositpory;
    @Autowired
    private BankCardRespositpory bankcardRespositpory;
    @Autowired
    private TurnOnRespositpory trunonrespositpory;
    @Autowired
    private PlatformDepositService platformDepositService;
    @Autowired
    private UserRepositpory userrepositpory;
    @Autowired
    private NoticeRespositpory noticeRespositpory;
    @Autowired
    private PayProposalRespositpory payProposalRespositpory;

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
                    UserList userList = userListRespositpory.findByRealName(deposit.getPayAccount());
                    if (userList == null)
                        userList = userListRespositpory.findBysecondrealName(deposit.getPayAccount());
                    if (userList == null)
                        userList = userListRespositpory.findBythirdrealName(deposit.getPayAccount());
//                    String username = "";
                    if (userList != null) {
                        deposit.setUserName(userList.getUserName());
                        deposit.setPayBankCard(userList.getBankCard());
                        deposit.setCallUrl(userList.getCallbackurl());
                        deposit.setOrderno(userList.getOrderno());
                    }
//                        username = userList.get(0).getUserName();
                    depositRepository.save(deposit);
                    if (platformDepositService.depositCallBack(deposit)) {
                        deposit.setState("SUCCESS");
                        depositRepository.save(deposit);
                    }
                }

            }
        }
        return ResultUtil.success("创建成功");
    }

    public Result getDepositList() {
        return ResultUtil.success(depositRepository.findByDepositList());
    }

    public Result postNotice(Notice notice) {
        Notice noticeitem = noticeRespositpory.findbytitle(notice.getTitle());
        if (noticeitem == null)
            noticeRespositpory.save(notice);
        else {
            noticeitem.setContent(notice.getContent());
            noticeRespositpory.save(noticeitem);
        }
        return ResultUtil.success("成功");
    }

    public Result getNotice(String title) {
        Notice noticeitem = noticeRespositpory.findbytitle(title);
//        if (noticeitem == null)
//            noticeRespositpory.save(notice);
//        else {
//            noticeitem.setContent(notice.getContent());
//            noticeRespositpory.save(noticeitem);
//        }
        return ResultUtil.success(noticeitem);
    }

    public Result addUserList(UserList userList) {
        //userListRespositpory
        UserList itemuser = userListRespositpory.findByRealName(userList.getRealName());
        if (itemuser == null)
            return ResultUtil.success(userListRespositpory.save(userList));
        else {
            return ResultUtil.error(401, "名字已经已绑定");
        }
    }

    public Result addMoreUserList(UserList userList) {
        UserList userlistitme = userListRespositpory.findByUserName(userList.getUserName());
        if (userlistitme == null)
            return ResultUtil.success(userListRespositpory.save(userList));
        UserList itemuserlist = null;
        if (userList.getRealName() != null) {
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findByRealName(userList.getRealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBysecondrealName(userList.getRealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBythirdrealName(userList.getRealName());

        } else if (userList.getSecondrealName() != null) {
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findByRealName(userList.getSecondrealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBysecondrealName(userList.getSecondrealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBythirdrealName(userList.getSecondrealName());
        } else if (userList.getThirdrealName() != null) {
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findByRealName(userList.getThirdrealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBysecondrealName(userList.getThirdrealName());
            if (itemuserlist == null)
                itemuserlist = userListRespositpory.findBythirdrealName(userList.getThirdrealName());
        }
        if (itemuserlist == null)
            return ResultUtil.success(userListRespositpory.save(updateUserListitem(userList, userlistitme)));
        else if (itemuserlist.getUserName().equals(userlistitme.getUserName()))
            return ResultUtil.success(userListRespositpory.save(updateUserListitem(userList, userlistitme)));
        else
            return ResultUtil.error(401, "名字已绑定");
//        if (itemuserlist == null)
//            return ResultUtil.success(userListRespositpory.save(userList));
//        else {
//            UserList useritem = itemuserlist;
//            if (userList.getUserName().equals(useritem.getUserName()))
//                return ResultUtil.success(userListRespositpory.save(updateUserListitem(userList, useritem)));
//            else
//                return ResultUtil.error(401, "名字已绑定");
//        }
    }

//    public Result updateMoreUserList(UserList userList) {
//        return ResultUtil.success();
//    }

    public UserList updateUserListitem(UserList userList, UserList useritem) {
        if (userList.getRealName() != null && !userList.getRealName().equals(""))
            useritem.setRealName(userList.getRealName());
        if (userList.getBankCard() != null && !userList.getBankCard().equals(""))
            useritem.setBankCard(userList.getBankCard());
        if (userList.getBankName() != null && !userList.getBankName().equals(""))
            useritem.setBankName(userList.getBankName());
        if (userList.getSecondrealName() != null && !userList.getSecondrealName().equals(""))
            useritem.setSecondrealName(userList.getSecondrealName());
        if (userList.getSecondbankCard() != null && !userList.getSecondbankCard().equals(""))
            useritem.setSecondbankCard(userList.getSecondbankCard());
        if (userList.getSecondbankName() != null && !userList.getSecondbankName().equals(""))
            useritem.setSecondbankName(userList.getSecondbankName());
        if (userList.getThirdrealName() != null && !userList.getThirdrealName().equals(""))
            useritem.setThirdrealName(userList.getThirdrealName());
        if (userList.getThirdbankCard() != null && !userList.getThirdbankCard().equals(""))
            useritem.setThirdbankCard(userList.getThirdbankCard());
        if (userList.getThirdbankName() != null && !userList.getThirdbankName().equals(""))
            useritem.setThirdbankName(userList.getThirdbankName());
        if (userList.getOrderno() != null && !userList.getOrderno().equals(""))
            useritem.setOrderno(userList.getOrderno());
        return useritem;
    }

    public Result updateUserList(UserList userList) {
        UserList itemuserList = userListRespositpory.findByUserName(userList.getUserName());
        if (itemuserList == null)
            return ResultUtil.error(401, "未绑定账号");
        UserList useritem = itemuserList;
        if (userList.getRealName() != null && !userList.getRealName().equals(""))
            useritem.setRealName(userList.getRealName());
        if (userList.getBankCard() != null && !userList.getBankCard().equals(""))
            useritem.setBankCard(userList.getBankCard());
        if (userList.getBankName() != null && !userList.getBankName().equals(""))
            useritem.setBankName(userList.getBankName());
        if (userList.getOrderno() != null && !userList.getOrderno().equals(""))
            useritem.setOrderno(userList.getOrderno());
        useritem.setCallbackurl(userList.getCallbackurl());
        return ResultUtil.success(userListRespositpory.save(useritem));
    }

    public Result setDefault() {
        BankCard bankCard = new BankCard();
        bankCard.setBankType("农业银行");
        bankCard.setBankname("济南瓦维商贸有限公司");
        bankCard.setBankaddress("济南市文苑支行");
        bankCard.setBankcard("15153201040004896");
        bankCard.setState("NORMAL");
        bankcardRespositpory.save(bankCard);
        BankCard bankCarditem = new BankCard();
        bankCarditem.setBankType("建设银行");
        bankCarditem.setBankname("广州琪其贸易部");
        bankCarditem.setBankaddress("广州南国花园支行");
        bankCarditem.setBankcard("44050158051100001542");
        bankCarditem.setState("NORMAL");
        bankcardRespositpory.save(bankCarditem);
        return ResultUtil.success("初始化成功");
    }

    public Result getUserList(String userName) {
        return ResultUtil.success(userListRespositpory.findByUserName(userName));
    }

    public Result getAllUserlist() {
        return ResultUtil.success(userListRespositpory.findByUserList());
    }

    public Result deleteUserlist(String id) {
        userListRespositpory.delete(Integer.valueOf(id));
        return ResultUtil.success("删除成功");
    }

    public Result setQr(String value) {
        User agent = userrepositpory.findByUserNameitem(value);
        if (agent == null)
            return ResultUtil.error(400, "未找到账号");
        String secret = GoogleAuthenticator.generateSecretKey();
        // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
        String qrcode = GoogleAuthenticator.getQRBarcode(value, secret);
        System.out.println("qrcode:" + qrcode + ",key:" + secret);
        agent.setPaySecret(secret);
        agent.setPayqr(qrcode);
        userrepositpory.save(agent);
        /**
         * 对app的随机生成的code,输入并验证
         */
        return ResultUtil.success(qrcode);
    }

    public Result updateUserList(Deposit deposit) {
        Deposit itemdeposit = depositRepository.findOne(deposit.getId());
        itemdeposit.setUserName(deposit.getUserName());
        itemdeposit.setPayAccount(deposit.getPayAccount());
        return ResultUtil.success(depositRepository.save(itemdeposit));
    }

    public Result updateBankCard(BankCard bankCard) {
        BankCard bankcarditem = bankcardRespositpory.findOne(bankCard.getId());
        if (bankcarditem == null)
            return ResultUtil.error(401, "账号不存在");
        bankcarditem.setBankaddress(bankCard.getBankaddress());
        bankcarditem.setBankcard(bankCard.getBankcard());
        bankcarditem.setBankname(bankCard.getBankname());
        bankcarditem.setBankType(bankCard.getBankType());
        bankcarditem.setState(bankCard.getState());
        bankcarditem.setUpdateTime(new Date());
        return ResultUtil.success(bankcardRespositpory.save(bankcarditem));
    }

    public Result getAllBankCard() {
        return ResultUtil.success(bankcardRespositpory.findAlllist());
    }

    public Result getStateBankCard(String state) {
        return ResultUtil.success(bankcardRespositpory.findStatelist(state));
    }

    //第四方回調接口
//    @RequestMapping(value = "/depositCallBack", method = RequestMethod.GET)
    public boolean depositCallBack(Deposit deposit) {
        Map map = new HashMap<String, String>();
        map.put("app_id", "1561293939922");                         //站长id，由中博支付分配
        map.put("account", deposit.getUserName());                  //中博所属系统下的会员账号
        map.put("money", deposit.getAmount().toString());                      //单位元（人民币），2位小数，最小支付金额为1.00
        map.put("order_no", deposit.getDepositNumber());            //站长系统订单号，该值需在商户系统内唯一，中博接口会校验该值是否唯一
        map.put("status", "0");                                     //该笔订单支付状态（0：支付成功 1：支付失败）
        map.put("type", "0");                                       //交易终端（0：PC 1：手机 2：APP）
        map.put("remark", "");
        map.put("tradeType", "0");                                  //会员支付所使用的交易类型（0：网银 1 微信 2支付宝 3QQ

        map.put("sign", md5Private(map, "8bb4bf843e284fc8b602f5faba77f29f"));

//      String url = "http://localhost:8081/hello";
        String url = "http://enoab4pay.abjxnow.com/fourth_payment_platform/pay/addMoney";
        logger.info("url:" + url);
        logger.info("queryParas" + map.toString());
        return checksuccessful(HttpUtil.get(url, map));
    }

    //第四方会员查询接口，校验有效账号
//    @RequestMapping(value = "/checkAccount ", method = RequestMethod.GET)
    public boolean checkAccount(Deposit deposit) {
        Map map = new HashMap<String, String>();
        map.put("app_id", "1561293939922");                         //站长id，由中博支付分配
        map.put("account", deposit.getUserName());                  //中博所属系统下的会员账号

        map.put("sign", md5Private(map, "8bb4bf843e284fc8b602f5faba77f29f"));
        String url = "http://enoab4pay.abjxnow.com/fourth_payment_platform/pay/checkAccount";
        logger.info("url:" + url);
        logger.info("queryParas" + map.toString());
        return checksuccessful(HttpUtil.get(url, map));
    }

    //第四方校验会员
    public Result checkStringAccount(String account) {
        Map map = new HashMap<String, String>();
        map.put("app_id", "1561293939922");                         //站长id，由中博支付分配
        map.put("account", account);                  //中博所属系统下的会员账号

        map.put("sign", md5Private(map, "8bb4bf843e284fc8b602f5faba77f29f"));
        String url = "http://enoab4pay.abjxnow.com/fourth_payment_platform/pay/checkAccount";
        logger.info("url:" + url);
        logger.info("queryParas" + map.toString());
        boolean isVailue = checksuccessful(HttpUtil.get(url, map));
        if (!isVailue)
            return ResultUtil.error(401, "不是有效会员");
        return ResultUtil.success("检测成功");
    }

    //存储客人存款信息
    public Result setDespositList() {
        return ResultUtil.success();
    }

    public Result setStringTurn(String depositNumber, String remark) {
        TurnOn turnOn = new TurnOn();
        turnOn.setDepositNumber(depositNumber);
        turnOn.setRemark(remark);
        TurnOn itemtrinOn = new TurnOn();
        itemtrinOn = trunonrespositpory.findBydepositNumber(depositNumber);
        if (itemtrinOn == null)
            return ResultUtil.success(trunonrespositpory.save(turnOn));
        itemtrinOn.setRemark(remark);
        trunonrespositpory.save(itemtrinOn);
        return ResultUtil.success("存储成功");
    }

    public Result setNewStringTurn(String depositNumber, String remark, String result) {
        TurnOn turnOn = new TurnOn();
        turnOn.setDepositNumber(depositNumber);
        turnOn.setRemark(remark);
        turnOn.setResult(result);
        TurnOn itemtrinOn = new TurnOn();
        itemtrinOn = trunonrespositpory.findBydepositNumber(depositNumber);
        if (itemtrinOn == null)
            return ResultUtil.success(trunonrespositpory.save(turnOn));
        itemtrinOn.setRemark(remark);
        itemtrinOn.setResult(result);
        trunonrespositpory.save(itemtrinOn);
        return ResultUtil.success("存储成功");
    }

    public Result setTurnOn(TurnOn turnOn) {
        TurnOn itemtrinOn = new TurnOn();
        itemtrinOn = trunonrespositpory.findBydepositNumber(turnOn.getDepositNumber());
        if (itemtrinOn == null)
            return ResultUtil.success(trunonrespositpory.save(turnOn));
        itemtrinOn.setRemark(turnOn.getRemark());
        trunonrespositpory.save(itemtrinOn);
        return ResultUtil.success("存储成功");
//        trunonrespositpory.findBydepositNumber(turnOn.getDepositNumber())
    }

    public Result setProposal(PayProposal payProposal) {
        payProposal.setRemark("default");
        return ResultUtil.success(payProposalRespositpory.save(payProposal));
    }

    public Result getProposal() {
//        payProposal.setRemark("default");
        return ResultUtil.success(payProposalRespositpory.getPlatformDeposit());
    }

    public Result getProposalitem(String proposalId) {
//        payProposal.setRemark("default");
        return ResultUtil.success(payProposalRespositpory.getPlatformDepositbydepositNumber(proposalId));
    }

    public Result updateProposal(String remark, String proposalId) {
        return ResultUtil.success(payProposalRespositpory.updatePlatformDeposit(remark, proposalId));
    }

    public Result getTurnOn(String depositNumber) {
//        TurnOn itemtrinOn = new TurnOn();
//        itemtrinOn = trunonrespositpory.findBydepositNumber(turnOn.getDepositNumber());
//        if (turnOn == null)
//            trunonrespositpory.save(turnOn);
//        itemtrinOn.setRemark(turnOn.getRemark());
//        trunonrespositpory.save(itemtrinOn);
        return ResultUtil.success(trunonrespositpory.findBydepositNumber(depositNumber));
//        trunonrespositpory.findBydepositNumber(turnOn.getDepositNumber())
    }

    private String md5Private(Map map, String secretKey) {
        String sign = QfpayUtil.mapACSIIrank(map, secretKey);
        logger.info("sign:" + sign);
        String signMD5 = "";
        try {
            signMD5 = MD5Utils.md5(sign, "GB2312");
            logger.info("signMD5:" + signMD5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signMD5;
    }

    private boolean checksuccessful(String result) {
        logger.info("result:" + result);
        Map maps = (Map) JSON.parse(result);
        if ("TRUE".equals(maps.get("status").toString().toUpperCase())) {
            return true;
        }
        return false;
    }

    public Result depositresend(String depositid) {
        if (!platformDepositService.depositCallBack(depositRepository.findByDepositnumber(depositid)))
            return ResultUtil.error(401, "重发失败，请确认该笔订单是否已成功");
        return ResultUtil.success("重发成功");
    }

    public Result login(String username, String password) {
        List<User> list = userrepositpory.findByUserName(username, password);
        if (list.size() > 0)
            return ResultUtil.success(list.get(0));
        else
            return ResultUtil.error(401, "账号名或密码错误");
    }
}
