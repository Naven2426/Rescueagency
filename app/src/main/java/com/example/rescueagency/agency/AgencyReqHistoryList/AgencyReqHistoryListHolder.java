package com.example.rescueagency.agency.AgencyReqHistoryList;

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

import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencySOSRequestDetailFragment;
import com.example.rescueagency.agency.AgencyStatusUpdateFragment;

import java.util.List;

public class AgencyReqHistoryListHolder extends RecyclerView.Adapter<AgencyReqHistoryListHolder.MyAgencyReqHistoryListHolder> {

    List<AgencyReqHistoryList> list;
    FragmentActivity activity;

    public AgencyReqHistoryListHolder(List<AgencyReqHistoryList> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AgencyReqHistoryListHolder.MyAgencyReqHistoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.agency_req_approved_list,
                parent, false);
        return new MyAgencyReqHistoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAgencyReqHistoryListHolder holder, int position) {
        AgencyReqHistoryList agencyReqHistoryList = list.get(position);
        holder.status.setText(agencyReqHistoryList.getStatus());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,AgencyReqHistoryList.getImage(), Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AgencyStatusUpdateFragment());
                transaction.addToBackStack("AgencyStatusUpdateFragment").commit();
            }
        });



    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyAgencyReqHistoryListHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CardView statusID;
        TextView status;
        TextView date;
        TextView dateID;
        TextView alert;
        TextView alertID;
        ImageView img;

        public MyAgencyReqHistoryListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.idAgencyReqHistoryListCV);
            status = itemView.findViewById(R.id.idAgencyStatusTV);
            date = itemView.findViewById(R.id.idAgencyReqDate);
            statusID = itemView.findViewById(R.id.idAgencyReqAcceptViewCV);
            dateID = itemView.findViewById(R.id.idAgencyReqDateTV);
            alert = itemView.findViewById(R.id.idAgencyAlertTypeTV);
            alertID = itemView.findViewById(R.id.idAgencyAlertNameTV);
            img = itemView.findViewById(R.id.idAgencyReqAcceptViewIV);
        }

    }
}
