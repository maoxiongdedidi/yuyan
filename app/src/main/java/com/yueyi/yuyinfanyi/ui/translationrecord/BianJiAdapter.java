package com.yueyi.yuyinfanyi.ui.translationrecord;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class BianJiAdapter  extends BindingRecyclerViewAdapter {
    @BindingAdapter(value = {"lockurl"}, requireAll = false)
    public static void setlockurl(ImageView imageView, int url) {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(200))).into(imageView);
    }
}
