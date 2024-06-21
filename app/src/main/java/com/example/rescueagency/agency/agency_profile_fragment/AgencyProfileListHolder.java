package com.example.rescueagency.agency.agency_profile_fragment;

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
import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.R;

import java.util.List;


public class AgencyProfileListHolder extends RecyclerView.Adapter<AgencyProfileListHolder.MyAgencyProfileListHolder>{

    List<AgencyProfileList> list;
    FragmentActivity activity;
    public AgencyProfileListHolder(List<AgencyProfileList> list) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AgencyProfileListHolder.MyAgencyProfileListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_profile_member_list,
                parent, false);
        return new AgencyProfileListHolder.MyAgencyProfileListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyProfileListHolder.MyAgencyProfileListHolder holder, int position) {

        AgencyProfileList data=list.get(position);
        holder.textView.setText(data.getText());
        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AgencyProfileFragment());
                transaction.addToBackStack("AgencyProfileFragment").commit();
            }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAgencyProfileListHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView textView;
        public MyAgencyProfileListHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.AgencyProfileCardView);
            imageView=itemView.findViewById(R.id.idAgencyMemberProfileImage);
            textView=itemView.findViewById(R.id.idAgencyMemberProfileName);
        }
    }


}
