package com.irush.adater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.irush.actitvity.R;
import com.irush.netutil.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by 莴佑黔 on 2017/11/3.
 */

public class IndexAdapter extends BaseAdapter {
    private List<Map<String,Object>> index_data;
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private String[] from={"where","index_num","increase_num","increase_per"};
    public IndexAdapter(Context context,List<Map<String,Object>>index_data,int layout){
        this.context=context;
        this.index_data=index_data;
        this.layout=layout;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return index_data.size();
    }

    @Override
    public Object getItem(int position) {
        return index_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView=inflater.inflate(layout,null);
        TextView where= (TextView) convertView.findViewById(R.id.where);
        TextView index_num= (TextView) convertView.findViewById(R.id.index_num);
        TextView increase_num= (TextView) convertView.findViewById(R.id.increase_num);
        TextView increase_per= (TextView) convertView.findViewById(R.id.increase_per);
        Map<String,Object> index_unit= (Map<String, Object>) getItem(position);
        where.setText(index_unit.get(from[0]).toString());
        index_num.setText(exsitElement(index_unit,from[1])?index_unit.get(from[1]).toString():"--");
        increase_num.setText(exsitElement(index_unit,from[2])?index_unit.get(from[2]).toString():"--");
        increase_per.setText(exsitElement(index_unit,from[3])?index_unit.get(from[3]).toString()+"%":"--");
        if(exsitElement(index_unit,from[2])&&Double.parseDouble(increase_num.getText().toString())<0){
            index_num.setTextColor(Color.parseColor("#1cad1a"));
            increase_num.setTextColor(Color.parseColor("#1cad1a"));
            increase_per.setTextColor(Color.parseColor("#1cad1a"));
        }
        return convertView;
    }
    private boolean exsitElement(Map<String,Object> index_unit,String key){
        if(index_unit.get(key)==null) return false;
        if(StringUtil.isEmpty(index_unit.get(key).toString())||index_unit.get(key).toString()=="--") return false;
        return true;
    }
    public void updateIndex(int position,String[] keys,String[] values){
        Map<String,Object> child=index_data.get(position);
        for(int i=0;i<keys.length;i++){
            child.put(keys[i],values[i]);
        }
    }
}
