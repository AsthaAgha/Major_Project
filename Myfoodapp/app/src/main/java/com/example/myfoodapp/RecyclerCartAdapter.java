
package com.example.myfoodapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerCartAdapter extends RecyclerView.Adapter<RecyclerCartAdapter.CartViewHolder> {

    private final Context mContext;
    private  final RecyclerMenuAdapter menuAdapter;
    private final OnItemDeleteListener itemDeleteListener;

    public RecyclerCartAdapter(Context context, RecyclerMenuAdapter menuAdapter, OnItemDeleteListener listener) {
        mContext = context;
        this.menuAdapter = menuAdapter;
        itemDeleteListener = listener;
    }

    public interface OnItemDeleteListener {
        void onItemDeleted();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemFoodImageView;
        Button deleteButton;
        TextView itemQuantityTextView, itemPriceTextView, itemNameTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemFoodImageView = itemView.findViewById(R.id.cimgFood);
            itemNameTextView = itemView.findViewById(R.id.cName);
            itemQuantityTextView = itemView.findViewById(R.id.cquantityTextView);
            itemPriceTextView = itemView.findViewById(R.id.cPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem currentItem = SharedData.cartItems.get(position);
        holder.itemFoodImageView.setImageResource(currentItem.getImageResource());
        holder.itemNameTextView.setText(currentItem.getItemName());
        holder.itemQuantityTextView.setText(String.valueOf(currentItem.getItemQuantity()));
        holder.itemPriceTextView.setText(String.valueOf(currentItem.getItemPrice()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.cartItems.remove(currentItem);
                notifyItemRemoved(position);
                menuAdapter.updateMenuItemQuantity(currentItem.getItemName());
                if (itemDeleteListener != null) {
                    itemDeleteListener.onItemDeleted();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return SharedData.cartItems.size();
    }
}