package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView mProfileImageView;
    private EditText mFullName, mEmail, mPassword, mConfirmPassword;
    private TextView mNavigationText;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init()
    {
        mProfileImageView = findViewById(R.id.register_image);
        mFullName = findViewById(R.id.register_name);
        mEmail = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.register_password);
        mConfirmPassword = findViewById(R.id.register_confirm_password);
        mNavigationText = findViewById(R.id.register_navigation);
        mSignUp = findViewById(R.id.register_sign_up);

        mProfileImageView.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mNavigationText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.register_image:
                // image logic
                Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_sign_up:
                // sign up logic here
                startActivity(new Intent(Register.this, Home.class));
                finish();
                break;
            case R.id.register_navigation:
                // Navigation Logic
                startActivity(new Intent(Register.this, Login.class));
                break;
        }
    }
}
