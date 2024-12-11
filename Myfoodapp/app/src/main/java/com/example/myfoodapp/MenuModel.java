package com.example.myfoodapp;

public class MenuModel {
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    int img,price,quantity;
    String name;
    boolean isExpanded;
    public MenuModel(int img,String name,int price){
        this.name=name;
        this.price=price;
        this.img=img;
        quantity=0;
        isExpanded=false;
    }
    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
    public int getPrice(){return price;}
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
