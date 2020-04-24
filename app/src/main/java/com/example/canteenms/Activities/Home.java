package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.canteenms.MainActivity;
import com.example.canteenms.Models.Dish;
import com.example.canteenms.R;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Home";

    private LinearLayout mTea, mBiryani, mBurger, mSamosa, mFrenchFries, mSalad, mOmlette, mColdDrink;
    private ImageView mHamburger;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
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
        }
        return true;
    }
}
