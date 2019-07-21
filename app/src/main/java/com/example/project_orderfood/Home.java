package com.example.project_orderfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.UserAdapter.MyCategoryAdapter;
import com.example.project_orderfood.UserAdapter.ViewPagerAdapter;
import com.example.project_orderfood.entity.Category;
import com.example.project_orderfood.model.CategoryDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    private List<Category> categoryList;
    private CategoryDAO dao;
    private RecyclerView.Adapter adapter;

    //slider
    private String[] imageList;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Menu");
        categoryList = new ArrayList<>();
        setSupportActionBar(toolbar);

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

        //Load menu
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
       // recycler_menu.setOnc

        //Setup slider
        imageList = new String[10];
        setupSlider();
        viewPager = findViewById(R.id.menuViewPager);
        viewPagerAdapter = new ViewPagerAdapter(this, imageList);
        viewPager.setAdapter(viewPagerAdapter);

    }

    private void setupSlider() {
        imageList = new String[]{
                "https://image.freepik.com/free-vector/big-sale-template-banner-vector-background_1375-1003.jpg",
                "http://d2.fajridemo.com/eurogulf/wp-content/uploads/2017/06/pizza-hut-promo.jpg",
                "https://recipes-secure-graphics.grocerywebsite.com/0_GraphicsRecipes/4589_4k.jpg",
                "https://i.ndtvimg.com/i/2016-06/chinese-625_625x350_81466064119.jpg",
                "https://i.hungrygowhere.com/business/1c/7a/12/00/66f50295-0b7b-4d46-bf95-0aa4a32c21dbimg-1999_800x0_crop_800x800_8fe89a587c.jpg",
                "https://downshiftology.com/wp-content/uploads/2019/04/Cobb-Salad-3.jpg",
                "https://coconutsandkettlebells.com/wp-content/uploads/homemade-ice-cream-cake.jpg",
                "https://res.cloudinary.com/sagacity/image/upload/c_crop,h_3456,w_5184,x_0,y_0/c_limit,dpr_auto,f_auto,fl_lossy,q_80,w_1080/superdeluxe_owgtql.jpg"

        };
    }

    private void loadMenu() {
        categoryList.clear();
        dao = new CategoryDAO();
        try {
            categoryList = dao.getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new MyCategoryAdapter(categoryList, this);
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);

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
        getMenuInflater().inflate(R.menu.home, menu);
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
        } else if(id == R.id.nav_information){
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            Intent intent=new Intent(this,User_OrderHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_sign_out) {
            SharedPreferences preferences = getSharedPreferences("Mypref", 0);
            preferences.edit().remove("userID").commit();
            preferences.edit().remove("orderDetails").commit();
            Intent intent=new Intent(this,SigninActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_menu){

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
