package com.example.canteenms.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenms.Adapters.ChatAdapter;
import com.example.canteenms.Models.Message;
import com.example.canteenms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Chat extends Fragment implements View.OnClickListener, ValueEventListener, View.OnFocusChangeListener {

    private static final String TAG = "Chat";

    private List<Message> dataModel;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private EditText mChatText;
    private Button mSend;

    private DatabaseReference mRef;
    private FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        init(view);

        return view;
    }

    private void init(View view)
    {
        mChatText = view.findViewById(R.id.chat_edittext_chatbox);
        mSend = view.findViewById(R.id.chat_button_chatbox_send);

        mRecyclerView = view.findViewById(R.id.chat_reyclerview_message_list);

        mUser = FirebaseAuth
                .getInstance()
                .getCurrentUser();

        mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Chat");

        mRef.addValueEventListener(this);
        mSend.setOnClickListener(this);
        mChatText.setOnFocusChangeListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    {
        dataModel = new ArrayList<>();

        for (DataSnapshot d : dataSnapshot.getChildren())
        {
            Message message = d.getValue(Message.class);
            dataModel.add(message);
            Log.d(TAG, "onDataChange: MESSAGE : " + message.getMsg());
        }
        updateUI();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError)
    {

    }

    @Override
    public void onClick(View v)
    {
        String msg = mChatText.getText().toString();
        long time = Calendar.getInstance().getTimeInMillis();
        final String timekwy = String.valueOf(time);

        if (v.getId() != R.id.chat_button_chatbox_send)
            return;
        if (msg.isEmpty())
        {
            mChatText.setError("Field is empty");
            mChatText.requestFocus();
            return;
        }

        final Message message = new Message(mUser.getUid(), mUser.getDisplayName(), mUser.getEmail(), mUser.getPhotoUrl().toString(), time, msg);

        mRef.child(timekwy).setValue(message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            DatabaseReference reference = FirebaseDatabase
                                    .getInstance()
                                    .getReference()
                                    .child("Chat")
                                    .child(timekwy);
                            reference.setValue(message)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: Chat Activity Both Side Success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure:  Chat Activity One side failure");
                                        }
                                    });

                        }
                        else
                        {
                            Log.d(TAG, "onComplete: Chat Activity Message Send is not Success");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "onFailure: Chat Activity : ", e);
                    }
                });

        mChatText.setText("");

    }

    private void updateUI()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new ChatAdapter(getActivity(), dataModel, mUser);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            hideKeyboard(v);

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
