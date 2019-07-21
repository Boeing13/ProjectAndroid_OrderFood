package com.example.project_orderfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_orderfood.Common.AddressDialog;
import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.UserAdapter.Adapter_OrderCart;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.model.OrderDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class User_OrderCart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddressDialog.DialogListener {
    ListView listView;
    TextView txtTotal, txtFullName,txtNotify;
    ArrayList<OrderDetail> orderDetails;
    Button btnBuy;
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__order_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        btnBuy = findViewById(R.id.btnBuy);
        listView = findViewById(R.id.listView_Food);
        txtTotal = findViewById(R.id.txtTotal);
        txtNotify=findViewById(R.id.txtNotifi);
        registerForContextMenu(listView);
        orderDetails = getData();
        if(orderDetails.isEmpty()){
            listView.setVisibility(View.INVISIBLE);
            txtNotify.setVisibility(View.VISIBLE);
            txtTotal.setText("Total: 0");
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(User_OrderCart.this,"Please choose some products first",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            listView.setVisibility(View.VISIBLE);
            txtNotify.setVisibility(View.INVISIBLE);

            Adapter_OrderCart adapter = new Adapter_OrderCart(orderDetails, this);
            listView.setAdapter(adapter);
            txtTotal.setText("Total: " + Float.toString(getTotal(orderDetails)));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(User_OrderCart.this, User_ViewDetailFood.class);
                    intent.putExtra("foodID", orderDetails.get(position).foodID);
                    startActivity(intent);
                }
            });

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressDialog addressDialog = new AddressDialog();
                    addressDialog.act = User_OrderCart.this;
                    addressDialog.show(getSupportFragmentManager(), "address");

                }
            });
        }





        //Set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getUsername());
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listView_Food) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_order_cart_del, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btnDel) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = info.position;
            orderDetails.remove(listPosition);
            loadData(orderDetails);
            Adapter_OrderCart adapter = new Adapter_OrderCart(orderDetails, this);
            listView.setAdapter(adapter);
            txtTotal.setText("Total: " + Float.toString(getTotal(orderDetails)));
        }
        return super.onContextItemSelected(item);
    }

    public float getTotal(ArrayList<OrderDetail> orderDetails) {
        float total = 0;
        for (int i = 0; i < orderDetails.size(); i++) {
            total += orderDetails.get(i).price * orderDetails.get(i).quantity;
        }
        return total;
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
        getMenuInflater().inflate(R.menu.user__order_cart, menu);
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
//            Intent intent=new Intent(this,User_OrderCart.class);
//            startActivity(intent);
//            this.finish();
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(this, User_OrderHistory.class);
            startActivity(intent);
            this.finish();
        } else if(id == R.id.nav_information){
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sign_out) {
            SharedPreferences preferences = getSharedPreferences("Mypref", 0);
            preferences.edit().remove("userID").commit();
            preferences.edit().remove("orderDetails").commit();
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_menu) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<OrderDetail> getData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPre", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("orderDetails", null);
        Type type = new TypeToken<ArrayList<OrderDetail>>() {
        }.getType();
        ArrayList<OrderDetail> orderDetails = gson.fromJson(json, type);
        return orderDetails;
    }

    public void loadData(ArrayList<OrderDetail> orderDetails) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MyPre", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderDetails);
        editor.putString("orderDetails", json);
        editor.apply();
    }

    @Override
    public void sendInfor(String text) {
        float totalTemp=getTotal(orderDetails);
        if(text.isEmpty()){
            Toast.makeText(this,"Please input address",Toast.LENGTH_LONG).show();
        }else{
            int i = new OrderDAO().insertOrder(Common.currentUser, text, totalTemp);
            if(i!=-1){
                Toast.makeText(this,"Order successful. Thank you so much",Toast.LENGTH_LONG).show();
                int oid= new OrderDAO().getNewestOrder();
                int check = new OrderDAO().insertOrderDetail(oid, orderDetails);
                orderDetails.clear();
                loadData(orderDetails);
                txtTotal.setText("Total: 0" );
                listView.setVisibility(View.INVISIBLE);
                txtNotify.setVisibility(View.VISIBLE);
            }
        }

    }
}
