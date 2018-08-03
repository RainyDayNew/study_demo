package com.demo.resetmysoul.study_demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.resetmysoul.study_demo.R;

import java.util.ArrayList;

/**
 * Created by Mr. Huang on 2018/4/2.
 * Type:
 * des:
 */

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.MyViewHodler> {

    private static final int TYPE_HEAD = 950;
    private static final int TYPE_BODY_ITEM = 515;

    private View mHeaderView;
    private Context mContext;

    public void setArrayList(ArrayList<Integer> arrayList) {
        mArrayList = arrayList;
    }

    private ArrayList<Integer> mArrayList;

    public NormalAdapter(View headerView) {
        mHeaderView = headerView;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        if(TYPE_HEAD == viewType){
//            TextView textView = new TextView(parent.getContext());
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            return new MyViewHodler(view);
        }else {
            TextView textView = new TextView(parent.getContext());
            return new MyViewHodler(textView);
        }


    }

    @Override
    public void onBindViewHolder(MyViewHodler holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_HEAD :
                TextView itemView1 = (TextView) holder.itemView;
                itemView1.setText("ahhahhhaha："+mArrayList.get(position));
                break;
            case TYPE_BODY_ITEM:
                TextView itemView = (TextView) holder.itemView;
                itemView.setText("ahhahhhaha："+mArrayList.get(position-1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_BODY_ITEM;
        }
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        public MyViewHodler(View itemView) {
            super(itemView);
        }
    }
}
