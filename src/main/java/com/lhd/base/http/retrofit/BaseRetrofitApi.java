package com.lhd.base.http.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.lhd.base.main.BaseApplication;
import com.lhd.base.utils.ACache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseRetrofitApi {

    private Retrofit retrofit;
    private static volatile BaseRetrofitApi instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (BaseRetrofitApi.class) {
                if (instance == null) {
                    instance = new BaseRetrofitApi();
                }
            }
        }
        return instance.getRetrofit();
    }

    public BaseRetrofitApi() {
        retrofit = createRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Retrofit createRetrofit() {
        retrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(Contact.BASE_URL)
                .build();
        return retrofit;
    }

    /**
     * 自定义OkHttpClient拦截请求参数并加上 加密参数
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("ApiUrl", "--->" + message);
            }
        });
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();

                // 添加新的参数
                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                        .newBuilder();
                authorizedUrlBuilder.scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter("urlencode", "false");

                FormBody.Builder newBuidle = new FormBody.Builder();
                if (oldRequest.method().equals("POST")) {
                    ACache cache = ACache.get(BaseApplication.getContext());
                    if (!TextUtils.isEmpty(cache.getAsString("user_id"))) {
                        newBuidle.add("user_id", cache.getAsString("user_id"));
//                        newBuidle.add("user_id", "1");
                    }
                    if (!TextUtils.isEmpty(cache.getAsString("user_token"))) {
                        newBuidle.add("token", cache.getAsString("user_token"));
//                        newBuidle.add("token", "o8aT50Mj2KL0DkhuFbW9KBV6WV5M");
                    }
                    FormBody old = (FormBody) oldRequest.body();
                    if (old != null && old.size() > 0) {
                        for (int i = 0; i < old.size(); i++) {
                            newBuidle.add(old.name(i), old.value(i));
                        }
                    }
                    newBuidle.add("urlencode", "false");
                }
                // 新的请求
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), newBuidle.build())
                        .url(authorizedUrlBuilder.build())
                        .build();

                return chain.proceed(newRequest);
            }
        };
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

}
