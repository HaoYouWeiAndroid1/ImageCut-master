package com.yipeng.imagecut;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Yipeng on 2016/8/26 0026.
 */
public interface Api {
    @GET("/book/recommend.from")
    Call<aaaa> getInfo(@Query("key") String key,
                       @Query("cat") String cat,
                       @Query("ranks") String ranks);
}
