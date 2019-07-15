package com.example.project_orderfood.entity;

public class Food {
   public int foodID;
    public String name;
    public String des;
    public String img;
    public float price;
    public int cateID;

    public Food(int foodID, String name, String des, String img, float price, int cateID) {
        this.foodID = foodID;
        this.name = name;
        this.des = des;
        this.img = img;
        this.price = price;
        this.cateID = cateID;
    }
}
