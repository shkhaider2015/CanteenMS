package com.example.canteenms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenms.Models.Message;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Calculation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    private static final int VIEW_TYPE_MESSAGE_SENT = 2;

    private Context mCTX;
    private List<Message> dataModel;
    private FirebaseUser mUser;

    public ChatAdapter(Context mCTX, List<Message> dataModel, FirebaseUser mUser)
    {
        this.mCTX = mCTX;
        this.dataModel = dataModel;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SenderMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_recieved, parent, false);
            return new ReceiverMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Message message = dataModel.get(position);

        switch (holder.getItemViewType())
        {
            case VIEW_TYPE_MESSAGE_SENT:
                //
                ((SenderMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                //
                ((ReceiverMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return dataModel.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        Message message = dataModel.get(position);

        if (mUser.getUid().equals(message.getSenderId()))
            return VIEW_TYPE_MESSAGE_SENT;
        else
            return VIEW_TYPE_MESSAGE_RECEIVED;

    }

    private class SenderMessageHolder extends RecyclerView.ViewHolder
    {
        TextView mMessage, mTime;

        public SenderMessageHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View v)
        {
            mMessage =  v.findViewById(R.id.sent_text_message_body);
            mTime = v.findViewById(R.id.sent_text_message_time);
        }

        void bind(Message message)
        {
            long time = message.getMsgTime();
            mMessage.setText(message.getMsg());
            mTime.setText(Calculation.getDate(time, "dd/MM/yyyy hh:mm:ss.SSS"));
        }
    }

    private class ReceiverMessageHolder extends RecyclerView.ViewHolder
    {
        CircleImageView mImage;
        TextView mName, mMessage, mTime;

        public ReceiverMessageHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View v)
        {
           mImage = v.findViewById(R.id.received_image_message_profile);
           mName = v.findViewById(R.id.received_text_message_name);
           mMessage = v.findViewById(R.id.received_text_message_body);
           mTime = v.findViewById(R.id.received_text_message_time);
        }

        void bind(Message message)
        {
            Picasso
                    .get()
                    .load(message.getSenderProfileURI())
                    .placeholder(R.drawable.ic_profile_80_80)
                    .into(mImage);

            mName.setText(message.getSenderName());
            mMessage.setText(message.getMsg());
            mTime.setText(Calculation.getDate(message.getMsgTime(), "dd/MM/yyyy hh:mm:ss.SSS"));
        }
    }
}
