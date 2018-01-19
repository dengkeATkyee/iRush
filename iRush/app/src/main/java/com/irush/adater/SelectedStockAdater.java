package com.irush.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.irush.actitvity.R;
import com.irush.model.Stock;

import java.util.List;

/**
 * Created by 莴佑黔 on 2017/10/28.
 */

public class SelectedStockAdater extends BaseAdapter {
    private Context context;
    private List<Stock> stocks;
    private LayoutInflater inflater;


    public SelectedStockAdater(Context context,List<Stock> stocks){
        this.context=context;
        this.stocks = stocks;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return stocks.size();
    }

    @Override
    public Object getItem(int position) {
        return stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         if(convertView!=null){
             return convertView;
         }
        convertView=inflater.inflate(R.layout.expand_children,null);
        TextView stock_name= (TextView) convertView.findViewById(R.id.stock_name);
        TextView stock_code= (TextView) convertView.findViewById(R.id.stock_code);
        TextView stock_price= (TextView) convertView.findViewById(R.id.stock_price);
        TextView stock_increase= (TextView) convertView.findViewById(R.id.stock_increase);
        Stock stock = (Stock) getItem(position);
        
        stock_name.setText(stock.getStockname());
        stock_code.setText(stock.getStockcode());
        stock_price.setText(stock.getPrice());
        stock_increase.setText(stock.getIncre_percent());

        return convertView;
    }
}
