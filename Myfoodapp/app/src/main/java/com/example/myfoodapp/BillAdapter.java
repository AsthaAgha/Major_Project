package com.example.myfoodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// BillAdapter.java
// BillAdapter.java
// BillAdapter.java
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private ArrayList<CartItem> cartItems;

    public BillAdapter(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.itemNameTextView.setText(item.getItemName());
        holder.itemQuantityTextView.setText("Quantity: " + item.getItemQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView, itemQuantityTextView;

        BillViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
        }
    }
}
