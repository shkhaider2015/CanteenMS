package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.canteenms.MainActivity;
import com.example.canteenms.Models.Dish;
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
        int prize = 0;
        int image = 0;

        switch (v.getId())
        {
            case R.id.home_tea:
                //
                dish = "Tea";
                prize = 25;
                image = R.drawable.ic_mug;
                break;
            case R.id.home_biryani:
                //
                dish = "Biryani";
                prize = 100;
                image = R.drawable.ic_biryani;
                break;
            case R.id.home_burger:
                //
                dish = "Burger";
                prize = 50;
                image = R.drawable.ic_burger;
                break;
            case R.id.home_samosa:
                //
                dish = "Samosa";
                prize = 12;
                image = R.drawable.ic_samosa;
                break;
            case R.id.home_salad:
                //
                dish = "Salad";
                prize = 20;
                image = R.drawable.ic_vegetable;
                break;
            case R.id.home_french_fries:
                //
                dish = "French Fries";
                prize = 30;
                image = R.drawable.ic_french_fries;
                break;
            case R.id.home_omelette:
                //
                dish = "Omelette";
                prize = 30;
                image = R.drawable.ic_omelette;
                break;
            case R.id.home_cold_drink:
                //
                dish = "Cold Drink";
                prize = 40;
                image = R.drawable.ic_cold_drink;
                break;
        }

        Dish dishobject = new Dish(dish, prize, image);

        Intent intent = new Intent(Home.this, DishDisplay.class);
        intent.putExtra("Object", dishobject);
        startActivity(intent);

    }
}
