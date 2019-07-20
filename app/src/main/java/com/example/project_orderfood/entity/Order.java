package com.example.project_orderfood.entity;

public class Order {
   public int orderID;
   public String phone;
    public String uname;
    public String address;
    public float total;
    public String status;

    public Order(int orderID, String phone, String uname, String address, float total, String status) {
        this.orderID = orderID;
        this.phone = phone;
        this.uname = uname;
        this.address = address;
        this.total = total;
        this.status = status;
    }
}
