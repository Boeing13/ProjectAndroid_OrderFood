package com.example.project_orderfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.entity.Food;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.model.FoodDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class User_ViewDetailFood extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView img;
    TextView txtFoodTittle,txtFoodName,txtPrice,txtNumber,txtDes,txtFullName;
    ImageButton btnImage;
    int foodID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__view_detail_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        img=findViewById(R.id.imgFood);
        txtFoodTittle=findViewById(R.id.txtFoodNameTittle);
        txtFoodName=findViewById(R.id.txtFoodName);
        txtPrice=findViewById(R.id.txtPrice);
        txtNumber=findViewById(R.id.txtChooseNumber);
        txtDes=findViewById(R.id.txtDescribe);
        btnImage=findViewById(R.id.btnBuy);

        foodID= getIntent().getIntExtra("foodID",-1);
        final Food food = new FoodDAO().getFood(foodID);
        String url=food.img;
        Picasso.with(this).load(url).into(img);
        txtFoodTittle.setText(food.name);
        txtFoodName.setText(food.name);
        txtDes.setText(food.des);
        txtPrice.setText(Float.toString(food.price));
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<OrderDetail> odList = getData();
                String textNumber= txtNumber.getText().toString();
                if(textNumber.equals("")){
                    Toast.makeText(User_ViewDetailFood.this,"Please select number of foods > 0",Toast.LENGTH_LONG).show();
                }else{
                    int number=Integer.parseInt(textNumber);
                    int check=checkExitedAndReturnPosition(odList,foodID);
                    if(check==-1){
                        odList.add(new OrderDetail(foodID,number,food.price));
                    }else {
                        odList.get(check).quantity+=number;
                    }
                    loadData(odList);
                    Toast.makeText(User_ViewDetailFood.this,"Done !",Toast.LENGTH_LONG).show();
                }
            }
        });

        //Set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getUsername());
    }

    public int  checkExitedAndReturnPosition(ArrayList<OrderDetail> orderDetails,int foodID){
        for(int i=0;i<orderDetails.size();i++){
            if(orderDetails.get(i).foodID==foodID){
                return i;
            }
        }
        return -1;
    }

    public  ArrayList<OrderDetail> getData(){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("MyPre",MODE_PRIVATE);
        Gson gson=new Gson();
        String json= sharedPreferences.getString("orderDetails",null);
        Type type =new TypeToken<ArrayList<OrderDetail>>(){}.getType();
        ArrayList<OrderDetail> orderDetails=gson.fromJson(json,type);
        return  orderDetails;
    }

    public void loadData(ArrayList<OrderDetail> orderDetails){
        SharedPreferences.Editor editor=getApplicationContext().getSharedPreferences("MyPre",MODE_PRIVATE).edit();
        Gson gson=new Gson();
        String json = gson.toJson(orderDetails);
        editor.putString("orderDetails",json);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user__view_detail_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent intent=new Intent(this,User_OrderCart.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_orders) {
            Intent intent=new Intent(this,User_OrderHistory.class);
            startActivity(intent);
            this.finish();
        } else if(id == R.id.nav_information){
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sign_out) {
            SharedPreferences preferences = getSharedPreferences("Mypref", 0);
            preferences.edit().remove("userID").commit();
            preferences.edit().remove("orderDetails").commit();
            Intent intent=new Intent(this,SigninActivity.class);
            startActivity(intent);
            this.finish();
        }else if(id==R.id.nav_menu){
            Intent intent=new Intent(this,Home.class);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
