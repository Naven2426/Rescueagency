package com.example.rescueagency.admin.AdminAgencyViewDetailList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.AdminMemberDetailViewFragment;
import com.example.rescueagency.agency.AgencyUserChatFragment;

import java.util.List;

public class AdminAgencyViewDetailListHolder extends RecyclerView.Adapter <AdminAgencyViewDetailListHolder.MyAdminAgencyViewDetailListHolder>{

    List<AdminAgencyViewDetailList> list;
    FragmentActivity activity;

    public AdminAgencyViewDetailListHolder(List<AdminAgencyViewDetailList> data, FragmentActivity activity){
        this.list = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyAdminAgencyViewDetailListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_profile_member_list,
                parent, false);
        return new MyAdminAgencyViewDetailListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdminAgencyViewDetailListHolder holder, int position) {
        AdminAgencyViewDetailList data = list.get(position);

        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.error)
                .error(R.mipmap.error).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AdminMemberDetailViewFragment());
                transaction.addToBackStack("AdminMemberDetailViewFragment").commit();
            }
        });

    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyAdminAgencyViewDetailListHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView  textView;
        CardView  cardView;

        public MyAdminAgencyViewDetailListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.AgencyProfileCardView);
            imageView = itemView.findViewById(R.id.idAgencyMemberProfileImage);
            textView = itemView.findViewById(R.id.idAgencyMemberProfileName);
        }
    }
}
