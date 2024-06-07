package com.example.rescueagency.user_agency_member_list_view;

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
import com.example.rescueagency.main_menu_fragments.View_all;

import java.util.List;

public class UserRescueTeamMemberListHolder extends RecyclerView.Adapter<UserRescueTeamMemberListHolder.MyUserRescueTeamMemberListHolder> {

    List<View_all> list;
    FragmentActivity activity;
    public UserRescueTeamMemberListHolder(List<View_all> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }



    @NonNull
    @Override
    public MyUserRescueTeamMemberListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.user_rescue_team_member_list,
                parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserRescueTeamMemberListHolder holder, int position) {
        View_all data=list.get(position);
        holder.textView.setText(data.getText());
        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, data.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyUserRescueTeamMemberListHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;

        public MyUserRescueTeamMemberListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_rescue_team_view_member_card);
            textView = itemView.findViewById(R.id.id_rescue_team_view_member_name_value);
            imageView = itemView.findViewById(R.id.id_rescue_team_view_member_image);
        }
    }
}
