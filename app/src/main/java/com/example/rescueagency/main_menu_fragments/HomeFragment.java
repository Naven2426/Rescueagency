package com.example.rescueagency.main_menu_fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.map.getcurrentlocation.CurrentLocationRootClass;
import com.example.rescueagency.apiresponse.map.getcurrentlocation.Results;
import com.example.rescueagency.notification_fragment.NotificationActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    AppCompatImageView sos_main_emergency;
    AppCompatImageView notificationButton;
    TextView currentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

//        getCurrentLocation();

        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView.getVisibility() == View.GONE) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(Animation);
        }
        apiCall(view);
        currentLocation = view.findViewById(R.id.myLocationTV);
        sos_main_emergency = view.findViewById(R.id.id_sos_alert_img);
        notificationButton = view.findViewById(R.id.GfG_full_loo);
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

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    String latLang;
    private void getCurrentLocation() {
//        Toast.makeText(requireContext(), "getcurrentlocation", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(requireContext(), "Can't Access Your Location", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(!isLocationEnabled()){
            Toast.makeText(requireContext(), "Enable Your Location", Toast.LENGTH_SHORT).show();
            return ;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            latLang = latitude + "," + longitude;
                            getCurrentLocation(latLang);
                        }
                    }
                });
//        return latLang;
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
                            dataList.add(new View_all(data.getCategory_name(),data.getImage(),data.getCategory_id()));
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
    private void getCurrentLocation(String latLang){
        Call<CurrentLocationRootClass> responseCall=RestClient.makeMapAPI().getCurrentLocation(latLang, Constant.KEY);
        responseCall.enqueue(new Callback<CurrentLocationRootClass>() {
            @Override
            public void onResponse(@NonNull Call<CurrentLocationRootClass> call, @NonNull Response<CurrentLocationRootClass> response) {
                if(response.isSuccessful()){
                    CurrentLocationRootClass main=response.body();
                    assert main != null;
                    if(main.getStatus().equalsIgnoreCase("OK") || !main.getResults().isEmpty()){
                        Results results=main.getResults().get(0);
                        Toast.makeText(requireContext(), "Google "+main.getStatus(), Toast.LENGTH_SHORT).show();
                        currentLocation.setText(results.getFormatted_address());
                    }else{
                        Toast.makeText(requireContext(), main.getError_message(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentLocationRootClass> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }


}