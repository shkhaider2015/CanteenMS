package com.example.canteenms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenms.Models.Food;
import com.example.canteenms.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    List<Food> mDataList;
    Context mCTX;

    public GridAdapter(List<Food> mDataList, Context mCTX) {
        this.mDataList = mDataList;
        this.mCTX = mCTX;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView mFoodImage;
        TextView mFoodName;
        Food food = mDataList.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(mCTX).inflate(R.layout.grid_item, null);

        mFoodImage = convertView.findViewById(R.id.grid_item_image);
        mFoodName = convertView.findViewById(R.id.grid_item_name);

        Picasso
                .get()
                .load(food.getFoodImageUri())
                .placeholder(R.mipmap.ic_spin)
                .into(mFoodImage);
        mFoodName.setText(food.getFoodName());

        return convertView;
    }
}
