package com.yueyi.yuyinfanyi.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DbService {
    private DatabaseHelper mSql;
    private AtomicInteger mOpenCounter;//原子操作的Integer的类,线程安全
    private SQLiteDatabase mDatabase;
    private Gson gson = null;


    public DbService(DatabaseHelper sqlLiteHelper) {
        mSql = sqlLiteHelper;
        mOpenCounter = new AtomicInteger();
        gson = new Gson();
    }

    /**
     * 数据插入
     *
     * @param bean 要插入的对象 需要实现DbBeanInterface
     * @return
     */
    public <T extends DbBeanInterface> int insert(T bean) {

        if (!isRightBean(bean) || TextUtils.isEmpty(bean.getDb_Id())) {
            return -1;
        }
        String catcheTime = System.currentTimeMillis() + "";
        if (queryById(bean.getDb_Id(), bean.getClass()) != null) {
            return updata(bean);
        }
        int flag = -1;
        SQLiteDatabase database = getWriteDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("cid", bean.getDb_Id());
            values.put("cacheTime", catcheTime);
            values.put("jsonData", gson.toJson(bean));
            values.put("type", TextUtils.isEmpty(bean.getDb_Type()) ? "no_type" : bean.getDb_Type());
            database.insert(mSql.TABLE_TEMP, null, values);
            database.setTransactionSuccessful();
            flag = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            database.endTransaction();
            dbclose(null);
        }
        return flag;
    }

    /**
     * 批量插入数据(数据更新操作),
     *
     * @param bean
     * @param <T>
     * @return
     */
    public <T extends DbBeanInterface> int update(List<T> bean) {
        if (bean == null || bean.size() <= 0) {
            return -1;
        }
        String cacheTime = System.currentTimeMillis() + "";
        int flag = -1;
        clearTable();
        SQLiteDatabase database = getWriteDatabase();
        database.beginTransaction();
        try {
            for (T dataBean : bean) {
                if (!TextUtils.isEmpty(dataBean.getDb_Id())) {
                    ContentValues values = new ContentValues();
                    values.put("cid", dataBean.getDb_Id());
                    values.put("cacheTime", cacheTime);
                    values.put("jsonData", gson.toJson(dataBean));
                    values.put("type", TextUtils.isEmpty(dataBean.getDb_Type()) ? "no_type" : dataBean.getDb_Type());
                    database.insert(mSql.TABLE_TEMP, null, values);
                }
            }
            database.setTransactionSuccessful();
            flag = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            database.endTransaction();
            dbclose(null);
        }
        return flag;
    }

    private <T extends DbBeanInterface> int updata(T bean) {
        if (!isRightBean(bean)) {
            return -1;
        }
        int flag = -1;
        SQLiteDatabase db = getWriteDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("cid", bean.getDb_Id());
            values.put("cacheTime", System.currentTimeMillis() + "");
            values.put("jsonData", gson.toJson(bean));
            values.put("type", TextUtils.isEmpty(bean.getDb_Type()) ? "no_type" : bean.getDb_Type());
            flag = db.update(mSql.TABLE_TEMP, values, "cid=?", new String[]{bean.getDb_Id()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            db.endTransaction();
            dbclose(null);
        }
        return flag;
    }

    private <T> boolean isRightBean(T bean) {
        return !(bean == null || !(bean instanceof DbBeanInterface));
    }

    /**
     * 根据缓存有效期取数据
     *
     * @param id
     * @param classOfT
     * @param cachetime 缓存有效期（单位 毫秒）
     * @param <T>
     * @return
     */
    public <T> T queryById(String id, Class<T> classOfT, long cachetime) {
        SQLiteDatabase db = getWriteDatabase();
        Cursor cursor = null;
        T bean = null;
        db.beginTransaction();
        try {
            cursor = db.rawQuery("select * from " + mSql.TABLE_TEMP + " where cid=?", new String[]{id});
            cursor.move(-1);
            if (cursor.moveToNext()) {
                bean = gson.fromJson(cursor.getString(cursor.getColumnIndex("jsonData")), classOfT);
                if (cachetime > 0) {
                    String startTime = cursor.getString(cursor.getColumnIndex("cacheTime"));
                    if (TextUtils.isEmpty(startTime) || isCacheDataOverTime(Long.parseLong(startTime), cachetime)) {
                        //deleteById(cursor.getString(cursor.getColumnIndex("cid")));
                        bean = null;
                    }
                }
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            db.endTransaction();
            dbclose(cursor);
        }
        return bean;
    }

    /**
     * 通过type查找数据
     *
     * @param type
     * @param classOfT
     * @param catcheTime 缓存有效期0表示无有效期
     * @param <T>
     * @return
     */
    public <T> List<T> queryByType(String type, Class<T> classOfT, long catcheTime) {
        List<T> list = null;
        if (!TextUtils.isEmpty(type)) {
            SQLiteDatabase db = getWriteDatabase();
            Cursor cursor = null;
            String starttime = "";
            list = new ArrayList<>();
            db.beginTransaction();
            try {
                cursor = db.rawQuery("select * from " + mSql.TABLE_TEMP + " where type=?", new String[]{type});
                cursor.move(-1);
                while (cursor.getCount() > 0 && cursor.moveToNext()) {
                    starttime = cursor.getString(cursor.getColumnIndex("cacheTime"));
                    if (catcheTime > 0) {
                        if (TextUtils.isEmpty(starttime) || isCacheDataOverTime(Long.parseLong(starttime), catcheTime)) {
                            //deleteById(cursor.getString(cursor.getColumnIndex("cid")));
                        } else {
                            list.add(gson.fromJson(cursor.getString(cursor.getColumnIndex("jsonData")), classOfT));
                        }
                    } else {
                        list.add(gson.fromJson(cursor.getString(cursor.getColumnIndex("jsonData")), classOfT));
                    }
                }
                db.setTransactionSuccessful();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                db.endTransaction();
                dbclose(cursor);
            }
        }
        return list;
    }

    /**
     * 引入原子操作，防止还在使用中的数据库线程被异常关闭
     *
     * @param cursor
     */
    private synchronized void dbclose(Cursor cursor) {
        if (mOpenCounter.decrementAndGet() == 0 && mDatabase != null) {
            // Closing database
            mDatabase.close();
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
    }

    /**
     * 引入原子操作，解决多线程并发访问数据库线程池异常问题
     *
     * @return
     */
    private synchronized SQLiteDatabase getWriteDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mSql.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 通过Id删除数据
     *
     * @param id
     * @return
     */
    public int deleteById(String id) {
        int flag = -1;
        if (!TextUtils.isEmpty(id)) {
            SQLiteDatabase db = getWriteDatabase();
            db.beginTransaction();
            try {
                flag = db.delete(mSql.TABLE_TEMP, "cid=?", new String[]{id});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                dbclose(null);
            }
        }
        return flag;
    }


    /**
     * 根据id查找数据
     *
     * @param id
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T queryById(String id, Class<T> classOfT) {
        return queryById(id, classOfT, 0);
    }

    /**
     * 判断数据是否过期
     *
     * @param starttime 数据存储时间
     * @param cacheTime 缓存有效期
     * @return true 数据失效 false 未过期
     */
    private boolean isCacheDataOverTime(long starttime, long cacheTime) {
        long timenow = System.currentTimeMillis();
        return starttime + cacheTime < timenow;
    }

    /**
     * 据缓存有效期取数据
     *
     * @param type
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> List<T> queryByType(String type, Class<T> classOfT) {
        return queryByType(type, classOfT, 0);
    }

    public int deleteByType(String type) {
        int flag = -1;
        if (!TextUtils.isEmpty(type)) {
            SQLiteDatabase db = getWriteDatabase();
            db.beginTransaction();
            try {
                flag = db.delete(mSql.TABLE_TEMP, "type=?", new String[]{type});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                dbclose(null);
            }
        }
        return flag;
    }

    /**
     * 清空本地数据
     *
     * @return
     */
    public void clearTable() {
        SQLiteDatabase db = getWriteDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + mSql.TABLE_TEMP);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            dbclose(null);
        }

}
}
