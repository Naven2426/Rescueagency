package com.example.rescueagency.admin.HomeFragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.Constant;
import com.example.rescueagency.LoginActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.AdminEditCategoryFragment;
import com.example.rescueagency.agency.AgencyEmergencyRequestDetailFragment;

import java.util.List;

public class AdminCategoryListHolder extends RecyclerView.Adapter<AdminCategoryListHolder.MyAdminCategoryListHolder> {

    List<AdminCategoryList> list;
    FragmentActivity activity;
    RecyclerView  recyclerView;
    Context context;

    public AdminCategoryListHolder(List<AdminCategoryList> list, FragmentActivity activity,RecyclerView recyclerView) {
        this.list = list;
        this.activity = activity;
        this.recyclerView = recyclerView;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View adminCategoryinfo =holder.adminCategoryInfoCV;
                if (adminCategoryinfo.getVisibility() == View.VISIBLE) {
                    adminCategoryinfo.setVisibility(View.GONE);
                }
            }
            });

        holder.categoryinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View adminCategoryinfo =holder.adminCategoryInfoCV;
                if (adminCategoryinfo.getVisibility() == View.GONE) {
                    adminCategoryinfo.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.categoryedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,AdminCategoryList.class.getName(), Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AdminEditCategoryFragment());
                transaction.addToBackStack("AdminEditCategoryFragment").commit();
            }
        });

        holder.categorydelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.category_delete_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idAgencyAddedBTNButton);
                androidx.appcompat.widget.AppCompatButton cancel_text = dialog.findViewById(R.id.idAgencyAddedBTHButton);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        }
        });
        



    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyAdminCategoryListHolder extends RecyclerView.ViewHolder {

        AppCompatImageView categoryimage;
        AppCompatImageView categoryinfo;
        AppCompatTextView categoryname;
        AppCompatImageView categoryedit;
        AppCompatImageView categorydelete;
        CardView cardView;
        CardView adminCategoryInfoCV;

        public MyAdminCategoryListHolder(@NonNull View itemView) {
            super(itemView);

            categoryimage = itemView.findViewById(R.id.idAdminCategoryImage);
            categoryinfo = itemView.findViewById(R.id.idAdminCategoryEdit);
            categoryname = itemView.findViewById(R.id.idAdminCategoryName);
            cardView = itemView.findViewById(R.id.idAdminCategoryListCV);
            adminCategoryInfoCV = itemView.findViewById(R.id.idAdminCategoryInfoCV);
            categoryedit = itemView.findViewById(R.id.idAdminCategoryEditIV);
            categorydelete = itemView.findViewById(R.id.idAdminCategoryTrashIV);
        }
    }




}
