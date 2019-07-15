package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    DBContext db;
    Connection connection;
    String connectResult = "";
    boolean isSuccess = false;

    //INSERT
    public void insertUser(User user){
        db = new DBContext();
        connection = db.connection();
        String query = "insert into Users (phone, username, password, isStaff) " +
                "values (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getPhone());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isStaff());
            ps.executeUpdate();
            System.out.println("ADDED");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CHECK DUPLICATE PHONE NUMBER
    public boolean isExistedPhone(String phone){
        db = new DBContext();
        connection = db.connection();
        String query = "select count(userID) as count from [Users] where phone=?";
        int count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(count > 0) {
            System.out.println("EXISTED");
            return true;
        }
        else {
            System.out.println("NOT EXIST");
            return false;
        }
    }

    //GET USER BY PHONE
    public User getUser(String phone){
        db = new DBContext();
        User user = new User();
        connection = db.connection();
        String query = "select * from [Users] where phone=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String password = rs.getString("password");
                String name = rs.getString("username");
                boolean isStaff = false;
                user = new User(phone, name, password, isStaff);
//                System.out.println("PASSWORD: " + password);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public List<User> getData() {
        List<User> data = new ArrayList<>();

            db = new DBContext();
            connection = db.connection();
        try{
            if(connection == null){
                connectResult = "CHECK YOUR INTERNET ACCESS";
            }
            else{
                String query = "select * from Users";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while(rs.next()){
                    List<User> datanum = new ArrayList<>();
                    // datanum.put("userID", rs.getInt("userID"));
                    String phone =  rs.getString("phone");
                    String account  =  rs.getString("username");
                    String password = rs.getString("password");
                    String staff = rs.getString("isStaff");
                    boolean isStaff = false;
                    if(staff.equals("True")) isStaff = true;
                    data.add(new User(phone, account, password, isStaff));
                }
                connectResult = "CONNECT SUCCESSFULLY";
                isSuccess = true;
                connection.close();


            }
        }catch (Exception e){
            System.err.println("Lỗi đây: " + e.getMessage());
        }


        return data;
    }


}
