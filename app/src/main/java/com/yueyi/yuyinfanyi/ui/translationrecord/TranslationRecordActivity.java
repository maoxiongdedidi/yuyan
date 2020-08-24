package com.yueyi.yuyinfanyi.ui.translationrecord;

import androidx.recyclerview.widget.LinearLayoutManager;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.database.DataBaseUtils;
import com.yueyi.yuyinfanyi.databinding.ActivityTranslationRecordBinding;
import com.yueyi.yuyinfanyi.ui.extension.ExtensionActivity;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.TTSUtils;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.List;

public class TranslationRecordActivity extends MyBaseActivity<ActivityTranslationRecordBinding,TranslationRecordViewModel> implements MyAdapter.CallBack {

    List<CacheBaseBean> cacheBaseBeans;
    private DataBaseUtils instance;
    private MyAdapter myAdapter;
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // 创建菜单：
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 140* Utils.getScreenHeight(TranslationRecordActivity.this)/Utils.getScreenWidth(TranslationRecordActivity.this);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(TranslationRecordActivity.this)
                    .setBackgroundColor(0xffEB1900)
                    .setImage(R.mipmap.cache_delete_white)
                    .setTextColor(Color.WHITE)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
            swipeLeftMenu.addMenuItem(deleteItem);
        }
    };
    // 菜单点击监听。
    SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection();//左边还是右边菜单
            int adapterPosition = menuBridge.getAdapterPosition();//    ecyclerView的Item的position。
            int position = menuBridge.getPosition();// 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                instance.delete(cacheBaseBeans.get(position).getDb_Id());
                cacheBaseBeans.remove(adapterPosition);
                myAdapter.notifyDataSetChanged();
            }

        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        instance = DataBaseUtils.getInstance(this);
        cacheBaseBeans =instance .queryById();
        myAdapter = new MyAdapter(this,cacheBaseBeans,this);
        return R.layout.activity_translation_record;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myAdapter!=null&&cacheBaseBeans!=null){
            cacheBaseBeans.clear();
            List<CacheBaseBean> caches = instance.queryById();
            cacheBaseBeans.addAll(caches);
            myAdapter.notifyDataSetChanged();
            Log.e("====调用1", this.cacheBaseBeans.size()+"");
        }
        Log.e("====调用2",instance.queryById().size()+"");
    }

    @Override
    public int initVariableId() {
        return BR.translationrecord;
    }

    @Override
    public void initData() {
        SwipeMenuRecyclerView activityTranslateRecycler = binding.activityTranslateRecycler;
        activityTranslateRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityTranslateRecycler.setSwipeMenuCreator(swipeMenuCreator);
        activityTranslateRecycler.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        activityTranslateRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityTranslateRecycler.setAdapter(myAdapter);
        activityTranslateRecycler.smoothCloseMenu();
//        if(cacheBaseBeans!=null){
//            for (int i = 0; i < cacheBaseBeans.size(); i++) {
//                viewModel.observableList.add(new TranslationRecordItemViewModel(viewModel,cacheBaseBeans.get(i)));
//            }
//        }


    }

    @Override
    public void delete(int position) {
        instance.delete(cacheBaseBeans.get(position).getDb_Id());
        cacheBaseBeans.remove(position);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void kuozhan(int position) {
        String fanyi = cacheBaseBeans.get(position).getCacheDataBean().getFanyi();
        Bundle bundle = new Bundle();
        bundle.putString("result",fanyi);
        startActivity(ExtensionActivity.class,bundle);
    }

    @Override
    public void yuedu(int position) {
        String fanyi = cacheBaseBeans.get(position).getCacheDataBean().getFanyi();

        TTSUtils.getInstance().speak(fanyi);
        ToastUtils.showShort("目前仅支持中文或英文朗读");
    }

    @Override
    public void allview(int position) {
        CacheBaseBean cacheBaseBean = cacheBaseBeans.get(position);
        Contents.STAFF_LANGUAGE=cacheBaseBean.getCacheDataBean().getYuanyuyan();
        Contents.TARGET_LANGUAGE=cacheBaseBean.getCacheDataBean().getMubiaoyuyan();
        Contents.cacheBaseBean=cacheBaseBean;

        startActivity(HomeActivity.class);





    }
}
