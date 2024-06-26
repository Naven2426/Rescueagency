package com.example.rescueagency.notification_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueagency.FeedbackFragment;
import com.example.rescueagency.R;

import java.util.List;

public class NotificationListHolder extends RecyclerView.Adapter<NotificationListHolder.MyNotificationListHolder> {

    List<NotificationList> list;
    FragmentActivity activity;
    public NotificationListHolder(List<NotificationList> list,FragmentActivity activity)
    {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyNotificationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_request_list,
                parent, false);
        return new MyNotificationListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotificationListHolder holder, int position) {
        NotificationList data=list.get(position);
        holder.textfeedback.setText(data.getStatus_name());

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, data.getStatus(), Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new FeedbackFragment());
                transaction.addToBackStack("FeedbackFragment").commit();
            }
        });



    }

    @Override
    public int getItemCount() {return list.size();}

    public class MyNotificationListHolder extends RecyclerView.ViewHolder{
        TextView textstatus;
        TextView textalert;
        TextView textalert_type;
        TextView textdate;
        TextView textdate_sent;
        TextView textfeedback;
        AppCompatButton send;
        CardView textstatus_name;
        CardView cardView;

        public MyNotificationListHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.id_notifiaction_card_view);
            textstatus = itemView.findViewById(R.id.id_notification_status);
            textstatus_name = itemView.findViewById(R.id.id_notification_status_view);
            textalert= itemView.findViewById(R.id.id_notification_alert_type);
            textalert_type= itemView.findViewById(R.id.id_notification_alert_type_text);
            textdate= itemView.findViewById(R.id.id_notification_date);
            textdate_sent= itemView.findViewById(R.id.id_notification_date_text);
            textfeedback = itemView.findViewById(R.id.id_notification_feedback);
            send = itemView.findViewById(R.id.idNotificationFeedbackButton);
        }
    }
}
