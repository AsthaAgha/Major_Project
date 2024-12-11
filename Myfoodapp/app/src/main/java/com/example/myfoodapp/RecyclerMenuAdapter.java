package com.example.myfoodapp;
// RecyclerMenuAdapter.java
import static com.example.myfoodapp.Cart.updateTotalOrderAmount;

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

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MenuModel> arrmenu;
    private RecyclerCartAdapter cartAdapter;

    private void updateQuantity(String itemName, int newQuantity) {
        for (int i = 0; i < arrmenu.size(); i++) {
            MenuModel item = arrmenu.get(i);
            if (item.getName().equals(itemName)) {
                item.setQuantity(newQuantity);
                notifyItemChanged(i); // Notify adapter about the change in item's quantity
                break;
            }
        }
    }
    private void updateCartItemQuantity(String itemName, int newQuantity) {
        for (int i = 0; i < SharedData.cartItems.size(); i++) {
            CartItem cartItem = SharedData.cartItems.get(i);
            if (cartItem.getItemName().equals(itemName)) {
                cartItem.setItemQuantity(newQuantity);
                if (newQuantity == 0) {
                    SharedData.cartItems.remove(i); // Remove the cart item if its quantity becomes 0
                }
                break;
            }
        }
    }
    public RecyclerMenuAdapter(Context context, ArrayList<MenuModel> arrmenu, RecyclerCartAdapter cartAdapter) {
        this.context = context;
        this.arrmenu = arrmenu;
        this.cartAdapter = cartAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuModel currentItem = arrmenu.get(position);

        holder.imgFood.setImageResource(currentItem.img);
        holder.iName.setText(currentItem.name);
        holder.iPrice.setText(String.valueOf(currentItem.price));
        holder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));

        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem.setQuantity(currentItem.getQuantity() + 1);
                holder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));

                boolean itemAlreadyInCart = false;
                for (CartItem cartItem : SharedData.cartItems) {
                    if (cartItem.getItemName().equals(currentItem.getName())) {

                        cartItem.setItemQuantity(cartItem.getItemQuantity() + 1);
                        itemAlreadyInCart = true;
                        break;
                    }
                }

                if (!itemAlreadyInCart) {
                    SharedData.cartItems.add(new CartItem(currentItem.getImg(),currentItem.getName(), currentItem.getQuantity(),currentItem.getPrice()));
                }


                if (cartAdapter != null) {
                    cartAdapter.notifyDataSetChanged();
                }
            }
        });

        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedQuantity = currentItem.getQuantity() - 1;
                if (updatedQuantity >= 0) {
                    currentItem.setQuantity(updatedQuantity);
                    holder.quantityTextView.setText(String.valueOf(updatedQuantity));

                    SharedData.cartItems.remove(new CartItem(currentItem.getImg(), currentItem.getName(), currentItem.getQuantity(), currentItem.getPrice() * currentItem.getQuantity()));
                    updateQuantity(currentItem.getName(), updatedQuantity);
                    updateCartItemQuantity(currentItem.getName(), updatedQuantity); // Update cart item quantity

                    if (cartAdapter != null) {
                        cartAdapter.notifyDataSetChanged();
                    }
                    updateTotalOrderAmount(); // Recalculate total amount
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrmenu.size();
    }

    public void updateMenuItemQuantity(String itemName) {
        for (int i = 0; i < arrmenu.size(); i++) {
            MenuModel item = arrmenu.get(i);
            if (item.getName().equals(itemName)) {
                item.setQuantity(0); // Set the quantity to 0
                notifyItemChanged(i); // Notify adapter about the change
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView iName, iPrice, quantityTextView;
        ImageView imgFood;
        Button increaseButton, decreaseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iName = itemView.findViewById(R.id.iName);
            iPrice = itemView.findViewById(R.id.iPrice);
            imgFood = itemView.findViewById(R.id.imgFood);
            quantityTextView= itemView.findViewById(R.id.quantityTextView);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
        }
    }
}