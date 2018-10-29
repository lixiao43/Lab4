package com.example.lixiao.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsInfo> cargood;

    public CartAdapter(Context context, List<GoodsInfo> cargood) {
        this.context = context;
        this.cargood=cargood;
    }
    @Override
    public int getCount() {
        if (cargood != null) {
            return cargood.size();
        } else return 0;
    }
    @Override
    public Object getItem(int i) {
        if (cargood == null) {
            return null;
        } else {
            return cargood.get(i);
        }
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {//获取数据项的布局样式
        View convertView;
        ViewHolder holder;
        if (view == null) {//view为空时才加载布局，并创建一个viewHolder
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, null);
            //通过inflate的方法加载布局
            holder = new ViewHolder();
            holder.pri =convertView.findViewById(R.id.firstLetter);
            holder.fir =convertView.findViewById(R.id.name);
            holder.nam =convertView.findViewById(R.id.price);
            convertView.setTag(holder);//用setTag的方法将处理好的viewHolder放入view中
        } else {//否则让convertView等于view，然后从中取出viewHolder
            convertView = view;
            holder = (ViewHolder) convertView.getTag();
        }
        //从viewHolder中取出对应的对象，然后赋值给它们
        holder.pri.setText(cargood.get(position).getName().substring(0,1).toUpperCase());
        holder.fir.setText(cargood.get(position).getName());
        holder.nam.setText(cargood.get(position).getprice());
        return convertView;//返回处理好的view
    }
    private class ViewHolder {
        public TextView fir;
        public TextView nam;
        public TextView pri;
    }

}
