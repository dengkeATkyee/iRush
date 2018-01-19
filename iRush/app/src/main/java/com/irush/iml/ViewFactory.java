package com.irush.iml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.irush.actitvity.R;
import com.irush.dao.UIUtil;
import com.irush.model.ViewModel;

/**
 * Created by 莴佑黔 on 2017/10/21.
 */

public class ViewFactory implements UIUtil {

    @Override
    public View cerateView(Context context, ViewModel viewModel) {
        View v=getInflater(context).inflate(viewModel.getLayout(),null);

        switch (viewModel.getLayout()){
            case R.layout.bottom_menu:
                break;
            case R.layout.expand_children:
                break;
            case R.layout.expand_parent:
                break;
        }

        return v;
    }

    private LayoutInflater getInflater(Context context){
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
