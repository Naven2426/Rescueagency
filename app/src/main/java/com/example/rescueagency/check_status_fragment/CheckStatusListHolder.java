package com.example.rescueagency.check_status_fragment;

import static androidx.core.content.ContextCompat.getObbDirs;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;
import com.example.rescueagency.TrackingMapActivity;
import com.example.rescueagency.UserAgencyChatActivity;

import java.util.List;


public class CheckStatusListHolder extends RecyclerView.Adapter<CheckStatusListHolder.MyCheckStatusListHolder> {

    List<CheckStatusList> list;
    FragmentActivity activity;
    public CheckStatusListHolder(List<CheckStatusList> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyCheckStatusListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chech_status_list,
                parent, false);
        return new MyCheckStatusListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCheckStatusListHolder holder, int position) {

        CheckStatusList data=list.get(position);
        holder.textstatus_name.setText(data.getStatus_name());

        if(data.getFrom().equalsIgnoreCase("history")){
            holder.track_agency.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }else{
            holder.track_agency.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.VISIBLE);
        }
        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.chat)
                .error(R.mipmap.chat).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, data.getStatus(), Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, UserAgencyChatActivity.class));

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               activity.startActivity(new Intent(activity, TrackingMapActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {return list.size();}

    public class MyCheckStatusListHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textstatus;
        AppCompatTextView textstatus_name;
        AppCompatTextView textalert;
        AppCompatTextView textalert_type;
        AppCompatTextView textdate;
        AppCompatTextView textdate_sent;
        AppCompatImageView imageView;
        CardView cardView;
        CardView track_agency;

        public MyCheckStatusListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.idCheckStatusCV);
            track_agency = itemView.findViewById(R.id.idCheckStatusCV2);
            textstatus = itemView.findViewById(R.id.idCSLStatus);
            textstatus_name = itemView.findViewById(R.id.idCSLStatusDescription);
            textalert= itemView.findViewById(R.id.idCSLAlertType);
            textalert_type= itemView.findViewById(R.id.idCSLAlertDescription);
            textdate= itemView.findViewById(R.id.idCSLDateSent);
            textdate_sent= itemView.findViewById(R.id.idCSLDate);
            imageView = itemView.findViewById(R.id.idCheckStatusIV);
        }
    }

}
