package com.yueyi.yuyinfanyi.ui.translationrecord;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<CacheBaseBean> cacheBaseBeans;
    CallBack callBack;
    public MyAdapter(Context context, List<CacheBaseBean> cacheBaseBeans, CallBack callBack) {
        this.context = context;
        this.cacheBaseBeans = cacheBaseBeans;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.translation_record_item,null);
        //返回MyViewHolder的对象
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.yuanwen.setText(cacheBaseBeans.get(position).getCacheDataBean().getYuanwen());
        holder.fanyi.setText(cacheBaseBeans.get(position).getCacheDataBean().getFanyi());
        String db_id = cacheBaseBeans.get(position).getDb_Id();
        long l = Long.valueOf(db_id) / 1000;
        String s = Utils.timeStamp2Date(l + "", "MM-dd HH:mm");
        holder.time.setText(s);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.delete(position);
            }
        });
        holder.kuozhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.kuozhan(position);
            }
        });
        holder.yuedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.yuedu(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.allview(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cacheBaseBeans.size();
    }
    //继承RecyclerView.ViewHolder抽象类的自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView yuanwen,fanyi,time;
        ImageView delete,kuozhan,yuedu;
        public MyViewHolder(View itemView) {
            super(itemView);
            yuanwen = (TextView) itemView.findViewById(R.id.translate_cache_yuanwen);
            fanyi=(TextView)itemView.findViewById(R.id.translate_cache_fanyi);
            time=(TextView)itemView.findViewById(R.id.translate_cache_time);
            delete = (ImageView) itemView.findViewById(R.id.translate_cache_delete);
            kuozhan=(ImageView)itemView.findViewById(R.id.translate_cache_kuozhan);
            yuedu=(ImageView)itemView.findViewById(R.id.translate_cache_yuedu);
        }
    }



    public interface CallBack{
        void delete(int position);
        void kuozhan(int position);
        void yuedu(int position);
        void allview(int position);
    }


}
