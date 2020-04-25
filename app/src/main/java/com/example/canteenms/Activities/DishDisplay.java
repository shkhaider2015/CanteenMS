package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenms.Models.Dish;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Calculation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DishDisplay extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "DishDisplay";

    private ImageView mDishImage;
    private TextView mDishName, mDishPrize;
    private EditText mQuantity, mLocation;
    private Button mOrder;
    private Dish dish;
    private boolean quantityAlert;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

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
        mQuantity.addTextChangedListener(this);

        quantityAlert = false;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    private void loadLocalData()
    {
        // Seriallaize object
        dish =(Dish) getIntent().getSerializableExtra("Object");

        assert dish != null;
        mDishImage.setImageResource(dish.getImage());
        mDishName.setText(dish.getDishName());
        String prize = "Rs : " + dish.getDishPrize();
        mDishPrize.setText(prize);
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String input = s.toString();
        if (!input.isEmpty())
        {
            Integer quan = Integer.parseInt(s.toString());
            Integer prize = dish.getDishPrize();
            int total = quan * prize;
            String display = "Rs : " + total;
            mDishPrize.setText(display);

            if (quan > 10)
                quantityAlert = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
