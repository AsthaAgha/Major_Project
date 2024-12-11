package com.example.myfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Handle click event for Cafe card
    public void onCafeClick(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    // Handle click event for Parking card
    public void onParkingClick(View view) {
        Intent intent = new Intent(this, Parking.class);
        startActivity(intent);
    }
}
