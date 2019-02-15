package com.zjh.administrat.zhaojunhui20190215.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.zjh.administrat.zhaojunhui20190215.R;
import com.zjh.administrat.zhaojunhui20190215.adapter.NewsAdapter;
import com.zjh.administrat.zhaojunhui20190215.bean.NewsBean;
import com.zjh.administrat.zhaojunhui20190215.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private String urlStr = "http://www.xieast.com/api/news/news.php";
    private RecyclerView recyclerView;
    private IPresenterImpl iPresenter;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycleView);
        iPresenter = new IPresenterImpl(this);

        Map<String, String> map = new HashMap<>();
        iPresenter.pRequestData(urlStr, map, NewsBean.class);
        mAdapter = new NewsAdapter(this);
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    public void viewData(Object data) {
       if (data instanceof NewsBean){
           NewsBean newsBean = (NewsBean) data;
           mAdapter.setDatas(newsBean.getData());

       }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }


}
