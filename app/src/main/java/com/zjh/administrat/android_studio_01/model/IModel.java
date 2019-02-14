package com.zjh.administrat.android_studio_01.model;

import com.zjh.administrat.android_studio_01.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
