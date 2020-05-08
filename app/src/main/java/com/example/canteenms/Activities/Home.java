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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.Fragments.Orders;
import com.example.canteenms.Models.Dish;
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

public class Home extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, ValueEventListener {

    private static final String TAG = "Home";

    private LinearLayout mTea, mBiryani, mBurger, mSamosa, mFrenchFries, mSalad, mOmlette, mColdDrink;
    private ImageView mHamburger;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


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
       mTea = findViewById(R.id.home_tea);
       mBiryani = findViewById(R.id.home_biryani);
       mBurger = findViewById(R.id.home_burger);
       mSamosa = findViewById(R.id.home_samosa);
       mFrenchFries = findViewById(R.id.home_french_fries);
       mSalad = findViewById(R.id.home_salad);
       mOmlette = findViewById(R.id.home_omelette);
       mColdDrink = findViewById(R.id.home_cold_drink);
       mHamburger = findViewById(R.id.home_hamburger);
       drawerLayout = findViewById(R.id.home_drawer);
       mNavigationView = findViewById(R.id.navigation);

       mTea.setOnClickListener(this);
       mBiryani.setOnClickListener(this);
       mBurger.setOnClickListener(this);
       mSalad.setOnClickListener(this);
       mSamosa.setOnClickListener(this);
       mFrenchFries.setOnClickListener(this);
       mOmlette.setOnClickListener(this);
       mColdDrink.setOnClickListener(this);
       mHamburger.setOnClickListener(this);
       mNavigationView.setNavigationItemSelectedListener(this);

       mNavigationView.setItemIconTintList(null);
       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.home_tea:
                //
                nextActivity(new Dish("Tea", 25, R.drawable.ic_mug));
                break;
            case R.id.home_biryani:
                //
                nextActivity(new Dish("Biryani", 100, R.drawable.ic_biryani));
                break;
            case R.id.home_burger:
                //
                nextActivity(new Dish("Burger", 50, R.drawable.ic_burger));
                break;
            case R.id.home_samosa:
                //
                nextActivity(new Dish("Samosa", 12, R.drawable.ic_samosa));
                break;
            case R.id.home_salad:
                //
                nextActivity(new Dish("Salad", 20, R.drawable.ic_vegetable));
                break;
            case R.id.home_french_fries:
                //
                nextActivity(new Dish("French Fries", 30, R.drawable.ic_french_fries));
                break;
            case R.id.home_omelette:
                //
                nextActivity(new Dish("Omelette", 30, R.drawable.ic_omelette));
                break;
            case R.id.home_cold_drink:
                //
                nextActivity(new Dish("Cold Drink", 40, R.drawable.ic_cold_drink));
                break;
            case R.id.home_hamburger:
                //
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

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
                Toast.makeText(Home.this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:
                //
                Toast.makeText(Home.this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_logout:
                //
                startActivity(new Intent(Home.this, Login.class));
                mAuth.signOut();
                Home.this.finish();
                break;
            case R.id.menu_my_order:
                //
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

        mRef.addValueEventListener(this);

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
