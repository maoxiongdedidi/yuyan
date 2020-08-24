package com.yueyi.yuyinfanyi.bean;

import com.yueyi.yuyinfanyi.database.DbBeanInterface;

public class CacheBaseBean implements DbBeanInterface {
    private CacheDataBean cacheDataBean;
    private String db_id;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public CacheDataBean getCacheDataBean() {
        return cacheDataBean;
    }

    public void setCacheDataBean(CacheDataBean cacheDataBean) {
        this.cacheDataBean = cacheDataBean;
    }

    public CacheBaseBean(CacheDataBean cacheDataBean, String db_id) {
        this.cacheDataBean = cacheDataBean;
        this.db_id = db_id;
    }

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    @Override
    public String getDb_Id() {
        return db_id;
    }

    @Override
    public String getDb_Type() {
        return "cache";
    }
}
