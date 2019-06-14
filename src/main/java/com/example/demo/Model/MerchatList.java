package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * Created by snsoft on 14/6/2019. 商户号列表
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchatList {
    private static final long serialVersionUID = 7247714666080613244L;
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty(message = "账号为空")
    @Column(name = "name", unique = true)
    private String name;
    private String nickName;
    private Double amount;
    private Double remark;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRemark() {
        return remark;
    }

    public void setRemark(Double remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MerchatList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", amount=" + amount +
                ", remark=" + remark +
                ", state='" + state + '\'' +
                '}';
    }
}
