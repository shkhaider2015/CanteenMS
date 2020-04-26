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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenms.Models.RegisterModel;
import com.example.canteenms.R;
import com.example.canteenms.Utilities.Image;
import com.example.canteenms.Utilities.Permission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Register";
    private static final int GALLERY_REQUEST_CODE = 101;

    private CircleImageView mProfileImageView;
    private EditText mFullName, mEmail, mPassword, mConfirmPassword;
    private TextView mNavigationText;
    private Button mSignUp;
    private ProgressBar mProgress;

    private byte[] img;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;

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
        mProgress = findViewById(R.id.register_progress);

        mProfileImageView.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mNavigationText.setOnClickListener(this);


        img = null;


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
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
        final String name, email, password, confirmPassword;
        name = mFullName.getText().toString();
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();

        if (img == null)
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

        progressBar(1);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            uploadImage(img);
                        }
                        else
                        {
                            Log.w(TAG, "onComplete: ", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Authentication Failed",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            progressBar(0);
                            return;
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Check You Internet",
                                Toast.LENGTH_SHORT)
                                .show();
                        progressBar(0);
                        return;

                    }
                });


    }

    private void getOnlineImage()
    {
        StorageReference imgRef = FirebaseStorage
                .getInstance()
                .getReference()
                .child("Profile")
                .child(mAuth.getCurrentUser().getUid());

        imgRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        updateUser(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "onFailure: ", e);
                        progressBar(0);
                    }
                });
    }
    private void updateUser(final Uri uri)
    {
        String name = mFullName.getText().toString();

        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        FirebaseUser user = mAuth.getCurrentUser();
        user.updateProfile(changeRequest);
        mAuth.updateCurrentUser(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Now You Can Login",
                                Toast.LENGTH_SHORT)
                                .show();

                        uploadInfotoDatabase(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "onFailure: ", e);
                        progressBar(0);
                    }
                });
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
                int size = 0;
                byte[] img = null;
                Uri uri = data.getData();

                try {
                    InputStream istream = getContentResolver().openInputStream(uri);
                    assert istream != null;
                    img = Image.getBytes(istream);
                    size = img.length;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onActivityResult: Image size : " + size);
                if(size > 500000)
                {
                    Toast.makeText(getApplicationContext(),
                            "Profile Picture Must Be 5KB or less",
                            Toast.LENGTH_SHORT)
                            .show();
                    img = null;
                    return;
                }
                mProfileImageView.setImageURI(uri);
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

    private void uploadImage(byte[] data)
    {
        if (data == null)
            return;

        StorageReference mref = mStorageRef.child("Profile")
                .child(mAuth.getCurrentUser().getUid())
                .child(Calendar.getInstance().getTimeInMillis() + ".jpg");

        mref.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getOnlineImage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "onFailure: ", e);
                        progressBar(0);
                    }
                });
    }

    private void uploadInfotoDatabase(Uri uri)
    {
        String name, email, image;
        name = mFullName.getText().toString();
        email = mEmail.getText().toString();
        image = uri.toString();

        RegisterModel registerModel = new RegisterModel(image, name, email);

        DatabaseReference mref = FirebaseDatabase
                .getInstance()
                .getReference();
        mref.child(mAuth.getCurrentUser().getUid())
                .child("Profile")
                .setValue(registerModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "onComplete: Information upload to database successfully");
                            progressBar(0);
                            startActivity(new Intent(Register.this, Login.class));
                        }
                        else
                            Log.d(TAG, "onComplete: Information uploading failure");
                        progressBar(0);

                    }
                });
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

    private void progressBar(int x)
    {
        switch (x)
        {
            case 0:
                //
                mProgress.setVisibility(View.INVISIBLE);
                mSignUp.setEnabled(true);
                break;
            case 1:
                //
                mProgress.setVisibility(View.VISIBLE);
                mSignUp.setEnabled(false);
                break;
        }
    }
}
