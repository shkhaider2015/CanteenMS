package com.example.canteenms.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenms.Models.Message;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Calculation;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    private static final int VIEW_TYPE_MESSAGE_SENT = 2;

    private Context mCTX;
    private List<Message> dataModel;

    public ChatAdapter(Context mCTX, List<Message> dataModel) {
        this.mCTX = mCTX;
        this.dataModel = dataModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
            mMessage.setText(message.getMsg());
            mTime.setText(Calculation.getDate(Integer.parseInt(message.getMsgTime()), "dd/MM/yyyy hh:mm:ss.SSS"));
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

        }
    }
}
