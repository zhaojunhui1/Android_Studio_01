package com.zjh.administrat.week1_demo.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApi {

    @GET
    Observable<ResponseBody> get(@Url String urlStr, @QueryMap Map<String, String> map);

}
