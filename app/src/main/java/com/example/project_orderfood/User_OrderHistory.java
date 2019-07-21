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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.UserAdapter.Adapter_OrderHis;
import com.example.project_orderfood.entity.Order;
import com.example.project_orderfood.model.OrderDAO;

import java.util.ArrayList;

public class User_OrderHistory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtFullName, txtNoti;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__order_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        txtNoti = findViewById(R.id.txtNotifi);
        listView = findViewById(R.id.lvOrderHis);
        final ArrayList<Order> allOrder = new OrderDAO().getAllOrder(Common.currentUser.getPhone());
        if (allOrder.isEmpty()) {
            txtNoti.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            txtNoti.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            Adapter_OrderHis adapter_orderHis = new Adapter_OrderHis(allOrder, this);
            listView.setAdapter(adapter_orderHis);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(User_OrderHistory.this, ViewDetailOrder.class);
                    intent.putExtra("oid", allOrder.get(position).orderID);
                    startActivity(intent);
                }
            });
        }


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
        getMenuInflater().inflate(R.menu.user__order_history, menu);
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
            Intent intent = new Intent(this, User_OrderCart.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_orders) {
//            Intent intent=new Intent(this,User_OrderHistory.class);
//            startActivity(intent);
//            this.finish();
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
}
