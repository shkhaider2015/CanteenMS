package com.example.canteenms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenms.Models.MyOrder;
import com.example.canteenms.Models.Order;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Image;

import java.util.List;

public class MyListView extends BaseAdapter {

    private Context mCTX;
    private List<Order> mData;
    LayoutInflater layoutInflater;

    public MyListView(Context mCTX, List<Order> mData)
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

        ImageView imageView;
        TextView mDishName, mClintLocation, mAccepted;
        Button mComplete;
        Order order = mData.get(position);

        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_view_item, null);

        // initialization
        imageView = convertView.findViewById(R.id.list_item_image);
        mDishName = convertView.findViewById(R.id.list_item_dish_name);
        mClintLocation = convertView.findViewById(R.id.list_item_dish_location);
        mAccepted = convertView.findViewById(R.id.list_item_accept_text);
        mComplete = convertView.findViewById(R.id.list_item_complete_button);

        // setting data
        imageView.setImageResource(Image.getLocalImageId(order.getDishName()));
        mDishName.setText(order.getDishName());
        mClintLocation.setText(order.getClintLocation());

        if (order.isAccepted())
        {
            mAccepted.setText(R.string.accept);
            if (order.isCompleted())
            {
                mComplete.setText(R.string.completed);
                mComplete.setBackgroundResource(R.drawable.bg_success);
                mComplete.setEnabled(false);
            }
            else
            {
                mComplete.setText(R.string.complete);
                mComplete.setBackgroundResource(R.drawable.bg_success_not);
                mComplete.setEnabled(true);
            }
        }
        else
        {
            if (order.isCancelled())
            {
                mAccepted.setText(R.string.empty);
                mComplete.setText(R.string.cancelled);
                mComplete.setBackgroundResource(R.drawable.bg_danger);
                mComplete.setEnabled(false);
            }
            else
            {
                mAccepted.setText(R.string.pending);
                mComplete.setText(R.string.cancel);
                mComplete.setBackgroundResource(R.drawable.bg_orange);
                mComplete.setEnabled(true);
            }
        }



        return convertView;
    }

}
