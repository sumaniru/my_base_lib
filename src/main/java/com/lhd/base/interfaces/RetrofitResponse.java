package com.lhd.base.interfaces;

import com.lhd.base.main.BaseBean;

public interface RetrofitResponse {

    void success(int id, BaseBean bean);

    void error(int id, Throwable throwable);

}
