package com.github.app.bean;

import android.os.ParcelUuid;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class BleBean extends BaseBean {
    private String name;
    private ParcelUuid[] uuid;

    public ParcelUuid[] getUuid() {
        return uuid;
    }

    public void setUuid(ParcelUuid[] uuid) {
        this.uuid = uuid;
    }

    public BleBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BleBean(String name) {
        this.name = name;
    }
}
