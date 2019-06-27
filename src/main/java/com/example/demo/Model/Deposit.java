package com.example.demo.Model;

/**
 * Created by snsoft on 16/6/2019.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by snsoft on 26/9/2017.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deposit implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty(message = "提案号")
    @Column(name = "deposit_number", unique = true)
    private String depositNumber;
    private Date tranTime;
    private Date creatTime;
    private Date excuteTime;
    private String transferTime;
    private String note;
    private Double amount;
    private Double tranfee;
    @NotEmpty(message = "账号")
    private String wechatName;
    private String nickName;
    private String state;
    private String billNo;
    private String platfrom;
    private Integer times;
    private String createUser;
    private String payAccount;
    private String ip;
    private String userRemark;
    private String payType;
    private String callUrl;
    private String sign;
    private String platfromName;
    private String userName;//游戏账号
    private String success;
    private String payBankCard;//付款卡号
    private String orderno;//平台订单号
    private String callbackurl;//支付成功回调请求地址

    public String getPayBankCard() {
        return payBankCard;
    }

    public void setPayBankCard(String payBankCard) {
        this.payBankCard = payBankCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private static final long serialVersionUID = 7247714666080613254L;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getPlatfromName() {
        return platfromName;
    }

    public void setPlatfromName(String platfromName) {
        this.platfromName = platfromName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPlatfrom() {
        return platfrom;
    }

    public void setPlatfrom(String platfrom) {
        this.platfrom = platfrom;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumer) {
        this.depositNumber = depositNumer;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(Date excuteTime) {
        this.excuteTime = excuteTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amuount) {
        this.amount = amuount;
    }

    public Double getTranfee() {
        return tranfee;
    }

    public void setTranfee(Double tranfee) {
        this.tranfee = tranfee;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public Deposit() {
        this.creatTime = new Date();
        this.tranTime = new Date();
        this.excuteTime = new Date();
        this.state = "PENDING";
        this.billNo = (new Date()).getTime() + "";
        this.times = 0;
        this.success = "1";
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", depositNumber='" + depositNumber + '\'' +
                ", tranTime=" + tranTime +
                ", creatTime=" + creatTime +
                ", excuteTime=" + excuteTime +
                ", transferTime='" + transferTime + '\'' +
                ", note='" + note + '\'' +
                ", amount=" + amount +
                ", tranfee=" + tranfee +
                ", wechatName='" + wechatName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", state='" + state + '\'' +
                ", billNo='" + billNo + '\'' +
                ", platfrom='" + platfrom + '\'' +
                ", times=" + times +
                ", createUser='" + createUser + '\'' +
                ", payAccount='" + payAccount + '\'' +
                ", ip='" + ip + '\'' +
                ", userRemark='" + userRemark + '\'' +
                ", payType='" + payType + '\'' +
                ", callUrl='" + callUrl + '\'' +
                ", sign='" + sign + '\'' +
                ", platfromName='" + platfromName + '\'' +
                ", userName='" + userName + '\'' +
                ", success='" + success + '\'' +
                ", payBankCard='" + payBankCard + '\'' +
                ", orderno='" + orderno + '\'' +
                ", callbackurl='" + callbackurl + '\'' +
                '}';
    }
}