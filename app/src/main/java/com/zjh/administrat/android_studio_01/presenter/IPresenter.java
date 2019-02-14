package com.zjh.administrat.android_studio_01.presenter;

import java.util.Map;

public interface IPresenter {
    void pRequestData(String urlStr, Map<String, String> map, Class clazz);
}
