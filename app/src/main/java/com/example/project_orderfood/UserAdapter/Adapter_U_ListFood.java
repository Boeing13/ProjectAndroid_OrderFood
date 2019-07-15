package com.example.project_orderfood.UserAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_orderfood.R;
import com.example.project_orderfood.entity.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_U_ListFood extends BaseAdapter {
    ArrayList<Food> foods;
    Activity context;
    int resource;
    public Adapter_U_ListFood(ArrayList<Food> foods, Activity context) {
        this.foods = foods;
        this.context = context;
        resource=1;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        TextView txtName;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_detail_layout,null);
        }
        img=convertView.findViewById(R.id.myImageView_food);
        txtName=convertView.findViewById(R.id.txtFoodName);
        txtName.setText(foods.get(position).name);
        String url=foods.get(position).img;
        Picasso.with(context).load(url).into(img);

        return convertView;
    }
}
