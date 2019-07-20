package com.example.project_orderfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_orderfood.UserAdapter.Adapter_OrderCart;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.model.OrderDAO;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewDetailOrder extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    TextView txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        int oid=getIntent().getIntExtra("oid",-1);

        txtTotal=findViewById(R.id.txtTotal);

        ArrayList<OrderDetail> orderDetai = new OrderDAO().getOrderDetai(oid);
        listView=findViewById(R.id.listViewOrder);
        Adapter_OrderCart adapter_orderCart=new Adapter_OrderCart(orderDetai,this);
        listView.setAdapter(adapter_orderCart);

       float total= getTotal(orderDetai);
       txtTotal.setText("Total :" + Float.toString(total));
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
        getMenuInflater().inflate(R.menu.view_detail_order, menu);
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
