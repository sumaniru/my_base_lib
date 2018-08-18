package com.lhd.base.interfaces;

import com.lhd.base.main.BaseBean;

public interface AdapterRetrofitResponse {

    void success(int id, BaseBean bean, int position);

    void error(int id, Throwable throwable, int position);

}
