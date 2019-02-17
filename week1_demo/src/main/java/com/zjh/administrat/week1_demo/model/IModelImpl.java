package com.zjh.administrat.week1_demo.model;

import com.google.gson.Gson;
import com.zjh.administrat.week1_demo.callback.MyCallBack;
import com.zjh.administrat.week1_demo.utils.RetrofitManager;

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
