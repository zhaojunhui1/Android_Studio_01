package com.zjh.administrat.android_studio_01.model;

import com.google.gson.Gson;
import com.zjh.administrat.android_studio_01.callback.MyCallBack;
import com.zjh.administrat.android_studio_01.utils.RetrofitManager;

import java.util.Map;

public class IModelImpl implements IModel {


    @Override
    public void mRequestData(String urlStr, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(urlStr, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null){
                    myCallBack.OnSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (myCallBack != null){
                    myCallBack.OnFail(error);
                }
            }
        });
    }


}
