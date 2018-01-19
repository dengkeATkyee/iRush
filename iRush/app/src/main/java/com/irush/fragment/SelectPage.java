package com.irush.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.irush.actitvity.R;
import com.irush.adater.SelectedStockAdater;
import com.irush.model.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 莴佑黔 on 2017/10/22.
 */

public class SelectPage extends Fragment {
    private View v;
    private List<Stock> stockList;
    private ListView listView;
    private SelectedStockAdater selectedStockAdater;
    public void initData(){
        stockList =new ArrayList<Stock>();
        Stock stock =new Stock("科大讯飞","002230","50.03","+1.53%");
        for(int i=0;i<7;i++){
            stockList.add(stock);
        }
        selectedStockAdater=new SelectedStockAdater(getContext(), stockList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.select,container, false);
        initData();
        listView= (ListView) v.findViewById(R.id.selected_stock);
        listView.setAdapter(selectedStockAdater);
        return  v;
    }
}
