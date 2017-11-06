package com.github.app.bean;

import java.io.Serializable;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class DataBean extends BaseBean{

    public DataBean(String title, String describe, String address, int tag) {
        this.title = title;
        this.describe = describe;
        this.address = address;
        this.tag = tag;
    }
}
