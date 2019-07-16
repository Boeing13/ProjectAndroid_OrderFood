package com.example.project_orderfood.UserAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_orderfood.Common.ItemClickListener;
import com.example.project_orderfood.R;
import com.example.project_orderfood.User_ListFood;
import com.example.project_orderfood.entity.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener, View.OnLongClickListener {
    public TextView tvCategoryName;
    public ImageView ivCategoryImage;
//    public RelativeLayout relativeLayout;

    private ItemClickListener itemClickListener;

    public MyViewHolder(View view) {
        super(view);
        tvCategoryName = view.findViewById(R.id.menu_name);
        ivCategoryImage = view.findViewById(R.id.menu_image);
        //   relativeLayout = view.findViewById(R.id.relativeLayout);

        // ivCategoryImage.setOnCreateContextMenuListener(this);

        //  ivCategoryImage=itemView.findViewById(R.id.menu_image);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderIcon(R.drawable.ic_create_black_24dp);
//            menu.setHeaderTitle("Select an option");
//            menu.add(this.getAdapterPosition(), 0, 0, "Update category");
//            menu.add(this.getAdapterPosition(), 1, 1, "Delete category");
    }

    //HB
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}


public class MyCategoryAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Category> categoryList;
    private Context context;


    public MyCategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    public MyCategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;

    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());
        Picasso.with(context).load(category.getImage()).into(holder.ivCategoryImage);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                int cateID = categoryList.get(position).getId();
                Intent intent = new Intent(context, User_ListFood.class);
                intent.putExtra("cateID", cateID);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
