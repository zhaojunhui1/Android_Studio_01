package com.zjh.administrat.android_studio_01.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjh.administrat.android_studio_01.R;
import com.zjh.administrat.android_studio_01.activity.MainActivity;
import com.zjh.administrat.android_studio_01.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchBean.ResultBean> mData;
    private Context mContect;

    public SearchAdapter(Context mContect) {
        this.mContect = mContect;
        mData = new ArrayList<>();
    }


    public void setDatas(List<SearchBean.ResultBean> result) {
        if (result != null){
            mData.addAll(result);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_data_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.search_title.setText(mData.get(i).getCommodityName());
        mHolder.search_price.setText("￥"+mData.get(i).getPrice());
        mHolder.search_number.setText("已售"+mData.get(i).getSaleNum()+"件");

        mHolder.search_imageView.setImageURI(mData.get(i).getMasterPic());

        //点击回调商品id
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSearchClick != null){
                    mOnSearchClick.OnClick(i, mData.get(i).getCommodityId()+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView search_imageView;
        public TextView search_title, search_price, search_number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_imageView = itemView.findViewById(R.id.search_imageView);
            search_title = itemView.findViewById(R.id.search_title);
            search_price = itemView.findViewById(R.id.search_price);
            search_number = itemView.findViewById(R.id.search_number);
        }
    }

    //成员变量
    OnSearchClick mOnSearchClick;
    //set方法
    public void setOnSearchClick(OnSearchClick onSearchClick) {
        this.mOnSearchClick = onSearchClick;
    }
    //定义一个接口
    public interface OnSearchClick {
        void OnClick(int i, String commodityId);
    }


}
