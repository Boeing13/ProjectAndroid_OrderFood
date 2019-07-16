package com.example.project_orderfood.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String phone;
    private String username;
    private String password;
    private boolean isStaff;

    public User(){

    }

    public User(String phone, String username, String password, boolean isStaff) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.isStaff=isStaff;
    }

    public User(int userId, String phone, String username, String password) {
        this.userId = userId;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    @Override
    public String toString() {
        return username;
    }
}
