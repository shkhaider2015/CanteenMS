package com.example.canteenms.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.canteenms.Models.Order;
import com.example.canteenms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KeepService extends Service implements ValueEventListener {

    private static final String TAG = "KeepService";
    FirebaseUser mUser;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "onBind: RUNS");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: RUNS");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: RUNS");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null)
        {
            init();
            final Intent intent1 = intent;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onTaskRemoved(intent1);
                }
            }, 6000);
        }
        else
        {
            Log.d(TAG, "onStartCommand: User is null in service");
        }
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        if (mUser != null)
        {

            Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
            restartServiceIntent.setPackage(getPackageName());
            startService(restartServiceIntent);
        }

        super.onTaskRemoved(rootIntent);
    }

    private void init()
    {
        DatabaseReference mNotificationRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Orders");
        mNotificationRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    {
        Log.d(TAG, "onDataChange: data " + dataSnapshot.getChildren());
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            Order order = ds.getValue(Order.class);
            assert order != null;
            if (!order.isNotify())
            {
                setNotifyToTrue(order);
            }
            else
                Log.d(TAG, "onDataChange: ORDER STATUS : " + order.getClintName() + "   :: " + order.getOrderTime());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void setNotifyToTrue(Order order)
    {
        genrateNotification(order);
        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Orders")
                .child(order.getOrderTime())
                .child("notify");
        mRef.setValue(true);
    }

    private void genrateNotification(Order order)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "MyNotification")
                        .setContentTitle(order.getDishName())
                        .setSmallIcon(R.mipmap.ic_launcher_cheff_round)
                        .setAutoCancel(true)
                        .setContentText("Your " + order.getDishName() + " order is accepted");
        NotificationManagerCompat managerCompat =
                NotificationManagerCompat.from(this);
        managerCompat.notify(99, builder.build());
    }
}
