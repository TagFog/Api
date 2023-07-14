package com.example.word.entity;

public class Data {
    public String user;
    public String sex;
    public String para;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    @Override
    public String toString() {
        return "Data{" +
                "user='" + user + '\'' +
                ", sex='" + sex + '\'' +
                ", para='" + para + '\'' +
                '}';
    }

}
