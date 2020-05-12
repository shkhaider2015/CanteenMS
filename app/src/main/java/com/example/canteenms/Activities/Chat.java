package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.canteenms.Adapters.ChatAdapter;
import com.example.canteenms.Models.Message;
import com.example.canteenms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class Chat extends AppCompatActivity implements ValueEventListener, View.OnClickListener {

    private static final String TAG = "Chat";

    private List<Message> dataModel;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private EditText mChatText;
    private Button mSend;

    private DatabaseReference mRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

    }

    private void init()
    {
        mChatText = findViewById(R.id.chat_edittext_chatbox);
        mSend = findViewById(R.id.chat_button_chatbox_send);

        mRecyclerView = findViewById(R.id.chat_reyclerview_message_list);

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
        String time = String.valueOf(Calendar.getInstance().getTimeInMillis());

        if (v.getId() != R.id.chat_button_chatbox_send)
            return;
        if (msg.isEmpty())
        {
            mChatText.setError("Field is empty");
            mChatText.requestFocus();
            return;
        }

        Message message = new Message(mUser.getUid(), mUser.getDisplayName(), mUser.getEmail(), mUser.getPhotoUrl().toString(), time, msg);

        mRef.child(time).setValue(message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

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


    }

    private void updateUI()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Chat.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new ChatAdapter(getApplicationContext(), dataModel, mUser);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
}
