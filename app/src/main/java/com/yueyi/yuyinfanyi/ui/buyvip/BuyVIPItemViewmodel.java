package com.yueyi.yuyinfanyi.ui.buyvip;

import android.content.ClipData;
import android.graphics.drawable.Drawable;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.ProductListBean;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class BuyVIPItemViewmodel extends ItemViewModel<BuyVIPViewModel> {
    public ObservableField<Integer> productNameColor = new ObservableField<>(0xff252525);
    public ObservableField<Integer> priceColor = new ObservableField<>(0xff252525);
    public ObservableField<Integer> discountPriceColor = new ObservableField<>(0xff252525);


    public ObservableField<String> productName = new ObservableField<>("");
    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> discountPrice = new ObservableField<>("");
    public ObservableField<String> price_discountPrice = new ObservableField<>("");


    public ObservableField<Drawable> topbg = new ObservableField<>();

    public ObservableField<Drawable> bottombg = new ObservableField<>();
    private ProductListBean bean;
    private int itemposition;
    public void setBean(ProductListBean bean) {
        this.bean=bean;
        productName.set(bean.getProductName());

        discountPrice.set("¥"+bean.getDiscountPrice()+"");
        if(bean.getPrice()==bean.getDiscountPrice()){
            price_discountPrice.set(bean.getRemarks());
            price.set("");
        }else{
            price_discountPrice.set(bean.getRemarks());
            price.set("¥"+bean.getPrice()+"");
        }

        if(bean.getAutoType().equals("YES")){
            topbg.set(viewModel.getApplication().getResources().getDrawable(R.drawable.bg_buy_vip_item_top_select));
            bottombg.set(viewModel.getApplication().getResources().getDrawable(R.drawable.bg_buy_vip_item_bottom_unselect));
            productNameColor.set(0xff252525);
            priceColor.set(0xff252525);
            discountPriceColor.set(0xff252525);
            viewModel.price.set(bean.getPrice()+"");

        }else{
            topbg.set(viewModel.getApplication().getResources().getDrawable(R.drawable.bg_buy_vip_item_top_unselect));
            bottombg.set(viewModel.getApplication().getResources().getDrawable(R.drawable.bg_buy_vip_item_bottom_select));
            productNameColor.set(0xffFF873C);
            priceColor.set(0xffC5C5C5);
            discountPriceColor.set(0xffFF873C);
        }

    }

    public ProductListBean getBean() {
        return bean;
    }

    public BuyVIPItemViewmodel(@NonNull BuyVIPViewModel viewModel, ProductListBean bean,int itemposition) {
        super(viewModel);
        this.itemposition=itemposition;
        setBean(bean);

    }



    public void setOnItemClick(int position){
       viewModel.uc.positon.setValue(position);
       viewModel.price.set(bean.getDiscountPrice()+"");
    }


}
