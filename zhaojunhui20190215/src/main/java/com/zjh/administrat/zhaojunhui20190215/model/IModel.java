package com.zjh.administrat.zhaojunhui20190215.model;

import com.zjh.administrat.zhaojunhui20190215.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
