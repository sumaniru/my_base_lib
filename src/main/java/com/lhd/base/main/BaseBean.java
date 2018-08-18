package com.lhd.base.main;

import java.io.Serializable;

/**
 * Created by mac on 17/12/9.
 */

public class BaseBean implements Serializable{

    private int viewType = -1;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
