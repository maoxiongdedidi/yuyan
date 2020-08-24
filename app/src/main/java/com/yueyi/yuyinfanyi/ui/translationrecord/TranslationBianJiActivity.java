package com.yueyi.yuyinfanyi.ui.translationrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.os.Bundle;
import android.util.Log;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.database.DataBaseUtils;
import com.yueyi.yuyinfanyi.databinding.ActivityTranslationBianJiBinding;
import com.yueyi.yuyinfanyi.ui.extension.ExtensionActivity;
import com.yueyi.yuyinfanyi.utils.TTSUtils;

import java.util.ArrayList;
import java.util.List;

public class TranslationBianJiActivity extends MyBaseActivity<ActivityTranslationBianJiBinding,TranslationBianjiViewModel> {

    List<CacheBaseBean> cacheBaseBeans;
    private DataBaseUtils instance;
    private BianJiAdapter bianJiAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_translation_bian_ji;
    }

    @Override
    public int initVariableId() {
        return BR.translationbianji;
    }

    @Override
    public void initData() {
        setbarfull();
        statusBarLightMode();
        instance = DataBaseUtils.getInstance(this);
        cacheBaseBeans =instance .queryById();
        bianJiAdapter = new BianJiAdapter();
        binding.setAdapter(bianJiAdapter);
      if(cacheBaseBeans!=null){
          for (int i = 0; i < cacheBaseBeans.size(); i++) {
              viewModel.observableList.add(new TranslationBianJiItemViewModel(viewModel,cacheBaseBeans.get(i)));
          }
      }

    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.delete.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                instance.delete(cacheBaseBeans.get(integer).getDb_Id());
                TranslationBianJiItemViewModel translationBianJiItemViewModel = viewModel.observableList.get(integer);
                viewModel.observableList.remove(translationBianJiItemViewModel);

            }
        });
        viewModel.uc.kuozhan.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String fanyi = cacheBaseBeans.get(integer).getCacheDataBean().getFanyi();
                Bundle bundle = new Bundle();
                bundle.putString("result",fanyi);
                startActivity(ExtensionActivity.class,bundle);
            }
        });
        viewModel.uc.yuedu.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String fanyi = cacheBaseBeans.get(integer).getCacheDataBean().getFanyi();
                TTSUtils.getInstance().speak(fanyi);
                ToastUtils.showShort("目前仅支持中文或英文朗读");
            }
        });

        viewModel.uc.check.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                TranslationBianJiItemViewModel translationBianJiItemViewModel = viewModel.observableList.get(integer);
                CacheBaseBean cacheBaseBean = translationBianJiItemViewModel.getCacheBaseBean();
                boolean check = cacheBaseBean.isCheck();
                cacheBaseBean.setCheck(!check);
                translationBianJiItemViewModel.setCacheBaseBean(cacheBaseBean);
            }
        });
        viewModel.uc.quanxuan.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int size = viewModel.observableList.size();
                for (int i = 0; i < size; i++) {
                    TranslationBianJiItemViewModel translationBianJiItemViewModel = viewModel.observableList.get(i);
                    CacheBaseBean cacheBaseBean = translationBianJiItemViewModel.getCacheBaseBean();
                    boolean check = cacheBaseBean.isCheck();
                    cacheBaseBean.setCheck(!check);
                    translationBianJiItemViewModel.setCacheBaseBean(cacheBaseBean);

                }
            }
        });
        viewModel.uc.shanchu.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int size = viewModel.observableList.size();
                ArrayList<CacheBaseBean> checklist = new ArrayList<>();
                ArrayList<TranslationBianJiItemViewModel> checkitemlist = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TranslationBianJiItemViewModel translationBianJiItemViewModel = viewModel.observableList.get(i);
                    CacheBaseBean cacheBaseBean = translationBianJiItemViewModel.getCacheBaseBean();
                    if(cacheBaseBean.isCheck()){
                       checklist.add(cacheBaseBean);
                        checkitemlist.add(translationBianJiItemViewModel);
                    }
                }
                for (int i = 0; i < checklist.size(); i++) {
                    instance.delete(checklist.get(i).getDb_Id());

                }
                for (int i = 0; i < checkitemlist.size(); i++) {
                    viewModel.observableList.remove(checkitemlist.get(i));
                }
            }
        });
    }
}
