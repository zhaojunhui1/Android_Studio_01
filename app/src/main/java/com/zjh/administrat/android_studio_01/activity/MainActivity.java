package com.zjh.administrat.android_studio_01.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.myapplication.greendao.DaoMaster;
import com.bawei.myapplication.greendao.DaoSession;
import com.bawei.myapplication.greendao.NewsEntityDao;
import com.zjh.administrat.android_studio_01.R;
import com.zjh.administrat.android_studio_01.adapter.SearchAdapter;
import com.zjh.administrat.android_studio_01.bean.IdBean;
import com.zjh.administrat.android_studio_01.bean.SearchBean;
import com.zjh.administrat.android_studio_01.greendao.NewsEntity;
import com.zjh.administrat.android_studio_01.presenter.IPresenterImpl;
import com.zjh.administrat.android_studio_01.utils.Api;
import com.zjh.administrat.android_studio_01.utils.NetWorkUtils;
import com.zjh.administrat.android_studio_01.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView;
    private EditText edit_home;
    private SearchAdapter searchAdapter;
    private NewsEntityDao newsEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGreenDao();


    }

    //初始化GreenDao
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        newsEntityDao = daoSession.getNewsEntityDao();

    }

    /*
    *   初始化View
    * */
    private void initView() {
        iPresenter = new IPresenterImpl(this);
        recyclerView = findViewById(R.id.recycleView);
        edit_home = findViewById(R.id.edit_home);

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

    }

    /*
    *  点击搜索
    * */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "10");
        map.put("keyword", edit_home.getText().toString());
        iPresenter.pRequestData(Api.SEARCH_URL, map, SearchBean.class);
        searchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(searchAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        searchAdapter.setOnSearchClick(new SearchAdapter.OnSearchClick() {
            @Override
            public void OnClick(int i, String commodityId) {
                Toast.makeText(MainActivity.this, "下标"+commodityId, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                IdBean idBean = new IdBean(commodityId);
                EventBus.getDefault().post(idBean);
            }
        });
    }



    //返回数据
    @Override
    public void viewData(Object data) {
        if (data instanceof SearchBean){
            SearchBean searchBean = (SearchBean) data;
            //添加数据
            if (NetWorkUtils.isNetWork(this)){
            for (int i = 0; i < searchBean.getResult().size(); i++) {
                NewsEntity entity = new NewsEntity();
                        /*searchBean.getResult().get(i).getCommodityId(),
                        searchBean.getResult().get(i).getCommodityName(),
                        searchBean.getResult().get(i).getMasterPic(),
                        searchBean.getResult().get(i).getPrice(),
                        searchBean.getResult().get(i).getSaleNum()*/
                entity.setCommodityId(searchBean.getResult().get(i).getCommodityId());
                entity.setCommodityName(searchBean.getResult().get(i).getCommodityName());
                entity.setMasterPic(searchBean.getResult().get(i).getMasterPic());
                entity.setPrice(searchBean.getResult().get(i).getPrice());
                entity.setSaleNum(searchBean.getResult().get(i).getSaleNum());
                newsEntityDao.insert(entity);
            }

            //查询数据
            searchAdapter.setDatas(searchBean.getResult());

        }else {
                List<NewsEntity> newsEntities = newsEntityDao.loadAll();
                searchAdapter.notifyDataSetChanged();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
