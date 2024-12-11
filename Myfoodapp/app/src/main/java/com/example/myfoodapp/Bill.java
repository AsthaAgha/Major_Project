package com.example.myfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
public class Bill extends AppCompatActivity {

    private RecyclerView recyclerViewBill;
    private TextView textViewTotal;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // Initialize views
        recyclerViewBill = findViewById(R.id.recyclerViewBill);
        textViewTotal = findViewById(R.id.textViewTotal);

        // Get bill data from intent
        Intent intent = getIntent();
        ArrayList<CartItem> cartItems = intent.getParcelableArrayListExtra("cartItems");
        double totalAmount = intent.getDoubleExtra("totalAmount", 0.0); // Default value of 0.0 if not found

        Log.d("TotalAmount", "Total Amount received: " + totalAmount); // Log total amount received

        // Set up RecyclerView
        BillAdapter billAdapter = new BillAdapter(cartItems);
        recyclerViewBill.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBill.setAdapter(billAdapter);

        // Display total price
        textViewTotal.setText("Total: $" + totalAmount);
    }


    private double calculateTotalAmount(ArrayList<CartItem> cartItems) {
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getItemPrice() * item.getItemQuantity();
        }
        return total;
    }
}