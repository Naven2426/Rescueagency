package com.example.rescueagency.agency.MessageList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyMemberDetailViewFragment;
import com.example.rescueagency.agency.AgencySOSRequestDetailFragment;
import com.example.rescueagency.agency.AgencyUserChatFragment;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileList;

import java.util.List;

public class AgencyMessageListHolder extends RecyclerView.Adapter<AgencyMessageListHolder.MyAgencyMessageListHolder> {

    List<AgencyMessageList> list;
    FragmentActivity activity;

    public AgencyMessageListHolder(List<AgencyMessageList> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyAgencyMessageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_notification_list,
                parent, false);
        return new MyAgencyMessageListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyMessageListHolder.MyAgencyMessageListHolder holder, int position) {

        AgencyMessageList data=list.get(position);
        holder.name.setText(data.getName());
        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AgencyUserChatFragment());
                transaction.addToBackStack("AgencyUserChatFragment").commit();
            }
        });

    }

    @Override
    public int getItemCount() {return list.size(); }

    public class MyAgencyMessageListHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        ImageView image;

        CardView cardView;

        public MyAgencyMessageListHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.idAgencyNotificationUserNameTV);
            message = itemView.findViewById(R.id.idAgencyNotificationUserStatusTV);
            image = itemView.findViewById(R.id.idAgencyNotificationUserProfileIV);
            cardView = itemView.findViewById(R.id.idAgencyNotificationCard);
        }

    }

}
