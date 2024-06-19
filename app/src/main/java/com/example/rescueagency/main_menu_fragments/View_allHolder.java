package com.example.rescueagency.main_menu_fragments;

import static android.app.PendingIntent.getActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.LoginActivityFragments.LoginFragment;
import com.example.rescueagency.R;

import java.util.List;
import com.squareup.picasso.Picasso;
public class View_allHolder extends RecyclerView.Adapter<View_allHolder.MyViewHolder>{

    List<View_all> list;
    FragmentActivity activity;
    public View_allHolder(List<View_all> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_all,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View_all data=list.get(position);
        holder.textView.setText(data.getText());
        Glide.with(activity).load(data.getImage()).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new BookingFragment());
                transaction.addToBackStack("BookingFragment").commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.viewAllCardView);
            imageView=itemView.findViewById(R.id.viewAllimg2);
            textView=itemView.findViewById(R.id.viewAllText);
        }
    }
}
