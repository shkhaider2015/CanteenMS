package com.example.canteenms.Activities;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity  implements View.OnClickListener {

    private static final String TAG = "Login";

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mNavigation;
    private ProgressBar mProgressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(Login.this, Home.class));
            this.finish();
        }


    }

    private void init() {
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mLogin = findViewById(R.id.login_log_in);
        mNavigation = findViewById(R.id.login_navigation);
        mProgressbar = findViewById(R.id.login_progress_bar);

        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(this);
        mNavigation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_log_in:
                // login logic
//                startActivity(new Intent(Login.this, Home.class));
//                finish();
                login();
                break;
            case R.id.login_navigation:
                // navigation Logic
                startActivity(new Intent(Login.this, Register.class));
                this.finish();
                break;
        }

    }

    private void login() {
        String email, password;
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (email.isEmpty()) {
            mEmail.setError("Please Enter Your Email");
            mEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Email Is Not Valid");
            mEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            mPassword.setError("Please Enter Your Password");
            mPassword.requestFocus();
            return;
        }
        if (password.length() <= 6) {
            mPassword.setError("Password Is Not Correct");
            mPassword.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(),
                "Login Correct",
                Toast.LENGTH_SHORT)
                .show();

        progress(1);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Login.this, Home.class));
                            Login.this.finish();
                        } else {
                            Log.w(TAG, "onComplete: ", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Check Your Email",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            progress(0);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Check Your Network",
                                Toast.LENGTH_SHORT)
                                .show();
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
                mProgressbar.setVisibility(View.VISIBLE);
                mLogin.setEnabled(false);
                break;
            case 0:
                //
                mProgressbar.setVisibility(View.INVISIBLE);
                mLogin.setEnabled(true);
                break;
        }
    }


}
