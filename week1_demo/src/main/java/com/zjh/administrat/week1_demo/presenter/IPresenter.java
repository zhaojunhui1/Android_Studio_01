package com.zjh.administrat.week1_demo.presenter;

import java.util.Map;

public interface IPresenter {
    void pRequestData(String urlStr, Map<String, String> map, Class clazz);
}
