package com.example.lixiao.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{//ViewHolder通常出现在适配器，为listview，RecyclerView滚动的时候快速设置值，不必每次都重新创建很多对象，提升性能。
    private OnItemClickListener mOnItemClickListener = null;
    private List<GoodsInfo> good;
    private Context context;
    public ListAdapter(List<GoodsInfo> good, Context context){
        this.good=good;
        this.context=context;
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(context).inflate(R.layout.goods_item,parent,false);//创建item视图，并返回相对应的viewHolder
        MyViewHolder holder=new MyViewHolder(view);//获取viewHolder的实例
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){//绑定数据到正确的item视图上
        holder.firstl.setText(good.get(position).getName());
        holder.goodsname.setText(good.get(position).getName().substring(0,1).toUpperCase());
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return good.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView firstl;
        TextView goodsname;
        public MyViewHolder(View view){
            super(view);
            firstl =(TextView)view.findViewById(R.id.name);
            goodsname =(TextView)view.findViewById(R.id.firstLetter);
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
}

