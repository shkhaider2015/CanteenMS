package com.example.canteenms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.R;


public class Login extends AppCompatActivity  implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mNavigation;
    private ProgressBar mProgressbar;

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
        mProgressbar = findViewById(R.id.login_progress_bar);

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
                login();
                break;
            case R.id.login_navigation:
                // navigation Logic
                startActivity(new Intent(Login.this, Register.class));
                break;
        }

    }

    private void login()
    {
        String email, password;
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (email.isEmpty())
        {
            mEmail.setError("Please Enter Your Email");
            mEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Email Is Not Valid");
            mEmail.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            mPassword.setError("Please Enter Your Password");
            mPassword.requestFocus();
            return;
        }
        if (password.length() <= 6)
        {
            mPassword.setError("Password Is Not Correct");
            mPassword.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(),
                "Login Correct",
                Toast.LENGTH_SHORT)
                .show();

        mProgressbar.setVisibility(View.VISIBLE);

        startActivity(new Intent(Login.this, Home.class));
        finish();
    }


}
