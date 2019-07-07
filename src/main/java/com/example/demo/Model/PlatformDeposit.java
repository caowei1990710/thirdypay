package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformDeposit {
    @Id
    @GeneratedValue
    private int id;
    private String banktype;
    private String bankkey;
    private String account;
    private String merchantno;
    private String amount;
    private String orderno;
    private String callbackurl;
    private String sign;
    private String status;
    private String acceptDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getBankkey() {
        return bankkey;
    }

    public void setBankkey(String bankkey) {
        this.bankkey = bankkey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {  this.status = status; }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    @Override
    public String toString() {
        return "PlatformDeposit{" +
                "id=" + id +
                ", banktype='" + banktype + '\'' +
                ", bankkey='" + bankkey + '\'' +
                ", account='" + account + '\'' +
                ", merchantno='" + merchantno + '\'' +
                ", amount='" + amount + '\'' +
                ", orderno='" + orderno + '\'' +
                ", callbackurl='" + callbackurl + '\'' +
                ", sign='" + sign + '\'' +
                ", status='" + status + '\'' +
                ", acceptDate='" + acceptDate + '\'' +
                '}';
    }
}
