package com.yueyi.yuyinfanyi.ui.checklanguage;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class MyAdapter extends BindingRecyclerViewAdapter {
    @BindingAdapter(value = {"url"}, requireAll = false)
    public static void setImageUri(ImageView imageView, int url) {
       Glide.with(imageView.getContext()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(200))).into(imageView);
    }
    @BindingAdapter(value = {"lockurl"}, requireAll = false)
    public static void setlockurl(ImageView imageView, int url) {
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.bitmapTransform(new RoundedCorners(200))).into(imageView);
    }
}
