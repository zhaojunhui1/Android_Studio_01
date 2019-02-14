package com.zjh.administrat.android_studio_01.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    /*
    *   创建单例
    * */
    private static volatile RetrofitManager retrofitManager;
    private final BaseApi baseApi;

    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    private RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10,  TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true);

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.BASE_URL)
                .client(client)
                .build();

        baseApi = retrofit.create(BaseApi.class);

    }

    public void get(String urlStr, Map<String, String> map, HttpListener listener){
        if (map == null) {
            map = new HashMap<>();
        }
        baseApi.get(urlStr, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    private Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(string);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

        };
        return observer;
    }


    private HttpListener mListener;
    public void setHttpLinstener(HttpListener httpLinstener) {
        this.mListener = httpLinstener;
    }
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }
}
