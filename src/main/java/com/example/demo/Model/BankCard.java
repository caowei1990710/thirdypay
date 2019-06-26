package com.example.demo.Model;

/**
 * Created by snsoft on 24/6/2019.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class BankCard implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String bankcard;
    private String bankname;
    private String bankaddress;
    private String bankType;
    private String state;
    private Date creatTime;
    private Date updateTime;
    private static final long serialVersionUID = 7247714666080613254L;

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BankCard() {
    }
}
