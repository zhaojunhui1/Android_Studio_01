package com.zjh.administrat.android_studio_01.presenter;

import android.support.v4.app.NavUtils;

import com.zjh.administrat.android_studio_01.callback.MyCallBack;
import com.zjh.administrat.android_studio_01.model.IModelImpl;
import com.zjh.administrat.android_studio_01.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void pRequestData(String urlStr, Map<String, String> map, Class clazz) {
        iModel.mRequestData(urlStr, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewData(data);
            }

            @Override
            public void OnFail(String error) {

            }
        });
    }

    public void onDetch(){
        if (iView != null){
            iView = null;
        }
        if (iModel != null){
            iModel = null;
        }
    }
}
