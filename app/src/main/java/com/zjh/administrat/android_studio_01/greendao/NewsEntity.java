package com.zjh.administrat.android_studio_01.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewsEntity {
    @Id(autoincrement = true)
    @Unique
    private long id;

    private int commodityId;
    private String commodityName;
    private String masterPic;
    private int price;
    private int saleNum;

    @Generated(hash = 2121778047)
    public NewsEntity() {
    }

    @Generated(hash = 720203909)
    public NewsEntity(long id, int commodityId, String commodityName,
            String masterPic, int price, int saleNum) {
        this.id = id;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.masterPic = masterPic;
        this.price = price;
        this.saleNum = saleNum;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getCommodityId() {
        return this.commodityId;
    }
    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
    public String getCommodityName() {
        return this.commodityName;
    }
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    public String getMasterPic() {
        return this.masterPic;
    }
    public void setMasterPic(String masterPic) {
        this.masterPic = masterPic;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getSaleNum() {
        return this.saleNum;
    }
    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }



}
