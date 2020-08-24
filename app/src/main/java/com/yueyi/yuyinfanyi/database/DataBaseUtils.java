package com.yueyi.yuyinfanyi.database;

import android.content.Context;

import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.bean.CacheDataBean;

import java.util.List;

public class DataBaseUtils {

    private static DataBaseUtils mInstance;
    private Context context;
    private DatabaseHelper helper;
    private DbService service;
    private DataBaseUtils(Context context) {
        this.context=context;
        helper= new DatabaseHelper(context);
        service = new DbService(helper);
    }
    public synchronized static DataBaseUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataBaseUtils(context);
        }
        return mInstance;
    }
    public void insert(String yuanwen,String fanyi,String yuanyuyan,String mubiaoyuyan){
        CacheDataBean cacheDataBean = new CacheDataBean();
        cacheDataBean.setFanyi(fanyi);
        cacheDataBean.setYuanwen(yuanwen);
        cacheDataBean.setYuanyuyan(yuanyuyan);
        cacheDataBean.setMubiaoyuyan(mubiaoyuyan);
        CacheBaseBean cacheBaseBean = new CacheBaseBean(cacheDataBean,System.currentTimeMillis()+"");
        service.insert(cacheBaseBean);
    }
    //查询最近三十天的数据
    public  List<CacheBaseBean> queryById(){
        List<CacheBaseBean> cacheBaseBean = service.queryByType("cache", CacheBaseBean.class, 30 * 24 * 60 * 60 * 1000);
        return cacheBaseBean;
    }
    public void delete(String db_id){
        service.deleteById(db_id);
    }







}
