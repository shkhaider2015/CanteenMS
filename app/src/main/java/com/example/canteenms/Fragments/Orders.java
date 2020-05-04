package com.example.canteenms.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenms.R;

public class Orders extends Fragment
{
    ListView mListView;
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
    }
}
