package com.zjh.administrat.week1_demo.activity;

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

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zjh.administarat.week1_demo_greendao.DaoMaster;
import com.zjh.administarat.week1_demo_greendao.DaoSession;
import com.zjh.administarat.week1_demo_greendao.NewsEntityDao;
import com.zjh.administrat.week1_demo.R;
import com.zjh.administrat.week1_demo.adapter.SearchAdapter;
import com.zjh.administrat.week1_demo.bean.IdBean;
import com.zjh.administrat.week1_demo.bean.SearchBean;
import com.zjh.administrat.week1_demo.greendao.NewsEntity;
import com.zjh.administrat.week1_demo.presenter.IPresenterImpl;
import com.zjh.administrat.week1_demo.utils.Api;
import com.zjh.administrat.week1_demo.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {
    private IPresenterImpl iPresenter;
    private XRecyclerView recyclerView;
    private EditText edit_home;
    private SearchAdapter searchAdapter;
    private int page;
    private int count = 5;
    private NewsEntityDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGreenDao();


    }

    //初始化GreenDao
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "newsdata.db");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mDao = daoSession.getNewsEntityDao();

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
                page =1;
                searchAdapter.notifyDataSetChanged();
            }
        });
        //刷新，加载
        searchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(searchAdapter);

        page = 1;
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }
            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();

    }

    /*
     *  点击搜索
     * */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", count+"");
        map.put("keyword", edit_home.getText().toString());
        iPresenter.pRequestData(Api.SEARCH_URL_GET, map, SearchBean.class);

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
            if (page == 1){
                searchAdapter.setDatas(searchBean.getResult());
            }else {
                searchAdapter.addDatas(searchBean.getResult());
            }
            page ++;
            //停止刷新，加载
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
            if (page ==1 && count == searchBean.getResult().size()){
                Toast.makeText(MainActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
            }



           /* if(searchBean.getStatus().equals("0000")){
                if(searchBean.getResult() != null&& searchBean.getResult().size() > 0){
                    if (page > 1) {
                        searchAdapter.addDatas(searchBean.getResult());
                        xrcContent.showContent();
                    } else {
                        searchAdapter.setDatas(searchBean.getResult());
                    }
                    //设置totalPage 和currentPage
                    xrcContent.getRecyclerView().setPage(page, page+1);

                    if (searchAdapter.getItemCount() < 1) {
                        xrcContent.showEmpty();
                        return;
                    }
                }else{
                    //由于没有更多的数据了，设置totalPage 和currentPage为一样
                    xrcContent.getRecyclerView().setPage(page, page);
                    getvDelegate().toastShort("没有更多数据了");
                }
            }else{
                getvDelegate().toastShort(searchBean.getMessage());
            }
*/

            //添加数据
            for (int i = 0; i < searchBean.getResult().size(); i++) {
                NewsEntity entityBean = new NewsEntity();
                entityBean.setCommodityId(searchBean.getResult().get(i).getCommodityId());
                entityBean.setCommodityName(searchBean.getResult().get(i).getCommodityName());
                entityBean.setMasterPic(searchBean.getResult().get(i).getMasterPic());
                entityBean.setPrice(searchBean.getResult().get(i).getPrice());
                entityBean.setSaleNum(searchBean.getResult().get(i).getSaleNum());
                mDao.insert(entityBean);
            }

            //查询数据


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }

}
