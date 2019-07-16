package com.example.project_orderfood.model;

import com.example.project_orderfood.connectDB.DBContext;
import com.example.project_orderfood.entity.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private DBContext context = new DBContext();
    private Connection conn;

    public CategoryDAO() {
        conn = context.connection(); // Tạo kết nối tới database
    }

    public List<Category> getData()throws SQLException {
        int id;
        String name = "";
        String image = "" ;
        List<Category> categoryList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "select * from Categories";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                id = rs.getInt("categoryID");
                name = rs.getString("name");
                image = rs.getString("image");
                categoryList.add(new Category(id, name, image));
            }
            conn.close();

        }catch (Exception e){
            System.out.println("CONNECTION " + conn);
        }
        return categoryList;
    }

    public void getByID(int cid){

    }
}
