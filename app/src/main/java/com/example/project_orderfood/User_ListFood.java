package com.example.project_orderfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.UserAdapter.Adapter_U_ListFood;
import com.example.project_orderfood.entity.Food;
import com.example.project_orderfood.model.FoodDAO;

import java.util.ArrayList;

public class User_ListFood extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    TextView txtSearch,txtError,txtFullName;
    int cateID;
     ArrayList<Food> allFoods=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__list_food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.listView_Food);
        txtSearch=findViewById(R.id.txtSearch);
        txtError=findViewById(R.id.txtError);
       txtSearch.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if(keyCode==KeyEvent.KEYCODE_ENTER){
                   search();
                   return true;
               }
               return false;
           }
       });


        FoodDAO foodDAO = new FoodDAO();

        //Example ưw
       // cateID = 1;
        cateID= getIntent().getIntExtra("cateID",0);
        allFoods = foodDAO.getAllFoodsByID(cateID);
        Adapter_U_ListFood adapter = new Adapter_U_ListFood(allFoods, this);
        listView.setAdapter(adapter);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              int foodID = allFoods.get(position).foodID;
              System.out.println("===============================");
              Intent intent = new Intent(User_ListFood.this, User_ViewDetailFood.class);
              intent.putExtra("foodID", foodID);
              startActivity(intent);
          }
      });


        //Set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getUsername());

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
        getMenuInflater().inflate(R.menu.user__list_cate_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnSelect) {
            AlertDialog.Builder builder = new AlertDialog.Builder(User_ListFood.this);
            builder.setTitle("Search");
            View customLayout = getLayoutInflater().inflate(R.layout.address, null);
            builder.setView(customLayout);
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    search();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void search(){
        String tmp = txtSearch.getText().toString();
        FoodDAO dao = new FoodDAO();
         allFoods = dao.Search(tmp, cateID);
        if (allFoods.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(User_ListFood.this);
            builder.setTitle("Message");
            builder.setMessage("Dont have that food !!");
            Adapter_U_ListFood adapter = new Adapter_U_ListFood(allFoods, User_ListFood.this);
            listView.setAdapter(adapter);
            txtError.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            txtError.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            Adapter_U_ListFood adapter = new Adapter_U_ListFood(allFoods, User_ListFood.this);
            listView.setAdapter(adapter);
        }
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
