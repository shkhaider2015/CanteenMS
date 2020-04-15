package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.canteenms.R;

public class Login extends AppCompatActivity  implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init()
    {
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mLogin = findViewById(R.id.login_log_in);
        mNavigation = findViewById(R.id.login_navigation);

        mLogin.setOnClickListener(this);
        mNavigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_log_in:
                // login logic
                startActivity(new Intent(Login.this, Home.class));
                finish();
                break;
            case R.id.login_navigation:
                // navigation Logic
                startActivity(new Intent(Login.this, Register.class));
                break;
        }

    }
    //commit one
    //commit two
}
