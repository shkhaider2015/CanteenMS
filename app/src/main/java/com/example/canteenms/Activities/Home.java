package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.canteenms.MainActivity;
import com.example.canteenms.R;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mTea, mBiryani, mBurger, mSamosa, mFrenchFries, mSalad, mOmlette, mColdDrink;

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

       mTea.setOnClickListener(this);
       mBiryani.setOnClickListener(this);
       mBurger.setOnClickListener(this);
       mSalad.setOnClickListener(this);
       mSamosa.setOnClickListener(this);
       mFrenchFries.setOnClickListener(this);
       mOmlette.setOnClickListener(this);
       mColdDrink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String dish = null;

        switch (v.getId())
        {
            case R.id.home_tea:
                //
                dish = "tea";
                break;
            case R.id.home_biryani:
                //
                dish = "biryani";
                break;
            case R.id.home_burger:
                //
                dish = "burger";
                break;
            case R.id.home_samosa:
                //
                dish = "samosa";
                break;
            case R.id.home_salad:
                //
                dish = "salad";
                break;
            case R.id.home_french_fries:
                //
                dish = "french_fries";
                break;
            case R.id.home_omelette:
                //
                dish = "omelatte";
                break;
            case R.id.home_cold_drink:
                //
                dish = "cold_drink";
                break;
        }

        Intent intent = new Intent(Home.this, MainActivity.class);
        intent.putExtra("dish", dish);
        startActivity(intent);

    }
}
