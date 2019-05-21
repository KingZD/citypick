package com.example.demo.greendao.db;

import com.example.demo.bean.AddressBean;
import com.example.demo.greendao.dao.AddressBeanDao;
import com.example.demo.greendao.helper.DbManager;

import java.util.List;
/**
 * 文件名:RegionDB
 * 创建者:zed
 * 创建日期:2019/4/16 15:28
 * 描述:TODO
 */
public class RegionDB {
    /**
     * 获取所有的州
     */
    public static List<AddressBean> getAllRegions() {
        List<AddressBean> list = DbManager.getDaoSession()
                .getAddressBeanDao()
                .queryBuilder()
                .where(AddressBeanDao.Properties.Pid.eq("0"))
                .list();
        return list;
    }

    /**
     * 获取所有的省
     */
    public static List<AddressBean> getAllChild(long id) {
        List<AddressBean> list = DbManager.getDaoSession()
                .getAddressBeanDao()
                .queryBuilder()
                .where(AddressBeanDao.Properties.Pid.eq(id))
                .list();
        return list;
    }

    /**
     * 获取所有的省
     */
    public static long getAllChildCount(long id) {
        return DbManager.getDaoSession()
                .getAddressBeanDao()
                .queryBuilder()
                .where(AddressBeanDao.Properties.Pid.eq(id))
                .count();
    }
}
