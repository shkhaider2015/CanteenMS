package com.example.canteenms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.canteenms.Models.MyOrder;

import java.util.List;

public class MyListView extends BaseAdapter {

    private Context mCTX;
    private List<MyOrder> mData;
    LayoutInflater layoutInflater;

    public MyListView(Context mCTX, List<MyOrder> mData)
    {
        this.mCTX = mCTX;
        this.mData = mData;
        layoutInflater = (LayoutInflater.from(mCTX));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
