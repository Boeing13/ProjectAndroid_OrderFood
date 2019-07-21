package com.example.project_orderfood;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.UserAdapter.ViewPagerAdapter;
import com.example.project_orderfood.entity.Category;
import com.example.project_orderfood.entity.Restaurant;
import com.example.project_orderfood.model.CategoryDAO;
import com.example.project_orderfood.model.RestaurantDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout linearPhone, linearAddress;
    private TextView txtFullName;
    private RestaurantDAO dao;
    private Restaurant restaurant;
    private TextView tvPhone, tvAddress;

    //slider
    private ViewPagerAdapter adapter;
    private String[] imageList;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About us");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getUsername());

        //Set phone and address
        linearPhone = findViewById(R.id.linearPhone);
        linearAddress = findViewById(R.id.linearAddress);
        tvPhone = linearPhone.findViewById(R.id.tvPhone);
        tvAddress = linearAddress.findViewById(R.id.tvAddress);
        restaurant = new Restaurant();
        setInformation();

        //Setup slider
        imageList = new String[10];
        setupSlider();
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(this, imageList);
        viewPager.setAdapter(adapter);


    }

    private void setupSlider() {
        imageList = new String[]{
                "https://designcontest-com-designcontest.netdna-ssl.com/data/contests/215250/entries/d5fdc19b02f7f67a.jpg",
                "https://jobfinderslb.com/jfl/wp-content/uploads/2018/01/WAITERS-AND-WAITRESS-1024x736.jpg",
                "https://d16jvv1mxapgw7.cloudfront.net/cover_59Sixty_komtar.jpg",
                "https://www.ahstatic.com/photos/5555_rsr001_01_p_1024x768.jpg",
                "https://santorinisecrets.com/wp-content/uploads/2016/01/santorini-romantic-restaurant-with-view-2.jpg",
                "https://www.hiddencitysecrets.com.au/wp-content/uploads/2017/05/Hacienda-Sydney-Unique-Function-Venues-CBD-Rooms-Rocks-Venue-Hire-Party-Room-Rooftop-Birthday-Corporate-Cocktail-Private-Weddings-Engagements-Event-001.jpg"
        };
    }

    public void setInformation(){
        dao = new RestaurantDAO();
        restaurant = dao.getInformation();
        System.out.println("INFORMATION: " + restaurant);
        tvPhone.setText(restaurant.getPhone() + "");
        tvAddress.setText(restaurant.getAddress() + "");
    }

    public void map(View view){
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }

    public void call(View view){
        if (ContextCompat.checkSelfPermission(AboutUsActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AboutUsActivity.this,
                    Manifest.permission.CALL_PHONE)) {

            } else {
                ActivityCompat.requestPermissions(AboutUsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        100);
            }
        } else {
            String number = tvPhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + number));
            startActivity(intent);
        }
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
        getMenuInflater().inflate(R.menu.about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_menu:
                menu();
                return true;
            case R.id.nav_information:
                return true;
            case R.id.nav_cart:
                cart();
                return true;
            case R.id.nav_orders:
                orders();
                return true;
            case R.id.nav_sign_out:
                signout();
                return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signout() {
        SharedPreferences preferences = getSharedPreferences("Mypref", 0);
        preferences.edit().remove("userID").commit();
        preferences.edit().remove("orderDetails").commit();
        Intent intent=new Intent(this,SigninActivity.class);
        startActivity(intent);
    }

    private void orders() {
        Intent intent=new Intent(this,User_OrderHistory.class);
        startActivity(intent);
    }

    private void cart() {
        Intent intent=new Intent(this,User_OrderCart.class);
        startActivity(intent);
    }

    private void menu(){
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
    }


}
