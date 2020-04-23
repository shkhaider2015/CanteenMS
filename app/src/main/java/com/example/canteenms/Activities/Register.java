package com.example.canteenms.Activities;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.R;
import com.example.canteenms.Utilities.Image;
import com.example.canteenms.Utilities.Permission;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Register";
    private static final int GALLERY_REQUEST_CODE = 101;

    private CircleImageView mProfileImageView;
    private EditText mFullName, mEmail, mPassword, mConfirmPassword;
    private TextView mNavigationText;
    private Button mSignUp;

    private String profileURL;

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

        profileURL = null;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.register_image:
                // image logic
                chooseImageFromGallery();
                break;
            case R.id.register_sign_up:
                // sign up logic here
                registeration();
                break;
            case R.id.register_navigation:
                // Navigation Logic
                startActivity(new Intent(Register.this, Login.class));
                break;
        }
    }

    private void registeration()
    {
        String name, email, password, confirmPassword;
        name = mFullName.getText().toString();
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();

        if (profileURL == null || profileURL.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Profile Image is not selected", Toast.LENGTH_SHORT).show();
            mProfileImageView.requestFocus();
            return;
        }
        if (name.isEmpty())
        {
            mFullName.setError("Name is required");
            mFullName.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Email is not correct");
            mEmail.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }
        if (password.length() <= 6)
        {
            mPassword.setError("Password is too short");
            mPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword))
        {
            mConfirmPassword.setError("Password did not matches");
            mConfirmPassword.requestFocus();
            return;
        }

        Toast.makeText(getApplicationContext(), "Everything is right", Toast.LENGTH_SHORT).show();
    }

    private void chooseImageFromGallery()
    {
        if (Permission.checkReadWritePermission(getApplicationContext()))
        {
            Log.d(TAG, "chooseImageFromGallery: READ AND WRITE PERMISSION ACCEPTED");
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        }
        else
        {
            Log.d(TAG, "chooseImageFromGallery: READ AND WRITE PERMISSION DENIED");
            Permission.requestReadAndWritePermission(Register.this, GALLERY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (grantResults.length > 0)
            {
                if (Permission.checkReadWritePermission(this))
                {
                    Toast.makeText(this,
                            "Gallery Permission Acctepted",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Gallery Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                    if (Permission.useRunTimePermission())
                    {
                        Log.d(TAG, "onRequestPermissionsResult: NEED RUNTIME PERMISSION");
                        showMessageOkCancel("You Need To Allow Storage Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Permission.requestReadAndWritePermission(Register.this, GALLERY_REQUEST_CODE);
                            }
                        });
                    }
                    else
                    {
                        return;
                    }
                }
            }
            else
            {
                Log.d(TAG, "onRequestPermissionsResult: GRANT RESULT LENGTH IS NOT ZERO");
                return;
            }
        }
        else
        {
            Log.d(TAG, "onRequestPermissionsResult: REQUEST CODE DID NOT MATCHES");
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED)
            return;

        if (requestCode == GALLERY_REQUEST_CODE)
        {
            if (data != null && data.getData() != null)
            {
                Uri uri = data.getData();
                mProfileImageView.setImageURI(uri);
                profileURL = Image.getPath(getApplicationContext(), uri);
            }
            else
            {
                return;
            }
        }
        else
        {
            return;
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(Register.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }
}
