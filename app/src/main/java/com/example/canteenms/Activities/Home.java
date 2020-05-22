package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.Adapters.GridAdapter;
import com.example.canteenms.Fragments.Chat;
import com.example.canteenms.Fragments.Orders;
import com.example.canteenms.Fragments.Profile;
import com.example.canteenms.Models.Dish;
import com.example.canteenms.Models.Food;
import com.example.canteenms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, ValueEventListener {

    private static final String TAG = "Home";

    private ImageView mHamburger;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    private GridView mGridView;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private List<Food> mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        headerUI();
        getNotify();


    }
    private void init()
    {

       mHamburger = findViewById(R.id.home_hamburger);
       drawerLayout = findViewById(R.id.home_drawer);
       mNavigationView = findViewById(R.id.navigation);
       mGridView = findViewById(R.id.home_grid_layout);

       mHamburger.setOnClickListener(this);
       mNavigationView.setNavigationItemSelectedListener(this);

       mNavigationView.setItemIconTintList(null);
       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Foods");
        mRef.addValueEventListener(this);

    }

    @Override
    public void onClick(View v)
    {


    }

    private void nextActivity(Dish dishobject)
    {
        Intent intent = new Intent(Home.this, DishDisplay.class);
        intent.putExtra("Object", dishobject);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_profile:
                //
                loadFragment(new Profile());
                drawerLayout.closeDrawer(GravityCompat.START, true);
                break;
            case R.id.menu_chat:
                //
                loadFragment(new Chat());
                drawerLayout.closeDrawer(GravityCompat.START, true);
                break;
            case R.id.menu_logout:
                //
                startActivity(new Intent(Home.this, Login.class));
                mAuth.signOut();
                Home.this.finish();
                break;
            case R.id.menu_my_order:
                //
                drawerLayout.closeDrawer(GravityCompat.START, true);
                loadFragment(new Orders());
                break;
        }
        return true;
    }

    private void headerUI()
    {
        View header = mNavigationView.getHeaderView(0);
        ImageView mHeaderImage = header.findViewById(R.id.header_profile_image);
        TextView mName = header.findViewById(R.id.header_text_name);
        TextView mEmail = header.findViewById(R.id.header_text_email);

        if (mUser != null)
        {
            Picasso
                    .get()
                    .load(mUser.getPhotoUrl())
                    .placeholder(R.drawable.ic_profile_80_80)
                    .into(mHeaderImage);
//            mHeaderImage.setImageURI(mUser.getPhotoUrl());
            mName.setText(mUser.getDisplayName());
            mEmail.setText(mUser.getEmail());
        }
    }

    private void getNotify()
    {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference().child("Users").child(mUser.getUid()).child("Orders");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d1 : dataSnapshot.getChildren())
                {

                    String dishName = String.valueOf(d1.child("dishName").getValue());
                    String orderId = String.valueOf(d1.getKey());
                    Boolean isAccepted = Boolean.valueOf(String.valueOf(d1.child("accepted").getValue()));
                    Boolean isNotify = Boolean.valueOf(String.valueOf(d1.child("notify").getValue()));

                    String msg = "Your " + dishName + " Order is accepted";
                    Log.d(TAG, "onDataChange: ACCEPTED : " + isAccepted);
                    Log.d(TAG, "onDataChange: dishName : " + dishName);
                    Log.d(TAG, "onDataChange: isNotifi");
                    if (!isNotify && isAccepted)
                    {
                        Log.d(TAG, "onDataChange: CONDITION RUN");
                        notificationGeneratedValue(orderId);
                        showNotification(dishName, msg);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel =
                    new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    {
        mDataList = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Food food = ds.getValue(Food.class);
            mDataList.add(food);
        }
        updateGrid();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public void showNotification(String title, String message)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "MyNotification")
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setAutoCancel(true)
                        .setContentText(message);
        NotificationManagerCompat managerCompat =
                NotificationManagerCompat.from(this);
        managerCompat.notify(99, builder.build());

    }

    private void notificationGeneratedValue(String orderId)
    {
        final String finalOrderId = orderId;
        DatabaseReference mRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Orders")
                .child(orderId)
                .child("notify");
        mRef.setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    DatabaseReference mref = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Orders")
                            .child(finalOrderId)
                            .child("notify");

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: Notification Set successfully");
                            mref.setValue(true)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Log.d(TAG, "onComplete: Both Side Update success");

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: One place update success but at another place failure");
                                        }
                                    });
                        }
                    }
                });
    }

    private void updateGrid()
    {
        GridAdapter adapter = new GridAdapter(mDataList, getApplicationContext());
        mGridView.setAdapter(adapter);

    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.home_fragment_container, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
