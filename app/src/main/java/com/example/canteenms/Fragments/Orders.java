package com.example.canteenms.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenms.Adapters.MyListView;
import com.example.canteenms.Models.MyOrder;
import com.example.canteenms.Models.Order;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Image;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orders extends Fragment implements ValueEventListener
{
    private static final String TAG = "OrdersFragment";

    private ListView mListView;
    private List<Order> mData;
    FirebaseUser mUser;

    public Orders()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.orders_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        mListView = view.findViewById(R.id.order_list_view);
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        downloadData();
    }

    private void downloadData()
    {
        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Orders")
                .child(mUser.getUid());
        mRef.addValueEventListener(this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    {
        mData = new ArrayList<>();
        for (DataSnapshot d1 : dataSnapshot.getChildren())
        {
            String dishName = String.valueOf(d1.child("dishName").getValue());
            String clintLocation = String.valueOf(d1.child("clintLocation").getValue());
            String dishQuantity = String.valueOf(d1.child("dishQuantity").getValue());
            boolean isAccepted = Boolean.parseBoolean(String.valueOf(d1.child("accepted").getValue()));
            int imageId = Image.getLocalImageId(dishName);

            Order order = d1.getValue(Order.class);
            assert order != null;
            Log.d(TAG, "onDataChange: Order checking --------------- : " + order.getClintName());
            mData.add(order);
        }

        updateList();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void updateList()
    {
        MyListView listAdapter = new MyListView(getActivity(), mData);
        mListView.setAdapter(listAdapter);
    }
}
