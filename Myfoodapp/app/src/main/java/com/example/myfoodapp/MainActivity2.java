package com.example.myfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if the user is already authenticated
        if (currentUser != null) {
            // User is already logged in, go to the main activity or home screen
            startActivity(new Intent(MainActivity2.this, Home.class));
            finish(); // finish the current activity to prevent the user from coming back here
        }

        init();
    }

    private void init() {
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLogin = new Intent(MainActivity2.this, LoginPage.class);
                startActivity(iLogin);
            }
        });

        Button signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSignup = new Intent(MainActivity2.this, Signup.class);
                startActivity(iSignup);
            }
        });
    }
}