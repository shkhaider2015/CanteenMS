package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenms.Models.Dish;
import com.example.canteenms.R;

public class DishDisplay extends AppCompatActivity implements View.OnClickListener {

    private ImageView mDishImage;
    private TextView mDishName, mDishPrize;
    private EditText mQuantity, mLocation;
    private Button mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_display);
        init();
        loadLocalData();

    }

    private void init()
    {
        mDishImage = findViewById(R.id.display_image);
        mDishName = findViewById(R.id.display_dish);
        mDishPrize = findViewById(R.id.display_prize);
        mQuantity = findViewById(R.id.display_quantity_edit);
        mLocation = findViewById(R.id.display_edittext_location);
        mOrder = findViewById(R.id.display_btn_order);

        mOrder.setOnClickListener(this);

    }

    private void loadLocalData()
    {
        // Seriallaize object
        Dish dish =(Dish) getIntent().getSerializableExtra("object");

        assert dish != null;
        mDishImage.setImageResource(dish.getImage());
        mDishName.setText(dish.getDishName());
        mDishPrize.setText(R.string.rateconcatenate + dish.getDishPrize());
    }

    @Override
    public void onClick(View v)
    {

    }
}
