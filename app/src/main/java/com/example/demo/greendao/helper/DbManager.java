package com.example.demo.greendao.helper;

/**
 * 文件名:DbManager
 * 创建者:zed
 * 创建日期:2019/4/16 15:12
 * 描述:TODO
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.demo.MyApplication;
import com.example.demo.greendao.dao.DaoMaster;
import com.example.demo.greendao.dao.DaoSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class DbManager {
    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "cp_dz_np.db";
    private static DbManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private static Context mContext;

    private DbManager(Context context) {
        mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession();
    }

    public static DbManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (DbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DbManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }

        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        mContext = context;
        if (null == mDaoMaster) {
            synchronized (DbManager.class) {
                if (null == mDaoMaster) {
                    copyDataBase(DB_NAME);
                    MyOpenHelper helper = new MyOpenHelper(context, DB_NAME, null);
                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    private static void copyDataBase(String dbname) {
        try {
            // Path to the just created empty db
            File outFileName = mContext.getDatabasePath(dbname);
            InputStream myInput = mContext.getAssets().open(dbname);
            if (!outFileName.exists()) {
                // Open your local db as the input stream
                outFileName.getParentFile().mkdirs();

                // Open the empty db as the output stream
                OutputStream myOutput = new FileOutputStream(outFileName);
                // transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                // Close the streams
                myOutput.flush();
                myOutput.close();
            } else {
                if (outFileName.length() != myInput.available()) {
                    //本地数据库损坏 需要重新来
                    boolean delete = outFileName.delete();
                    //重新开始拷贝
                    if (delete)
                        copyDataBase(DB_NAME);
                } else {
                }
            }
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 获取DaoMaster
//     *
//     * 判断是否存在数据库，如果没有则创建数据库
//     * @param context
//     * @return
//     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (null == mDaoMaster) {
//            synchronized (DbManager.class) {
//                if (null == mDaoMaster) {
//                    MyOpenHelper helper = new MyOpenHelper(context,DB_NAME,null);
//                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
//                }
//            }
//        }
//        return mDaoMaster;
//    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (DbManager.class) {
                mDaoSession = getDaoMaster(MyApplication.getApplication()).newSession();
            }
        }
        return mDaoSession;
    }
}