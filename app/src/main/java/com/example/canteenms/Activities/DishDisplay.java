package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.Models.Dish;
import com.example.canteenms.Models.Food;
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
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

public class DishDisplay extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    private static final String TAG = "DishDisplay";

    private ImageView mDishImage;
    private TextView mDishName, mDishPrize, mAvailability;
    private EditText mQuantity, mLocation;
    private Button mOrder;
    private ProgressBar mProgress;

    private Food food;
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
        mAvailability = findViewById(R.id.display_availability);
        mDishPrize = findViewById(R.id.display_prize);
        mQuantity = findViewById(R.id.display_quantity_edit);
        mLocation = findViewById(R.id.display_edittext_location);
        mOrder = findViewById(R.id.display_btn_order);
        mProgress = findViewById(R.id.display_progress);

        mOrder.setOnClickListener(this);
        mQuantity.addTextChangedListener(this);

        mQuantity.setOnFocusChangeListener(this);
        mLocation.setOnFocusChangeListener(this);

        quantityAlert = false;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    private void loadLocalData()
    {
        // Seriallaize object
        food =(Food) getIntent().getSerializableExtra("Object");

        assert food != null;
        Picasso
                .get()
                .load(food.getFoodImageUri())
                .placeholder(R.drawable.ic_profile_80_80)
                .into(mDishImage);
        mDishName.setText(food.getFoodName());
        String prize = "Rs : " + food.getFoodPrice();
        mDishPrize.setText(prize);
        if (food.isAvailability())
            mAvailability.setVisibility(View.INVISIBLE);
        else
            mAvailability.setVisibility(View.VISIBLE);
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
            Integer prize = Integer.parseInt(food.getFoodPrice());
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
        String quantity, location, dishName, dishPrize, totalDishPrize, dishImageUri, clintName, clintUID, clintPhotouri, orderTime;
        quantity = mQuantity.getText().toString();
        location = mLocation.getText().toString();
        dishName = food.getFoodName();
        dishPrize = food.getFoodPrice();
        totalDishPrize = mDishPrize.getText().toString();
        dishImageUri = food.getFoodImageUri();
        clintName = mUser.getDisplayName();
        clintUID = mUser.getUid();
        clintPhotouri = Objects.requireNonNull(mUser.getPhotoUrl()).toString();
        orderTime = String.valueOf(Calendar.getInstance().getTimeInMillis());


        if (!food.isAvailability())
        {
            mAvailability.requestFocus();
            return;
        }
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

        uploadData(new Order(dishName, quantity, dishPrize, totalDishPrize, dishImageUri, location,
                clintName, clintUID, clintPhotouri, orderTime, false,
                false, false, false));

    }

    private void uploadData(final Order order)
    {
        DatabaseReference userRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(mUser.getUid())
                .child("Orders")
                .child(order.getOrderTime());
        final DatabaseReference genralRef = FirebaseDatabase
            .getInstance()
            .getReference()
            .child("Orders")
            .child(order.getOrderTime());


        userRef.setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            genralRef.setValue(order)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Log.d(TAG, "onComplete: Upload Both Side Successfully");
                                                Toast.makeText(getApplicationContext(), "Your Order has sent", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(DishDisplay.this, Home.class));
                                                DishDisplay.this.finish();
                                            }
                                            else
                                            {
                                                Log.w(TAG, "onComplete: ERROR : ", task.getException());
                                            }

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            progress(0);

                                        }
                                    });
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            hideKeyboard(v);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
