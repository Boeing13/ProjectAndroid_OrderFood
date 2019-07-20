package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodDAO {

    public ArrayList<Food> getAllFoodsByID(int id){
        ArrayList<Food> foods=new ArrayList<>();
        String sql="Select * from Food where categoryID = ?";

        DBContext dbContext=new DBContext();
        Connection connection = dbContext.connection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
              int fid=  rs.getInt(1);
              String name=rs.getString(2);
              String des=rs.getString(3);
              String img=rs.getString(4);
              float price=rs.getFloat(5);
             int cateID=rs.getInt(6);
             foods.add(new Food(fid,name,des,img,price,cateID));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return foods;
    }

     public ArrayList<Food> Search(String temp,int id){
         ArrayList<Food> foods=new ArrayList<>();
        String sql=" select * from Food where name like '%"+temp+"%' and categoryID= ?";
         DBContext dbContext=new DBContext();
         Connection connection = dbContext.connection();

         try {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1,id);
             ResultSet rs = preparedStatement.executeQuery();
             while (rs.next()){
                 int fid=  rs.getInt(1);
                 String name=rs.getString(2);
                 String des=rs.getString(3);
                 String img=rs.getString(4);
                 float price=rs.getFloat(5);
                 int cateID=rs.getInt(6);
                 foods.add(new Food(fid,name,des,img,price,cateID));
             }
             rs.close();
             preparedStatement.close();
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }


         return foods;
     }

     public Food getFood(int foodID){
        String sql="select * from Food where foodID = ?";
         DBContext dbContext=new DBContext();
         Connection connection = dbContext.connection();

         try {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1,foodID);
             ResultSet rs = preparedStatement.executeQuery();
             while (rs.next()){
                 int fid=  rs.getInt(1);
                 String name=rs.getString(2);
                 String des=rs.getString(3);
                 String img=rs.getString(4);
                 float price=rs.getFloat(5);
                 int cateID=rs.getInt(6);
                return new Food(fid,name,des,img,price,cateID);
             }
             rs.close();
             preparedStatement.close();
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }

     public int deletFood(int foodID){
        String sql="delete from Food where foodID = ?";
         DBContext dbContext=new DBContext();
         Connection connection = dbContext.connection();
         PreparedStatement preparedStatement = null;
         try {
             preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1,foodID);
             int i = preparedStatement.executeUpdate();
             return i;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return -1;
     }

}
