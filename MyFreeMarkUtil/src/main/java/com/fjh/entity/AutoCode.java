package com.fjh.entity;
    import java.lang.Integer;
    import java.lang.Float;
    import java.util.Date;

/**
* ClassName:AutoCode
* Description:
* date: 2019-9-9 19:43:10
*/
public class AutoCode {

    /**
    * 主键
    */
    private Integer id;
    /**
    * 姓名
    */
    private String name;
    /**
    * 价格
    */
    private Float price;
    /**
    * 生日
    */
    private Date birth;
    /**
    * 用户名
    */
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}