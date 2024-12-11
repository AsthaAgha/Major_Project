package com.example.myfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText mEmail, mPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        init();
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();

        // Set transformation method to PasswordTransformationMethod for hiding the password
        mPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void init() {
        Button loginsubmit = findViewById(R.id.loginsubmit);
        loginsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pemail = mEmail.getText().toString().trim();
                String ppassword = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(pemail)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(ppassword)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                if (ppassword.length() < 8) {
                    mPassword.setError("Password must be >= 8 Characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(pemail, ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            Toast.makeText(LoginPage.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}