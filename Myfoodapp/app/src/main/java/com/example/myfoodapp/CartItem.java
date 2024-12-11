package com.example.myfoodapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class CartItem implements Parcelable {
    private int imageResource;
    private String itemName;
    private int itemQuantity,price;
    public void setItemQuantity(int quantity) {
        this.itemQuantity = quantity;
    }

    public CartItem(int imageResource, String itemName, int itemQuantity,int price) {
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price=price;
    }

    protected CartItem(Parcel in) {
        imageResource = in.readInt();
        itemName = in.readString();
        itemQuantity = in.readInt();
        price=in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public int getImageResource() {

        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getItemPrice(){
        return itemQuantity*price;
    }

    public void incrementQuantity() {
        itemQuantity++;
    }

    public void decrementQuantity() {
        if (itemQuantity > 0) {
            itemQuantity--;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(itemName);
        dest.writeInt(itemQuantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemName", itemName);
        result.put("itemPrice", price);
        result.put("quantity", itemQuantity);
        return result;
    }


}

