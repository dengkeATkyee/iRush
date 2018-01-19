package com.irush.adater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.irush.actitvity.R;
import com.irush.model.Stock;
import com.irush.netutil.StringUtil;

import java.util.List;
import java.util.Map;



/**
 * description:选股策越分类 适配器
 * Created by dengke on 2017/10/21.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    private Map<String, List<Stock>> categoryMap;
    private List<String> groups;
    private Context context;
    int count=0;
    public CategoryAdapter(Context context,Map categoryMap,List groups){
        this.context=context;
        this.categoryMap=categoryMap;
        this.groups=groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key=groups.get(groupPosition);
        if(key==null) return 1;
        return categoryMap.get(key)==null?1:categoryMap.get(key).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key=groups.get(groupPosition);
        if (key==null) return null;
        return categoryMap.get(key)==null?null:categoryMap.get(key).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * desctiption:设置父item组件
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.expand_parent,null);
        }
        TextView tv= (TextView) convertView.findViewById(R.id.category_parent);
        tv.setText(groups.get(groupPosition));
        return convertView;
    }

    /**
     * description:设置子item组件
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       return getView(inflater,convertView,groupPosition,childPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

     //更新分组数据
     public void updateChildDatas(String key, List<Stock> childArray){
            this.categoryMap.put(key,childArray);
     }

     View getView(LayoutInflater inflater,View convertView,int groupPosition, int childPosition){
         convertView=inflater.inflate(R.layout.expand_children,null);
         TextView stock_name= (TextView) convertView.findViewById(R.id.stock_name);
         TextView stock_code= (TextView) convertView.findViewById(R.id.stock_code);
         TextView stock_price= (TextView) convertView.findViewById(R.id.stock_price);
         TextView stock_increase= (TextView) convertView.findViewById(R.id.stock_increase);
         Stock stock = (Stock) getChild(groupPosition,childPosition);
         if(stock==null){
             stock_price.setText("数据加载失败!");
             return convertView;
         }
         switch (groupPosition){
             case 0:
//                 stock_price.setText(String.valueOf(count));
//                 count++;
                 stock_price.setText("");
                 stock_name.setText(stock.getStockname());
                 stock_code.setText(stock.getStockcode());
                 if(stock.getDdje()<0) stock_increase.setTextColor(Color.parseColor("#1cad1a"));
                 stock_increase.setText(stock.getDdje()+"万");
                 break;
             case 1:
                 stock_name.setText(stock.getStock_name());
                 stock_code.setText(stock.getStock_code());
                 if(stock.getStock_zdf().contains("-")) {
                     stock_price.setTextColor(Color.parseColor("#1cad1a"));
                     stock_increase.setTextColor(Color.parseColor("#1cad1a"));
                 }
                 if(StringUtil.nOfdecimals(stock.getStock_zxj())==1) {
                     stock_price.setText(String.valueOf(stock.getStock_zxj())+"0");
                 }else{
                     stock_price.setText(String.valueOf(stock.getStock_zxj()));
                 };
                 stock_increase.setText(stock.getStock_zdf());
                 break;
         }
        return convertView;

    }
}
