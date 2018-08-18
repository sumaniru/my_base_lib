package com.lhd.base.http.retrofit.exception;


import com.lhd.base.main.BaseApplication;
import com.lhd.base.utils.ToastTool;

public class ApiException extends RuntimeException {

    private int code;


    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));
        ToastTool.showToast(BaseApplication.getContext(), message);
    }
}