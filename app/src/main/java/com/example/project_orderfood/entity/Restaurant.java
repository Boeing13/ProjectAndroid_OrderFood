package com.example.project_orderfood.entity;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int restaurantID;
    private String name;
    private String phone;
    private String address;

    public Restaurant() {
    }

    public Restaurant(int restaurantID, String name, String phone, String address) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Restaurant(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID=" + restaurantID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
