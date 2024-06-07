package com.example.rescueagency.main_menu_fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.NotificationFragment;
import com.example.rescueagency.R;


public class HomeFragment extends Fragment {


    CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    AppCompatImageView sos_main_emergency;
    AppCompatImageView notificationButton;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        cardView1=view.findViewById(R.id.homeViewAllCardView);
        cardView2=view.findViewById(R.id.id_home_medical_booking);
        cardView3=view.findViewById(R.id.id_home_fire_force_booking);
        cardView4=view.findViewById(R.id.id_home_tow_booking);
        cardView5=view.findViewById(R.id.id_home_cop_booking);


        sos_main_emergency=view.findViewById(R.id.id_sos_alert_img);
        notificationButton=view.findViewById(R.id.GfG_full_loo);
        sos_main_emergency.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Dialog dialog = new Dialog(getContext());
                  dialog.setContentView(R.layout.sos_mainalert_dialog_layout);
                  dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                  dialog.setCancelable(false);
                  dialog.show();

                  androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.id_sos_done_button);

                  okay_text.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dialog.dismiss();

                      }
                  });

              }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                transaction.replace(R.id.frameLayout,new NotificationFragment()).addToBackStack(null).commit();

            }
            });


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_allFragment view_allFragment=new View_allFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frameLayout,new View_allFragment()).addToBackStack("").commit();

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_allFragment view_allFragment=new View_allFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frameLayout,new BookingFragment()).addToBackStack("").commit();


            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_allFragment view_allFragment=new View_allFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frameLayout,new BookingFragment()).addToBackStack("").commit();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_allFragment view_allFragment=new View_allFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frameLayout,new BookingFragment()).addToBackStack("").commit();
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View_allFragment view_allFragment=new View_allFragment();
                FragmentTransaction transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frameLayout,new BookingFragment()).addToBackStack("").commit();
            }
        });

        return view;
    }



}