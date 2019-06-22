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
    private String state;

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
                '}';
    }

    public UserList() {
        this.state = "NORMAL";
    }
}
