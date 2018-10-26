package com.example.lixiao.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GoodsDetail extends AppCompatActivity {
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        final Intent intent = getIntent();
        setResult(1,intent);

        final String ShowName = intent.getStringExtra("Name");
        final String Price = intent.getStringExtra("Price");
        final String info = intent.getStringExtra("Info");

        final String[] s = new String[]{"一键下单","分享商品","不感兴趣","查看更多促销信息"};
        ArrayList ss = new ArrayList<String>();
        for(int i=0; i<s.length; i++) ss.add(s[i]);
        ListView llv = (ListView)findViewById(R.id.function);
        MoreInfAdapter fun = new MoreInfAdapter(GoodsDetail.this, ss);
        llv.setAdapter(fun);

        ImageView img = (ImageView)findViewById(R.id.pic);
        switch (ShowName){
            case "Enchated Forest":
                img.setImageResource(R.mipmap.enchatedforest);
                break;
            case "Arla Milk":
                img.setImageResource(R.mipmap.arla);
                break;
            case "Devondale Milk":
                img.setImageResource(R.mipmap.devondale);
                break;
            case "Kindle Oasis":
                img.setImageResource(R.mipmap.kindle);
                break;
            case "waitrose 早餐麦片":
                img.setImageResource(R.mipmap.waitrose);
                break;
            case "Mcvitie's 饼干":
                img.setImageResource(R.mipmap.mcvitie);
                break;
            case "Ferrero Rocher":
                img.setImageResource(R.mipmap.ferrero);
                break;
            case "Maltesers":
                img.setImageResource(R.mipmap.maltesers);
                break;
            case "Lindt":
                img.setImageResource(R.mipmap.lindt);
                break;
            case "Borggreve":
                img.setImageResource(R.mipmap.borggreve);
                break;
        }

        ImageButton bb = (ImageButton) findViewById(R.id.back);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView na = (TextView) findViewById(R.id.nam);
        na.setText(ShowName);
        TextView pr = (TextView) findViewById(R.id.price);
        pr.setText(Price);
        TextView inf = (TextView) findViewById(R.id.cate);
        inf.setText(info);

        final ImageButton Star = (ImageButton) findViewById(R.id.star);
        Star.setTag("0");
        Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Star.getTag() == "0") {
                    Star.setBackgroundResource(R.mipmap.full_star);
                    Star.setTag("1");
                } else {
                    Star.setBackgroundResource(R.mipmap.empty_star);
                    Star.setTag("0");
                }
            }
        });

        ImageButton shopcart = (ImageButton) findViewById(R.id.addshopcar);
        shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GoodsDetail.this, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                cnt++;
                intent.putExtra("cnt",cnt);
                intent.putExtra("na",ShowName);
                intent.putExtra("Pri",Price);
                intent.putExtra("info",info);
                setResult(0, intent);
            }
        });
    }
}
