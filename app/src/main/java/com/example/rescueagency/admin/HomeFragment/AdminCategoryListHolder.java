package com.example.rescueagency.admin.HomeFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;

import java.util.List;

public class AdminCategoryListHolder extends RecyclerView.Adapter<AdminCategoryListHolder.MyAdminCategoryListHolder> {

    List<AdminCategoryList> list;
    FragmentActivity activity;

    public AdminCategoryListHolder(List<AdminCategoryList> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyAdminCategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_category_view_list, parent, false);
        return new MyAdminCategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdminCategoryListHolder holder, int position) {

        AdminCategoryList data = list.get(position);
        Glide.with(activity).load(data.getCategoryimage()).error(R.mipmap.error).into(holder.categoryimage);
        ;

    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyAdminCategoryListHolder extends RecyclerView.ViewHolder {

        AppCompatImageView categoryimage;
        AppCompatImageView categoryinfo;
        AppCompatTextView categoryname;
        CardView cardView;

        public MyAdminCategoryListHolder(@NonNull View itemView) {
            super(itemView);

            categoryimage = itemView.findViewById(R.id.idAdminCategoryImage);
            categoryinfo = itemView.findViewById(R.id.idAdminCategoryEdit);
            categoryname = itemView.findViewById(R.id.idAdminCategoryName);
            cardView = itemView.findViewById(R.id.idAdminCategoryListCV);
        }
    }




}
