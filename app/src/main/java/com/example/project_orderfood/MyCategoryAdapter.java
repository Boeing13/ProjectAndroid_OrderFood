package com.example.project_orderfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project_orderfood.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder> {
    private List<Category> categoryList;
    private Context context;
    private int position;


    public MyCategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    public MyCategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView tvCategoryName;
        public ImageView ivCategoryImage;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.menu_name);
            ivCategoryImage = view.findViewById(R.id.menu_image);
            relativeLayout = view.findViewById(R.id.relativeLayout);
            ivCategoryImage.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderIcon(R.drawable.ic_create_black_24dp);
//            menu.setHeaderTitle("Select an option");
//            menu.add(this.getAdapterPosition(), 0, 0, "Update category");
//            menu.add(this.getAdapterPosition(), 1, 1, "Delete category");
        }

    }


    @Override
    public void onViewRecycled(MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public MyCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());
        // holder.ivCategoryImage.setImageResource(R.drawable.italianfood);
        Picasso.with(context).load(category.getImage()).into(holder.ivCategoryImage);
        holder.ivCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, FoodPage.class);
//                intent.putExtra("categoryID", category.getCategoryID());
//                context.startActivity(intent);
            }
        });

        holder.ivCategoryImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }

        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
