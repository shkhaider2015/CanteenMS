package com.example.canteenms.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.canteenms.Models.MyOrder;
import com.example.canteenms.Models.Order;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Image;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyListView extends BaseAdapter{

    private Context mCTX;
    private List<Order> mData;

    public MyListView(Context mCTX, List<Order> mData)
    {
        this.mCTX = mCTX;
        this.mData = mData;
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
        final Order order = mData.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(mCTX).inflate(R.layout.list_view_item, null);
//            convertView = layoutInflater.inflate(R.layout.list_view_item, null);

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

        Log.d(TAG, "getView: accepted : " + order.isAccepted() + " iscompleted : " + order.isCompleted() + " icCancelled : " + order.isCancelled());

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
                mComplete.setTextColor(ContextCompat.getColor(mCTX, R.color.white));
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

        mComplete.setOnClickListener(new View.OnClickListener()
        {
            DatabaseReference mRef = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Orders")
                    .child(order.getClintUID())
                    .child(order.getOrderTime());
            @Override
            public void onClick(View v)
            {


                if (order.isAccepted())
                {
                    Toast.makeText(mCTX, "Complete button Clicked", Toast.LENGTH_SHORT).show();
                    mRef.child("completed")
                            .setValue(true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Log.d(TAG, "onComplete: Complete set to True");
                                    }
                                    else
                                    {
                                        Log.w(TAG, "onComplete: ERROR : " + task.getException() );
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }
                else
                {
                    Toast.makeText(mCTX, "Cancel button Clicked", Toast.LENGTH_SHORT).show();
                    mRef.child("cancelled")
                            .setValue(true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Log.d(TAG, "onComplete: Cancelled set to True");
                                    }
                                    else
                                    {
                                        Log.w(TAG, "onComplete: ERROR : " + task.getException() );
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }

            }
        });


        return convertView;
    }





}
