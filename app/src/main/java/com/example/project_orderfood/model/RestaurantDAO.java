package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.Restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    DBContext db;
    Connection connection;

    public Restaurant getInformation(){
        Restaurant restaurant = new Restaurant();
        db = new DBContext();
        connection = db.connection();
        String query = "select * from Restaurants";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                restaurant = new Restaurant(name, phone, address);
                System.out.println(restaurant);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurant;
    }


}
