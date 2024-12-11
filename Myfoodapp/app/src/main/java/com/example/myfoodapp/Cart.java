package com.example.myfoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity implements PaymentResultListener, RecyclerCartAdapter.OnItemDeleteListener {

    private RecyclerView recyclerView;
    private RecyclerCartAdapter cartAdapter;
    private RecyclerMenuAdapter adapter;
    private static TextView totalAmountTextView;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button checkoutButton = findViewById(R.id.checkoutButton);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        totalAmountTextView = findViewById(R.id.totalPrice);

        cartAdapter = new RecyclerCartAdapter(this, adapter, this); // Pass the adapter instance
        recyclerView.setAdapter(cartAdapter);

        Checkout.preload(Cart.this);
        updateTotalOrderAmount();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment((int) calculateTotalOrderAmount()); // You might want to pass the actual amount here
            }
        });
    }

    public static double calculateTotalOrderAmount() {
        double totalAmount = 0.0;

        for (CartItem cartItem : SharedData.cartItems) {
            totalAmount += cartItem.getItemPrice();
        }
        return totalAmount;
    }

    public static void updateTotalOrderAmount() {
        double totalAmount = calculateTotalOrderAmount();
        totalAmountTextView.setText(String.valueOf(totalAmount));
    }

    public void startPayment(int amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_NGAZfmxol2X3wT");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "JAYPEE INSTITUTE OF INFORMATION TECHNOLOGY");
            jsonObject.put("description", "");
            jsonObject.put("theme.color", "#00BCD4");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", amount * 100);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            jsonObject.put("retry", retryObj);
            checkout.open(Cart.this, jsonObject);

        } catch (Exception e) {
            Toast.makeText(Cart.this, "something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // Initialize the Firebase database reference
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");

        // Create a map to hold the cart items data
        Map<String, Object> orderData = new HashMap<>();
        for (int i = 0; i < SharedData.cartItems.size(); i++) {
            CartItem item = SharedData.cartItems.get(i);
            orderData.put("item_" + i, item.toMap());
        }

        // Push the data to Firebase
        reference.push().setValue(orderData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Clear the cart after successful payment
                        totalAmountTextView.setText("0.0");


                        Toast.makeText(Cart.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Cart.this, "Failed to place order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
        generateAndPrintBill();
    }
    private void generateAndPrintBill() {
        // Calculate total amount
        double totalAmount = calculateTotalOrderAmount();

        // Get cart items
        ArrayList<CartItem> cartItems = new ArrayList<>(SharedData.cartItems);

        // Start Bill activity and pass data
        Intent intent = new Intent(this, Bill.class);
        intent.putParcelableArrayListExtra("cartItems", cartItems);
        intent.putExtra("totalAmount", totalAmount);
        Log.d("TotalAmount", "Total Amount: " + totalAmount);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        // Handle payment error
    }

    @Override
    public void onItemDeleted() {
        updateTotalOrderAmount();// Update the total amount when an item is deleted

    }
}
