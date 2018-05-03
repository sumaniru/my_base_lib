package com.lhd.base.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lhd.base.main.BaseApplication;
import com.lhd.base.tools.ACache;
import com.lhd.base.tools.ToastTool;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by mac on 17/12/8.
 */

public class BaseRequest {

    public static void StringRequestPost(final Context mContext, final int id, final String url,
                                         String tag, final Map<String, String> params,
                                         final RequestResponse vif) {
        BaseApplication.getHttpQueues().cancelAll(tag);
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Logger.d(mContext.getResources().getString(id) + "==>" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getString("code").equals("0")) {
                        ACache.get(mContext).put(mContext.getResources().getString(id), s, ACache.TIME_DAY * 3);
                        ToastTool.showToast(mContext, jsonObject.getString("message"));
                    } else {
                        String cache = ACache.get(mContext).getAsString(mContext.getResources().getString(id));
                        if (TextUtils.isEmpty(cache)) {
                            vif.success(id, "");
                        } else {
                            vif.success(id, cache);
                        }
                    }
                } catch (JSONException e) {
                    Logger.e("error", "json解析出错");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logger.e(mContext.getResources().getString(id) + "==>" + volleyError.toString());
                String cache = ACache.get(mContext).getAsString(mContext.getResources().getString(id));
                if (TextUtils.isEmpty(cache)) {
                    vif.error(id, volleyError, "");
                } else {
                    vif.error(id, volleyError, cache);
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringrequest.setTag(tag);
        BaseApplication.getHttpQueues().add(stringrequest);
    }

//    public static void GetBitmap(Context mContext, final String id, String url,
//                                 String tag, int width, int height, final ImageRequestResponse vif) {
//        App.getHttpQueues().cancelAll(tag);
//        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                vif.onSuccess(id, bitmap);
//            }
//        }, width, height, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                vif.onError(id, volleyError);
//            }
//        });
//        imageRequest.setTag(tag);
//        App.getHttpQueues().add(imageRequest);
//    }

}
