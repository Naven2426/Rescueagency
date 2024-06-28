package com.example.rescueagency.admin.HomeFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;

import java.util.List;

public class AdminUserListHolder extends RecyclerView.Adapter<AdminUserListHolder.MyAdminUserListHolder> {


    List<AdminUserViewList> list;

    FragmentActivity activity;

    public AdminUserListHolder (List<AdminUserViewList> data ,FragmentActivity activity){
        this.list = data;
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyAdminUserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_view_list,
                parent, false);
        return new MyAdminUserListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdminUserListHolder holder, int position) {
        AdminUserViewList data =list.get(position);

        Glide.with(activity).load(data.getProfileimage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View u) {
                Toast.makeText(activity, data.getProfileimage(),Toast.LENGTH_SHORT).show();

            }

        });

    }


    @Override
    public int getItemCount() {return list.size();}

    public class MyAdminUserListHolder extends RecyclerView.ViewHolder{
        TextView textusername;
        TextView textusernamet;
        TextView textname;
        TextView textnamet;
        ImageView imageView;
        CardView cardView;

        public MyAdminUserListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.idAdminUserListCV);
            textusername = itemView.findViewById(R.id.idAdminUserName);
            textusernamet = itemView.findViewById(R.id.idAdminUserNameTV);
            textname = itemView.findViewById(R.id.idAdminUserListName);
            imageView = itemView.findViewById(R.id.idAdminUserProfile);
            textnamet = itemView.findViewById(R.id.idAdminUserListNameTV);

            }
    }
}
