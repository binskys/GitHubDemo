package com.github.app.utils;

import java.io.Serializable;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class DataBean implements Serializable{
    private String title;
    private String describe;
    private String address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DataBean(String title, String describe, String address) {
        this.title = title;
        this.describe = describe;
        this.address = address;
    }
}
