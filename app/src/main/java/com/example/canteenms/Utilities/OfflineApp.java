package com.example.canteenms.Utilities;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class OfflineApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
