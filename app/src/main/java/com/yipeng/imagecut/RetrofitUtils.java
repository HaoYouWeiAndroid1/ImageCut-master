package com.yipeng.imagecut;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

public class RetrofitUtils {

    public static boolean API_DEBUG = true;
    public static final String TAG = "RetrofitUtilsNew";

    public static final String DEFAULT_DOMAIN =  "http://japi.juhe.cn";


    private static Api sApi;

    public static synchronized Api getApiInstance() {
        if (sApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DEFAULT_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            sApi = retrofit.create(Api.class);
        }
        return sApi;
    }


    public abstract static class ApiCallback<T> implements Callback<T> {
        @Override
        public void onFailure(Throwable t) {
            Log.d(TAG, "api failure: ", t);
        }
    }
}
