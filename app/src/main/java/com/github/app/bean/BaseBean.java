package com.github.app.bean;

import java.io.Serializable;

/**
 * Created by benny
 * on 2017/10/18.
 */

public class BaseBean  implements Serializable {
    protected String title;
    protected String describe;
    protected String address;
    protected int tag;

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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
