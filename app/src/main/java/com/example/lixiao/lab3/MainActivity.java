package com.example.lixiao.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListView mListView;

    private List<GoodsInfo> goods_list;
    private List<GoodsInfo> cart_goods;

    private CartAdapter cartAdapter;
    private ListAdapter mAdapter;
    private ImageButton switch_btn;
    private LinearLayout ll;
    DynamicReceiver dynamicReceiver = new DynamicReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        EventBus.getDefault().register(this);//注册订阅者

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("dynamic");//添加动态广播的action
        registerReceiver(dynamicReceiver, intentFilter);//注册自定义动态广播消息

        ll = (LinearLayout) findViewById(R.id.shop_cart);
        ll.setVisibility(View.INVISIBLE);

        final String[] Name = new String[]{
                "Enchated Forest",
                "Arla Milk",
                "Devondale Milk",
                "Kindle Oasis",
                "waitrose 早餐麦片",
                "Mcvitie's 饼干",
                "Ferrero Rocher",
                "Maltesers",
                "Lindt",
                "Borggreve"
        };
        final String[] Price = new String[]{
                "¥ 5.00",
                "¥ 59.00",
                "¥ 79.00",
                "¥ 2399.00",
                "¥ 179.00",
                "¥ 14.00",
                "¥ 132.59",
                "¥ 141.43",
                "¥ 139.43",
                "¥ 28.90"
        };
        final String[] Info = new String[]{
                "作者  Johanna Basford",
                "产地  德国",
                "产地  澳大利亚",
                "版本  8GB",
                "重量  2Kg",
                "产地  英国",
                "重量  300g",
                "重量  118g",
                "重量  249g",
                "重量  640g"
        };
        final int[] shopItemPic = {
                R.mipmap.enchatedforest,
                R.mipmap.arla,
                R.mipmap.devondale,
                R.mipmap.kindle,
                R.mipmap.waitrose,
                R.mipmap.mcvitie,
                R.mipmap.ferrero,
                R.mipmap.maltesers,
                R.mipmap.lindt,
                R.mipmap.borggreve
        };
        Random random = new Random();
        int i = random.nextInt(10);//返回0到9的整数随机数
        Intent intentBroadcast = new Intent("randomRecommendation");
        Bundle mBundle = new Bundle();
        mBundle.putString("Name", Name[i]);
        mBundle.putString("Price",Price[i]);
        mBundle.putString("Info",Info[i]);
        mBundle.putInt("Image", shopItemPic[i]);
        intentBroadcast.putExtras(mBundle);
        sendBroadcast(intentBroadcast);//将图片和文字内容发送出去

        goods_list =new ArrayList<GoodsInfo>();
        cart_goods =new ArrayList<GoodsInfo>();

        for (int ii = 0; ii < Name.length; ii++) {
            goods_list.add(new GoodsInfo(Name[ii],Info[ii],Price[ii]));
        }

        cartAdapter = new CartAdapter(MainActivity.this, cart_goods);

        mRecyclerView = (RecyclerView) findViewById(R.id.shop_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//线性显示，类似于listview
        mAdapter = new ListAdapter(goods_list, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);//使用自定义的adapter为RecyclerView填充数据
        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, GoodsDetail.class);
                intent.putExtra("Name", goods_list.get(position).getName());
                intent.putExtra("Price", goods_list.get(position).getprice());
                intent.putExtra("Info", goods_list.get(position).getInfo());
                //startActivityForResult(intent, 0);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"个商品",Toast.LENGTH_SHORT).show();
                goods_list.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });

        mListView = (ListView) findViewById(R.id.cart);
        mListView.setAdapter(cartAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, final int i,long l){
                Intent intent = new Intent(MainActivity.this, GoodsDetail.class);
                intent.putExtra("Name", cart_goods.get(i).getName());
                intent.putExtra("Price", cart_goods.get(i).getprice());
                intent.putExtra("Info", cart_goods.get(i).getInfo());
                //startActivityForResult(intent, 0);
                startActivity(intent);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品")
                        .setMessage("从购物车移除"+cart_goods.get(i).getName()+"?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (cart_goods.remove(i)!=null )
                                    cartAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) { }
                        })
                        .show();
                return true;
            }
        });


        switch_btn = (ImageButton) findViewById(R.id.switch_button);
        switch_btn.setTag("0");
        switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_btn.getTag() == "0") {
                    switch_btn.setImageResource(R.mipmap.mainpage);
                    switch_btn.setTag("1");
                    ll.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                } else {
                    switch_btn.setImageResource(R.mipmap.shoplist);
                    switch_btn.setTag("0");
                    ll.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==0 && resultCode==0){
            Bundle bud=intent.getExtras();
            String str=bud.getString("na");
            String pri=bud.getString("Pri");
            String info=bud.getString("info");
            int cnt=bud.getInt("cnt",0);
            for(int i = 0; i < cnt; i++){
                cart_goods.add(new GoodsInfo(str,info,pri));
            }
            cartAdapter.notifyDataSetChanged();
        }
    }
*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent event){
        GoodsInfo shoppingItem = event.getGoodsInfo();
        cart_goods.add(shoppingItem);
        cartAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onNewIntent(Intent intent){
        ll.setVisibility(View.INVISIBLE);
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras != null) {
            if(extras.getString("add_in_shoplist").equals("yes")) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                ll.setVisibility(View.VISIBLE);
                switch_btn.setTag("1");
                switch_btn.setImageResource(R.mipmap.mainpage);
            }
        }
        setIntent(intent);
    }
}

