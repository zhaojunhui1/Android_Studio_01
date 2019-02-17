package com.zjh.administrat.week1_demo.model;

import com.zjh.administrat.week1_demo.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
