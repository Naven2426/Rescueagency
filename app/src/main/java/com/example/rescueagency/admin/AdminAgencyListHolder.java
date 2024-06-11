package com.example.rescueagency.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;

import java.util.List;

public class AdminAgencyListHolder extends RecyclerView.Adapter <AdminAgencyListHolder.MyAdminAgencyListHolder>{

    List<AdminAgencyViewList> list;
    FragmentActivity activity;


    public AdminAgencyListHolder(List<AdminAgencyViewList> data, FragmentActivity activity) {
        this.list = data;
        this.activity = activity;
    }



    @NonNull
    @Override
    public MyAdminAgencyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_agency_view_list,
                parent, false);
        return new MyAdminAgencyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdminAgencyListHolder holder, int position) {
        AdminAgencyViewList data = list.get(position);

        Glide.with(activity).load(data.getAgencyimage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View o) {
                Toast.makeText(activity, data.getAgencyimage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyAdminAgencyListHolder extends RecyclerView.ViewHolder {

        TextView textagencyname;
        TextView textrating;
        TextView textaddress;
        TextView textagencytype;
        ImageView imageView;
        CardView cardView;

        public MyAdminAgencyListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.idAdminAgencyListCV);
            textagencyname = itemView.findViewById(R.id.idAdminAgencyName);
            textrating = itemView.findViewById(R.id.idAdminAgencyRatingNum);
            textaddress = itemView.findViewById(R.id.idAdminAgencyAddressTV);
            textagencytype = itemView.findViewById(R.id.idAdminAgencyTypeName);
            imageView = itemView.findViewById(R.id.idAdminAgencyProfile);
        }


    }

}
