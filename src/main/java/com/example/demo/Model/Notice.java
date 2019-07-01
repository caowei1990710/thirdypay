package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by snsoft on 1/7/2019.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notice implements Serializable {
    private static final long serialVersionUID = 724771466608032154L;
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty(message = "标题")
    @Column(name = "title", unique = true)
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Notice() {
        this.title = "ads";
        this.content = "因业务需求，入款卡已更新为济南瓦维商贸有限公司，请您认清最新入款卡进行充值，感谢您的配合。华彩祝您生活愉快，盈利多多。";
    }
}
