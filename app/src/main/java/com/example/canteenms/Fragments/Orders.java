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

import com.example.canteenms.Models.MyOrder;
import com.example.canteenms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Orders extends Fragment implements ValueEventListener
{
    private static final String TAG = "OrdersFragment";

    private ListView mListView;
    private List<MyOrder> mData;
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
        for (DataSnapshot d1 : dataSnapshot.getChildren())
        {
            Log.d(TAG, "onDataChange: d1" + d1);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
