package com.zjh.administrat.zhaojunhui20190215.model;

import com.zjh.administrat.zhaojunhui20190215.callback.ICallBack;
import com.zjh.administrat.zhaojunhui20190215.callback.MyCallBack;
import com.zjh.administrat.zhaojunhui20190215.utils.OkHttps;

import java.util.Map;

public class IModelImpl implements IModel {


    @Override
    public void mRequestData(String urlStr, final Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        OkHttps.getInstance().getRequest(urlStr, map, clazz, new ICallBack() {
            @Override
            public void success(Object data) {
                myCallBack.OnSuccess(data);
            }

            @Override
            public void fails(Exception e) {

            }
        });
    }

}
