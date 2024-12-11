package com.example.myfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText mEmail, mPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail = findViewById(R.id.emailID);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();

        init();
    }

    private void init() {
        Button signupsubmit = findViewById(R.id.signupsubmit);
        signupsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // Validate email format
                if (!isValidEmail(email)) {
                    mEmail.setError("Invalid email format");
                    return;
                }

                // Validate password format
                if (!isValidPassword(password)) {
                    mPassword.setError("Invalid password format");
                    return;
                }

                // Continue with Firebase authentication
                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "User Created.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                } else {
                                    Toast.makeText(Signup.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Set transformation method to PasswordTransformationMethod for hiding the password
        mPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return email.endsWith("@mail.jiit.ac.in");
    }
    // Validate password format
    private boolean isValidPassword(String password) {
        // Password should be between 8 and 32 characters
        // At least 1 uppercase letter, 1 numeric digit, and 1 special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,32}$";
        return password.matches(passwordPattern);
    }
}