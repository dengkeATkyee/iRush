package com.irush.actitvity;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.view.View;

import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TabHost;
import android.widget.TextView;


import com.irush.fragment.AcountPage;
import com.irush.fragment.ConsultPage;
import com.irush.fragment.HomePage;
import com.irush.fragment.SelectPage;
import com.irush.iml.ViewFactory;
import com.irush.model.ViewModel;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost fragmentTabHost;
    private int[] imgs;
    private String[] texts;
    private Class[] framPage;
    private void initData(){
        imgs=new int[]{R.mipmap.home,R.mipmap.good,R.mipmap.news,R.mipmap.account};
        texts=new String[]{getResources().getString(R.string.home),getResources().getString(R.string.select),
                getResources().getString(R.string.consult),getResources().getString(R.string.info)};
        framPage=new Class[]{HomePage.class, SelectPage.class, ConsultPage.class, AcountPage.class};
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root);
        initData();
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(),android.R.id.tabcontent);
               for (int i = 0; i < texts.length; i++) {
                    TabHost.TabSpec spec=fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));
                       fragmentTabHost.addTab(spec, framPage[i], null);
                       //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                      fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bottom_selector);
                   }
    }
    View getView(int i){
        View view=View.inflate(MainActivity.this, R.layout.bottom_menu, null);
             //取得布局对象
             ImageView imageView=(ImageView) view.findViewById(R.id.home_img);
             TextView textView=(TextView) view.findViewById(R.id.home_text);
            //设置图标
             imageView.setImageResource(imgs[i]);
             //设置标题
              textView.setText(texts[i]);
          return view;
    }
}
