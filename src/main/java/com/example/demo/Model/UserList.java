package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by snsoft on 22/6/2019.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList implements Serializable {
    private static final long serialVersionUID = 7247714666080613264L;
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String realName;
    @NotEmpty(message = "银行卡号")
    @Column(name = "bank_card", unique = true)
    private String bankCard;
    private String bankName;
    private String secondrealName;
    @Column(name = "secondbank_card", unique = true)
    private String secondbankCard;
    private String secondbankName;
    private String thirdrealName;
    @Column(name = "thirdbank_card", unique = true)
    private String thirdbankCard;
    private String thirdbankName;
    private String state;
    private String callbackurl;//回调地址
    private String orderno;//订单号

    public String getSecondrealName() {
        return secondrealName;
    }

    public void setSecondrealName(String secondrealName) {
        this.secondrealName = secondrealName;
    }

    public String getThirdrealName() {
        return thirdrealName;
    }

    public void setThirdrealName(String thirdrealName) {
        this.thirdrealName = thirdrealName;
    }

    public String getSecondbankCard() {
        return secondbankCard;
    }

    public void setSecondbankCard(String secondbankCard) {
        this.secondbankCard = secondbankCard;
    }

    public String getSecondbankName() {
        return secondbankName;
    }

    public void setSecondbankName(String secondbankName) {
        this.secondbankName = secondbankName;
    }

    public String getThirdbankCard() {
        return thirdbankCard;
    }

    public void setThirdbankCard(String thirdbankCard) {
        this.thirdbankCard = thirdbankCard;
    }

    public String getThirdbankName() {
        return thirdbankName;
    }

    public void setThirdbankName(String thirdbankName) {
        this.thirdbankName = thirdbankName;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", state='" + state + '\'' +
                ", callbackurl='" + callbackurl + '\'' +
                ", orderno='" + orderno + '\'' +
                '}';
    }

    public UserList() {
        this.state = "NORMAL";
    }
}
