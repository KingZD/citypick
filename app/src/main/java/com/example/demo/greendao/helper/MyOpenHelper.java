package com.example.demo.greendao.helper;

/**
 * 文件名:MyOpenHelper
 * 创建者:zed
 * 创建日期:2019/4/16 15:13
 * 描述:TODO
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.demo.greendao.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新 有几个表升级都可以传入到下面

        Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
            Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
//            MigrationHelper.getInstance().migrate(db, RegionBeanDao.class);
//            MigrationHelper.getInstance().migrate(db, ProvinceBeanDao.class);
//            MigrationHelper.getInstance().migrate(db, CityBeanDao.class);
//            MigrationHelper.getInstance().migrate(db, BarangayBeanDao.class);
        }
    }
}