package com.irush.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.irush.actitvity.R;
import com.irush.adater.CategoryAdapter;
import com.irush.adater.IndexAdapter;
import com.irush.dto.NewRecordDto;
import com.irush.dto.TopFiveDto;
import com.irush.model.Stock;
import com.irush.netutil.HttpUtils;
import com.irush.netutil.StringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 莴佑黔 on 2017/10/22.
 */

public class HomePage extends Fragment {
    private GridView gridView;
    private List<Map<String,Object>> index_data;
    private ExpandableListView categoryList;
    private List groups;
    private Map categoryMap;
    private TopFiveDto topFiveDto;
    private NewRecordDto newRecordDto;
    private HttpUtils httpUtils;
    private View v;
    private String endpoint;
    IndexAdapter indexAdapter;
    CategoryAdapter categoryAdapter;
    private Context context;
    private String[] classification;
    private String[] index_class;
    private  ScheduledExecutorService pool;
    private final Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (this) {
                if (msg.what == 1) {
                    refreshClassfiedData();
                } else if (msg.what == 2) {
                    refreshIndexData();
                }
                super.handleMessage(msg);
            }
        }
    };
    //更新分组数据的定时器
    private final TimerTask timerTaskGroup=new TimerTask(){
        @Override
        public void run() {
             updateGroupFromNet();
             Message message=new Message();
             message.what=1;
            handler.sendMessage(message);
        }
    };
    //更新指数的定时任务
    private final TimerTask timerTaskIndex=new TimerTask(){
        @Override
        public void run() {
            updateIndexFromNet();
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
        }
    };
    public  void initPage(){
        //指数适配器初始化
        indexAdapter = new IndexAdapter(context,index_data,R.layout.index_layout);
        gridView.setAdapter(indexAdapter);
        //选股分类界面初始化
        groups= Arrays.asList(classification);
        categoryAdapter = new CategoryAdapter(context, categoryMap, groups);
        categoryList.setAdapter(categoryAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        httpUtils=new HttpUtils(context);
        index_data=new ArrayList<Map<String,Object>>();
        categoryMap=new HashMap<String,List<Object>>();
        endpoint=context.getResources().getString(R.string.endpoint);
        classification=new String[]{context.getResources().getString(R.string.top_5),
                context.getResources().getString(R.string.n_record),
                context.getResources().getString(R.string.org_own),
                context.getResources().getString(R.string.cash_in)};
        index_class=new String[]{context.getResources().getString(R.string.szzs),
                              context.getResources().getString(R.string.szcz),
                                  context.getResources().getString(R.string.cybz)};
        Map<String,Object> adData=null;
        for(int i=0;i<index_class.length;i++) {
            adData=new HashMap<String,Object>();
            adData.put("where",index_class[i]);
            index_data.add(adData);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_main, container,false);
        categoryList= (ExpandableListView) v.findViewById(R.id.category_list);
        gridView = (GridView) v.findViewById(R.id.index);
        //categoryList.setGroupIndicator(this.getResources().getDrawable(R.drawable.class_sel,getResources().getDrawable(R.style.AppTheme)));
        initPage();
        categoryList.expandGroup(0);
        pool = Executors.newScheduledThreadPool(2);
        pool.scheduleAtFixedRate(timerTaskIndex, 0,3000, TimeUnit.MILLISECONDS);
        pool.scheduleAtFixedRate(timerTaskGroup,0,30*60000, TimeUnit.MILLISECONDS);
        return  v;
   }
    @Override
    public void onDestroyView() {
        pool.shutdownNow();
        super.onDestroyView();
    }
    public void updateGroupFromNet(){
        try {
            topFiveDto= (TopFiveDto) httpUtils.doGet(TopFiveDto.class,endpoint,context.getResources().getString(R.string.topfive),null);
            Map<String,Object> nr_param=new HashMap<String, Object>();
            nr_param.put("board",4);
            newRecordDto=(NewRecordDto) httpUtils.doGet(NewRecordDto.class,endpoint,context.getResources().getString(R.string.cxg),nr_param);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   private void refreshClassfiedData(){
        if(topFiveDto!=null&&topFiveDto.getRetcode().equals("0")){
            List<Stock> stocks =topFiveDto.getResult().getDesc();
            stocks.addAll(topFiveDto.getResult().getAsc());
            categoryAdapter.updateChildDatas(classification[0],stocks);
        }
        if(newRecordDto!=null&&newRecordDto.getRetcode().equals("0")) {

            categoryAdapter.updateChildDatas(classification[1],newRecordDto.getResult());
        }
       categoryAdapter.notifyDataSetChanged();
    }
    String shindex,szindex,cyindex;
    private void updateIndexFromNet(){
        try {
            shindex = httpUtils.doGet(context.getResources().getString(R.string.index_url), context.getResources().getString(R.string.shapi), "");
            szindex = httpUtils.doGet(context.getResources().getString(R.string.index_url), context.getResources().getString(R.string.szapi), "");
            cyindex = httpUtils.doGet(context.getResources().getString(R.string.index_url), context.getResources().getString(R.string.cyapi), "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void refreshIndexData(){
        String[] indexGroup;
        String[] from={"index_num","increase_num","increase_per"};
        if((indexGroup=StringUtil.getMatchedArray(shindex))!=null){
            indexAdapter.updateIndex(0,from,indexGroup);
        }
        if((indexGroup=StringUtil.getMatchedArray(szindex))!=null){
            indexAdapter.updateIndex(1,from,indexGroup);
        }
        if((indexGroup=StringUtil.getMatchedArray(cyindex))!=null){
            indexAdapter.updateIndex(2,from,indexGroup);
        }
        indexAdapter.notifyDataSetChanged();
    }

}
