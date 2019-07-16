package com.example.project_orderfood.entity;

import android.content.SharedPreferences;

public class OrderDetail  {
    int orderID;
    int foodID;
    int quantity;
    float price;

    public OrderDetail(int foodID, int quantity, float price) {
        this.foodID = foodID;
        this.quantity = quantity;
        this.price = price;
    }
}
