package com.example.rescueagency.main_menu_fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.notification_fragment.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    AppCompatImageView sos_main_emergency;
    AppCompatImageView notificationButton;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            bottomNavigationView.setVisibility(View.VISIBLE);
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(Animation);


        }

        apiCall(view);


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

                startActivity(new Intent(getContext(), NotificationActivity.class));

            }
            });




        return view;
    }

    private void apiCall(View view) {
        Call<GetCategoryResponse> responseCall = RestClient.makeAPI().getCategory();
        responseCall.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                if (response.isSuccessful()) {
                    GetCategoryResponse imageResponse = response.body();
                    if (imageResponse.getStatus() == 200) {
                        RecyclerView recyclerView=view.findViewById(R.id.viewAllRecyclerView);
                        List<View_all> dataList=new ArrayList<>();
                        for(int i=0;i<imageResponse.getData().size();i++){
                            GetCategoryResponse.Data data = response.body().getData().get(i);
                            dataList.add(new View_all(data.getCategory_name(),data.getImage()));
                        }
                        GridLayoutManager grid=new GridLayoutManager(getContext(),3);
                        recyclerView.setLayoutManager(grid);
                        recyclerView.setAdapter(new View_allHolder(dataList,getActivity()));
                    } else {
                        Toast.makeText(getContext(), imageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}