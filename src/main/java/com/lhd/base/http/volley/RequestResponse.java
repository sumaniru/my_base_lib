package com.lhd.base.http.volley;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface RequestResponse {
    void success(int id, String response);

    void error(int id, VolleyError error, String cache);
}
