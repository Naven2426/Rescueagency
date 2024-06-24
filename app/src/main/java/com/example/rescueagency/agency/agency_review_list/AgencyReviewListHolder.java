package com.example.rescueagency.agency.agency_review_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.AdminAgencyViewDetailList.AdminAgencyViewDetailList;

import java.util.List;

public class AgencyReviewListHolder extends RecyclerView.Adapter<AgencyReviewListHolder.MyAgencyReviewListHolder>{

    List<AgencyReviewList> list;
    FragmentActivity activity;

    public AgencyReviewListHolder(List<AgencyReviewList> data, FragmentActivity activity) {
        this.list = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyAgencyReviewListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_review_list,
                parent,false);
        return new MyAgencyReviewListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAgencyReviewListHolder holder, int position) {
         AgencyReviewList data = list.get(position);


        Glide.with(activity).load(data.getProfile()).placeholder(R.mipmap.error)
                .error(R.mipmap.error).into(holder.imageView);

    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyAgencyReviewListHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView username;
        TextView agencyname;
        CardView rating;
        TextView review;
        CardView cardView;



        public MyAgencyReviewListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.idAdminAgencyListCV);
            imageView = itemView.findViewById(R.id.idAdminAgencyProfile);
            username = itemView.findViewById(R.id.idAgencyUserName);
            agencyname = itemView.findViewById(R.id.idAgencyName);
            rating = itemView.findViewById(R.id.idAdminAgencyRatingCV);
            review = itemView.findViewById(R.id.idAgencyReviewText);

        }
    }
}
