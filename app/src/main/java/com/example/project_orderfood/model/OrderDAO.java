package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.Food;
import com.example.project_orderfood.entity.Order;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAO {
     String status="shipped";
    public int insertOrder(User user, String address,float total){
        String sql="INSERT INTO Orders\n" +
                "VALUES (?,?,?,?,?);";
        DBContext dbContext=new DBContext();
        Connection connection = dbContext.connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getPhone());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3,address);
            preparedStatement.setFloat(4,total);
            preparedStatement.setString(5,status);
            int i = preparedStatement.executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getNewestOrder(){
        String sql=" select Top(1) orderID from Orders order by orderID desc";
        DBContext dbContext=new DBContext();
        Connection connection = dbContext.connection();
        int oid=-1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                oid=  rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return oid;
    }

    public int insertOrderDetail(int oid, ArrayList<OrderDetail> orderDetails){
        String sql="INSERT INTO OrderDetails\n" +
                "VALUES (?,?,?,?);";
        DBContext dbContext=new DBContext();
        Connection connection = dbContext.connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<orderDetails.size();i++){
                preparedStatement.setInt(1,oid);
                preparedStatement.setInt(2,orderDetails.get(i).foodID);
                preparedStatement.setInt(3,orderDetails.get(i).quantity);
                preparedStatement.setFloat(4,orderDetails.get(i).price);
                 preparedStatement.executeUpdate();
            }

            preparedStatement.close();;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;

        }
        return 1;
    }

    public ArrayList<Order> getAllOrder(String phoneUser) {
        String sql = "select * from Orders where phone = '"+phoneUser+"'";
        DBContext dbContext = new DBContext();
        Connection connection = dbContext.connection();
        ArrayList<Order> orders=new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt(1);
                String phone = rs.getString(2);
                String uname = rs.getString(3);
                String address = rs.getString(4);
                float total = rs.getFloat(5);
                String status = rs.getString(6);
                orders.add(new Order(oid,phone,uname,address,total,status));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return orders;
    }

    public ArrayList<OrderDetail> getOrderDetai(int orderID){
        String sql="select * from OrderDetails where orderID= ?";
        DBContext dbContext = new DBContext();
        Connection connection = dbContext.connection();
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
               int foodID=rs.getInt(2);
               int quantity=rs.getInt(3);
               float price=rs.getFloat(4);
               orderDetails.add(new OrderDetail(foodID,quantity,price));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return orderDetails;
    }

}
