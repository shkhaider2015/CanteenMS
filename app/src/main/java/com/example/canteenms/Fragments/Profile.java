package com.example.canteenms.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.canteenms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Profile extends Fragment {

    CircleImageView imageView;
    TextView mFullName, mEmail, mTotal, mComplete, mCancel;

    FirebaseUser mUser;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(view);
        downloadData();
        return view;
    }

    private void init(View view)
    {
        imageView = view.findViewById(R.id.profile_image);
        mFullName = view.findViewById(R.id.profile_name);
        mEmail = view.findViewById(R.id.profile_email);
        mTotal = view.findViewById(R.id.profile_total_value);
        mComplete = view.findViewById(R.id.profile_complete_value);
        mCancel = view.findViewById(R.id.profile_cancel_value);

        mUser = FirebaseAuth
                .getInstance()
                .getCurrentUser();

        mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Orders");
    }

    private void downloadData()
    {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int total = 0;
                int completed = 0;
                int cancelled = 0;

                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    total = total + 1;
                    boolean isCompleted = Boolean.parseBoolean(String.valueOf(d.child("completed").getValue()));
                    boolean isCancelled = Boolean.parseBoolean(String.valueOf(d.child("cancelled").getValue()));

                    if (isCompleted)
                        completed = completed + 1;
                    if (isCancelled)
                        cancelled = cancelled + 1;
                }

                updateUI(completed, cancelled, total);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void updateUI(int completed, int cancelled, int total)
    {
        Picasso
                .get()
                .load(mUser.getPhotoUrl())
                .placeholder(R.drawable.ic_profile_80_80)
                .into(imageView);
        mFullName.setText(mUser.getDisplayName());
        mEmail.setText(mUser.getEmail());

        mTotal.setText(String.valueOf(total));
        mComplete.setText(String.valueOf(completed));
        mCancel.setText(String.valueOf(cancelled));
    }
}
