package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.Models.Dish;
import com.example.canteenms.Models.Order;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Calculation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class DishDisplay extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "DishDisplay";

    private ImageView mDishImage;
    private TextView mDishName, mDishPrize;
    private EditText mQuantity, mLocation;
    private Button mOrder;
    private ProgressBar mProgress;

    private Dish dish;
    private boolean quantityAlert;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        mProgress = findViewById(R.id.display_progress);

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
        switch (v.getId())
        {
            case R.id.display_btn_order:
                //
                sendOrder();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

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

    private void sendOrder()
    {
        String quantity, location, dishName, dishPrize, clintName, clintUID, clintPhotouri;
        quantity = mQuantity.getText().toString();
        location = mLocation.getText().toString();
        dishName = mDishName.getText().toString();
        dishPrize = mDishPrize.getText().toString();
        clintName = mUser.getDisplayName();
        clintUID = mUser.getUid();
        clintPhotouri = Objects.requireNonNull(mUser.getPhotoUrl()).toString();


        if (quantity.isEmpty())
        {
            mQuantity.setError("Select At Least 1 Quantity");
            mQuantity.requestFocus();
            return;
        }
        if (location.isEmpty())
        {
            mLocation.setError("Select Your Location");
            mLocation.requestFocus();
            return;
        }
        if (quantityAlert)
        {
            mQuantity.setError("Quantity Is Must Be Less Than 10");
            mQuantity.requestFocus();
            return;
        }

        progress(1);
        uploadData(new Order(dishName, quantity, dishPrize, location, clintName, clintUID, clintPhotouri, false, false));

    }

    private void uploadData(Order order)
    {
        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Orders")
                .child(mUser.getUid())
                .child(Calendar.getInstance().getTimeInMillis() + "");

        mRef.setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Your Order has sent", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DishDisplay.this, Home.class));
                            DishDisplay.this.finish();
                        }
                        else
                        {
                            progress(0);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress(0);
                    }
                });


    }

    private void progress(int x)
    {
        switch (x)
        {
            case 1:
                //
                mProgress.setVisibility(View.VISIBLE);
                mOrder.setEnabled(false);
                break;
            case 0:
                //
                mProgress.setVisibility(View.INVISIBLE);
                mOrder.setEnabled(true);
                break;
        }
    }
}
