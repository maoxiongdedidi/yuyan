package com.yueyi.yuyinfanyi.ui.buyvip;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.ProductListBean;
import com.yueyi.yuyinfanyi.databinding.BuyVipItemBinding;

import java.util.List;

import androidx.databinding.BindingConversion;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class BuyVIPItemAdapter extends BindingRecyclerViewAdapter<BuyVIPItemViewmodel> {
    private List<ProductListBean> listBeans ;

    public BuyVIPItemAdapter(List<ProductListBean> listBeans) {
        this.listBeans = listBeans;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
//        int itemCount = getItemCount();
////        ProductListBean productListBean = listBeans.get(itemCount);
////
////        BuyVipItemBinding buyVipItemBinding = (BuyVipItemBinding) binding;
////       buyVipItemBinding.yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(binding);
//        View itemView = viewHolder.itemView;
//        TextView viewById = itemView.findViewById(R.id.yuanjia);
//        viewById.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        return viewHolder;
//    }


    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, final BuyVIPItemViewmodel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        BuyVipItemBinding buyVipItemBinding = (BuyVipItemBinding) binding;
        buyVipItemBinding.yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        buyVipItemBinding.woqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setOnItemClick(position);
            }
        });
    }

}
