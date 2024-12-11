package com.example.myfoodapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;

public class Home extends AppCompatActivity implements RecyclerCartAdapter.OnItemDeleteListener {

    ArrayList<MenuModel> arrmenu = new ArrayList<>();
    ArrayList<MenuModel> arrmenuDrinks = new ArrayList<>();
    RecyclerMenuAdapter adapter;
    RecyclerCartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.CMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrmenu.add(new MenuModel(R.drawable.samosa, "Samosa", 10));
        arrmenu.add(new MenuModel(R.drawable.coldrink, "Coldrink", 10));
        arrmenu.add(new MenuModel(R.drawable.paneerpatty, "Paneer Patty", 15));
        arrmenu.add(new MenuModel(R.drawable.aloopatty, "AlooPatty", 10));
        arrmenu.add(new MenuModel(R.drawable.oreo, "Oreo", 10));
        arrmenu.add(new MenuModel(R.drawable.noodles, "Noodle", 10));
        arrmenu.add(new MenuModel(R.drawable.pavbhaji, "PavBhaji", 10));
        arrmenu.add(new MenuModel(R.drawable.coffee, "Coffee", 10));
        arrmenu.add(new MenuModel(R.drawable.coldcoffee, "ColdCoffee", 10));
        arrmenu.add(new MenuModel(R.drawable.tea, "Tea", 10));

        arrmenuDrinks.add(new MenuModel(R.drawable.coffee, "Coffee", 10));
        arrmenuDrinks.add(new MenuModel(R.drawable.coldrink, "Coldrink", 10));

        cartAdapter = new RecyclerCartAdapter(this, adapter, this); // Pass the adapter instance
        adapter = new RecyclerMenuAdapter(this, arrmenu, cartAdapter);
        recyclerView.setAdapter(adapter);

        init();
    }

    private void init() {
        Button cart = findViewById(R.id.cartButton);
        Button myOrders = findViewById(R.id.myOrdersButton);
        Button myAccount = findViewById(R.id.myAccountButton);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCart = new Intent(Home.this, Cart.class);
                startActivity(iCart);
            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imyOrders = new Intent(Home.this, MyOrders.class);
                startActivity(imyOrders);
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imyAccount = new Intent(Home.this, MyAccount.class);
                startActivity(imyAccount);
            }
        });
    }

    // Listener method for item deletion
    @Override
    public void onItemDeleted() {
        adapter.notifyDataSetChanged(); // Notify RecyclerMenuAdapter of data change
    }
}


