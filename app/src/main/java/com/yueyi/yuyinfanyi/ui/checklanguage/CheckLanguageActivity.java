package com.yueyi.yuyinfanyi.ui.checklanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

import android.content.Intent;
import android.os.Bundle;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.CountryBean;
import com.yueyi.yuyinfanyi.databinding.ActivityCheckLanguageBinding;
import com.yueyi.yuyinfanyi.ui.dialogactivity.SeniorDialogActivity;
import com.yueyi.yuyinfanyi.utils.UserPreference;

import java.util.ArrayList;

public class CheckLanguageActivity extends MyBaseActivity<ActivityCheckLanguageBinding,CheckLanguageViewModel> {

    private ArrayList<CountryBean> countryBeans = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_check_language;
    }

    @Override
    public int initVariableId() {
        return BR.checklanguageviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        String name = getIntent().getStringExtra("name");
        viewModel.title.set(name);
        binding.setAdapter(new MyAdapter());
        CountryBean countryBean1 = new CountryBean();
        countryBean1.setName("中文");
        countryBean1.setNational_flag_resource(R.mipmap.ch);
        countryBean1.setLock(false);
        countryBeans.add(countryBean1);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean1));

        CountryBean countryBean2 = new CountryBean();
        countryBean2.setName("英文");
        countryBean2.setNational_flag_resource(R.mipmap.en);
        countryBean2.setLock(false);
        countryBeans.add(countryBean2);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean2));

        CountryBean countryBean3 = new CountryBean();
        countryBean3.setName("日语");
        countryBean3.setNational_flag_resource(R.mipmap.jp);
        countryBean3.setLock(false);
        countryBeans.add(countryBean3);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean3));

        CountryBean countryBean4 = new CountryBean();
        countryBean4.setName("韩语");
        countryBean4.setNational_flag_resource(R.mipmap.kor);
        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
            countryBean4.setLock(true);
        }else{
            countryBean4.setLock(false);
        }
        countryBeans.add(countryBean4);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean4));

//        CountryBean countryBean5 = new CountryBean();
//        countryBean5.setName("西班牙语");
//        countryBean5.setNational_flag_resource(R.mipmap.kor);
//        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
//            countryBean5.setLock(true);
//        }else{
//            countryBean5.setLock(false);
//        }
//        countryBeans.add(countryBean5);
//        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean5));

        CountryBean countryBean6 = new CountryBean();
        countryBean6.setName("葡萄牙语");
        countryBean6.setNational_flag_resource(R.mipmap.pt);
        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
            countryBean6.setLock(true);
        }else{
            countryBean6.setLock(false);
        }
        countryBeans.add(countryBean6);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean6));

        CountryBean countryBean7 = new CountryBean();
        countryBean7.setName("法语");
        countryBean7.setNational_flag_resource(R.mipmap.fra);
        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
            countryBean7.setLock(true);
        }else{
            countryBean7.setLock(false);
        }
        countryBeans.add(countryBean7);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean7));

        CountryBean countryBean8 = new CountryBean();
        countryBean8.setName("德语");
        countryBean8.setNational_flag_resource(R.mipmap.de);
        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
            countryBean8.setLock(true);
        }else{
            countryBean8.setLock(false);
        }
        countryBeans.add(countryBean8);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean8));

//        CountryBean countryBean9 = new CountryBean();
//        countryBean9.setName("意大利语");
//        countryBean9.setNational_flag_resource(R.mipmap.kor);
//        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
//            countryBean9.setLock(true);
//        }else{
//            countryBean9.setLock(false);
//        }
//        countryBeans.add(countryBean9);
//        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean9));

        CountryBean countryBean10 = new CountryBean();
        countryBean10.setName("俄语");
        countryBean10.setNational_flag_resource(R.mipmap.ru);
        if(UserPreference.getInstance(this).getType().equals("NORMAL")){
            countryBean10.setLock(true);
        }else{
            countryBean10.setLock(false);
        }
        countryBeans.add(countryBean10);
        viewModel.observableList.add(new CheckLanguageItemViewModel(viewModel,countryBean10));





    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.positon.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String type = UserPreference.getInstance(CheckLanguageActivity.this).getType();
                if(type!=null&&(type.equals("VIP")||type.equals("FOREVER_VIP"))){
                    Bundle bundle =new Bundle();
                    bundle.putString("name",countryBeans.get(integer).getName());

                    //设置返回数据
                    // 先设置ReaultCode,再设置存储数据的意图
                    setResult(RESULT_OK,new Intent().putExtra("name",countryBeans.get(integer).getName()));
                    //关闭当前activity
                    finish();
                }else {
                    if(integer>2){
                        startActivity(new Intent(getBaseContext(), SeniorDialogActivity.class));
                        return;
                    }else{
                        Bundle bundle =new Bundle();
                        bundle.putString("name",countryBeans.get(integer).getName());

                        //设置返回数据
                        // 先设置ReaultCode,再设置存储数据的意图
                        setResult(RESULT_OK,new Intent().putExtra("name",countryBeans.get(integer).getName()));
                        //关闭当前activity
                        finish();
                    }
                }



            }
        });
    }
}
