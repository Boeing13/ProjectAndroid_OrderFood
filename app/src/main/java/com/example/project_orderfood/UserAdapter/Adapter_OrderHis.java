package com.example.project_orderfood.UserAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_orderfood.R;
import com.example.project_orderfood.entity.Order;

import java.util.ArrayList;

public class Adapter_OrderHis extends BaseAdapter {
    ArrayList<Order> orders;
    Activity context;

    public Adapter_OrderHis(ArrayList<Order> order, Activity context) {
        this.orders = order;
        this.context = context;
        int resource=1;
    }

    @Override
    public int getCount() {
        return orders.size();
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
        TextView txtFID;
        TextView txtStatus;
        TextView txtPhone,txtAddress;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,null);
        }
        txtFID=convertView.findViewById(R.id.txtFID);
        txtStatus=convertView.findViewById(R.id.txtStatus);
        txtPhone =convertView.findViewById(R.id.txtPhone);
        txtAddress=convertView.findViewById(R.id.txtAddress);
        txtFID.setText(orders.get(position).orderID+"");
        txtStatus.setText(orders.get(position).status);
        txtPhone.setText(orders.get(position).phone);
        txtAddress.setText(orders.get(position).address);
        return convertView;
    }
}
