package com.example.rescueagency.agency.SOSRequestRVFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyEmergencyRequestDetailFragment;
import com.example.rescueagency.agency.AgencySOSLocationViewFragment;
import com.example.rescueagency.agency.AgencySOSRequestDetailFragment;

import java.util.List;

public class AgencySOSReqListHolder extends RecyclerView.Adapter<AgencySOSReqListHolder.MyAgencySOSReqListHolder> {

    List<AgencySOSReqList> list;
    FragmentActivity activity;
    public AgencySOSReqListHolder(List<AgencySOSReqList> list, FragmentActivity activity)
    {
        this.list = list;
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyAgencySOSReqListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sos_req_list,
                parent, false);
        return new MyAgencySOSReqListHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAgencySOSReqListHolder holder, int position) {
        AgencySOSReqList sosReqList = list.get(position);
        holder.textname.setText(sosReqList.getName());
        if(!sosReqList.getStatus().equalsIgnoreCase("sos")){
            holder.location.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            holder.alertType.setVisibility(View.VISIBLE);
            holder.alertTypeText.setVisibility(View.VISIBLE);
            holder.alertTypeText.setText(sosReqList.getStatus());
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity,sosReqList.getName(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout,new AgencyEmergencyRequestDetailFragment());
                    transaction.addToBackStack("AgencyEmergencyRequestDetailFragment").commit();
                }
            });
        }else{
            holder.cardView.setVisibility(View.VISIBLE);
            holder.alertType.setVisibility(View.GONE);
            holder.alertTypeText.setVisibility(View.GONE);
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity,sosReqList.getName(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout,new AgencySOSRequestDetailFragment());
                    transaction.addToBackStack("AgencySOSRequestDetailFragment").commit();
                }
            });
        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Opening Location View", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.frameLayout, new AgencySOSLocationViewFragment());
                transaction.addToBackStack("AgencySOSLocationViewFragment").commit();
            }
        });

    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyAgencySOSReqListHolder extends RecyclerView.ViewHolder{

        TextView textname;
        TextView textname_id;
        TextView date;
        TextView date_sent;
        TextView location;
        CardView cardView;

        ImageView info;
        CardView view;
        CardView viewMap;
        AppCompatTextView alertTypeText;
        AppCompatTextView alertType;

        public MyAgencySOSReqListHolder(@NonNull View itemView) {
            super(itemView);
            textname        = itemView.findViewById(R.id.idReqName);
            textname_id      = itemView.findViewById(R.id.idReqNameTV);
            date = itemView.findViewById(R.id.idReqDate);
            date_sent = itemView.findViewById(R.id.idReqDateTV);
            location = itemView.findViewById(R.id.idReqLocation);
            cardView = itemView.findViewById(R.id.idSOSReqListCV);
            info = itemView.findViewById(R.id.idSOSReqInfoIV);
            view = itemView.findViewById(R.id.idViewCV);
            alertTypeText = itemView.findViewById(R.id.idReqAlertType);
            alertType = itemView.findViewById(R.id.idReqAlertValue);

        }
    }
}
