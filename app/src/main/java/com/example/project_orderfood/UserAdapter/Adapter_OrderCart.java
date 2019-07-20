package com.example.project_orderfood.UserAdapter;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_orderfood.R;
import com.example.project_orderfood.entity.Food;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.model.FoodDAO;

import java.util.ArrayList;

public class Adapter_OrderCart  extends BaseAdapter {
     ArrayList<OrderDetail> orderDetails;
     Activity context;

    public Adapter_OrderCart(ArrayList<OrderDetail> orderDetails, Activity context) {
        this.orderDetails = orderDetails;
        this.context = context;
        int resource=1;
    }

    @Override
    public int getCount() {
        return orderDetails.size();
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
        TextView txtName;
        TextView txtPrice;
        TextView txtNumberPro;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,null);
        }
        txtName=convertView.findViewById(R.id.txtFName);
        txtPrice=convertView.findViewById(R.id.txtPrice);
        txtNumberPro=convertView.findViewById(R.id.txtNumberProduct);
       // ConstraintLayout layout=convertView.findViewById(R.id.layout_Total);

        Food food = new FoodDAO().getFood(orderDetails.get(position).foodID);
        txtName.setText(food.name);
        txtPrice.setText(Float.toString(orderDetails.get(position).price));
        txtNumberPro.setText(String.valueOf(orderDetails.get(position).quantity));
        return convertView;
    }
}
