package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    Connection connection;
    String connectResult = "";
    boolean isSuccess = false;

    public List<User> getData() {
        List<User> data = new ArrayList<>();

            DBContext db = new DBContext();
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
                    data.add(new User(phone, account, password));
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

    //ĐÂY LÀ PHẦN CODE SẮN CHO AI THÍCH DÙNG THÌ DÙNG, KHÔNG THÌ CÓ THỂ XÓA ĐI NHA ^^

 //    private JDBCController jdbcController = new JDBCController();
//    private Connection connection;
//
//    public UserDAO() {
//        connection = jdbcController.ConnnectionData(); // Tạo kết nối tới database
//    }
//
//    public String getPasswordByPhone(String phone)throws SQLException{
//        String password = "";
//        try {
//            Statement statement = connection.createStatement();
//
//            String query = "select u.password from Users u where u.phone = '"+ phone +"'";
//            ResultSet rs = statement.executeQuery(query);
//            while(rs.next()){
//                password = rs.getString("password");
//                connection.close();
//                System.out.println("password: " + password);
//
//            }
//            connection.close();
//            return null;
//        }catch (Exception e){
//            System.out.println("CONNECTION " + connection);
//        }
//        return password;
//    }
//
//    public List<User> getuserlist() throws SQLException {
//        List<User> list = new ArrayList<>();
//        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//        String sql = "select * from User";
//        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
//        ResultSet rs = statement.executeQuery(sql);
//        while (rs.next()) {
//          //  list.add(new User(rs.getString("Name"), rs.getInt("ID")));// Đọc dữ liệu từ ResultSet
//        }
//        connection.close();// Đóng kết nối
//        return list;
//    }
//
//    public boolean Insert(User objUser) throws SQLException {
//        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//        String sql = "insert in to User(Name) values(" + objUser.getPhone() + ")";
//        if (statement.executeUpdate(sql) > 0) { // Dùng lệnh executeUpdate cho các lệnh CRUD
//            connection.close();
//            return true;
//        } else {
//            connection.close();
//            return false;
//        }
//    }
//
//    public boolean Update(User objUser) throws SQLException {
//        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//        String sql = "Update User set Name = " + objUser.getUsername() + " where ID = " + objUser.getUserId();
//        if (statement.executeUpdate(sql) > 0) {
//            connection.close();
//            return true;
//        } else
//            connection.close();
//        return false;
//    }
//
//    public boolean Delete(User objUser) throws SQLException {
//        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
//        String sql = "delete from User where ID = " + objUser.getUserId();
//        if (statement.executeUpdate(sql) > 0){
//            connection.close();
//            return true;
//        }
//
//        else
//            connection.close();
//        return false;
//    }
}
