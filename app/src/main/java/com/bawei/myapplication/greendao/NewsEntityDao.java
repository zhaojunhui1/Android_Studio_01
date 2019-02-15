package com.bawei.myapplication.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zjh.administrat.android_studio_01.greendao.NewsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEWS_ENTITY".
*/
public class NewsEntityDao extends AbstractDao<NewsEntity, Long> {

    public static final String TABLENAME = "NEWS_ENTITY";

    /**
     * Properties of entity NewsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property CommodityId = new Property(1, int.class, "commodityId", false, "COMMODITY_ID");
        public final static Property CommodityName = new Property(2, String.class, "commodityName", false, "COMMODITY_NAME");
        public final static Property MasterPic = new Property(3, String.class, "masterPic", false, "MASTER_PIC");
        public final static Property Price = new Property(4, int.class, "price", false, "PRICE");
        public final static Property SaleNum = new Property(5, int.class, "saleNum", false, "SALE_NUM");
    }


    public NewsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public NewsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEWS_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ," + // 0: id
                "\"COMMODITY_ID\" INTEGER NOT NULL ," + // 1: commodityId
                "\"COMMODITY_NAME\" TEXT," + // 2: commodityName
                "\"MASTER_PIC\" TEXT," + // 3: masterPic
                "\"PRICE\" INTEGER NOT NULL ," + // 4: price
                "\"SALE_NUM\" INTEGER NOT NULL );"); // 5: saleNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEWS_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NewsEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getCommodityId());
 
        String commodityName = entity.getCommodityName();
        if (commodityName != null) {
            stmt.bindString(3, commodityName);
        }
 
        String masterPic = entity.getMasterPic();
        if (masterPic != null) {
            stmt.bindString(4, masterPic);
        }
        stmt.bindLong(5, entity.getPrice());
        stmt.bindLong(6, entity.getSaleNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NewsEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getCommodityId());
 
        String commodityName = entity.getCommodityName();
        if (commodityName != null) {
            stmt.bindString(3, commodityName);
        }
 
        String masterPic = entity.getMasterPic();
        if (masterPic != null) {
            stmt.bindString(4, masterPic);
        }
        stmt.bindLong(5, entity.getPrice());
        stmt.bindLong(6, entity.getSaleNum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public NewsEntity readEntity(Cursor cursor, int offset) {
        NewsEntity entity = new NewsEntity( //
            cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // commodityId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // commodityName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // masterPic
            cursor.getInt(offset + 4), // price
            cursor.getInt(offset + 5) // saleNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NewsEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setCommodityId(cursor.getInt(offset + 1));
        entity.setCommodityName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMasterPic(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrice(cursor.getInt(offset + 4));
        entity.setSaleNum(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(NewsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(NewsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NewsEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}