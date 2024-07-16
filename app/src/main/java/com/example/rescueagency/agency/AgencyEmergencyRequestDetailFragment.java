package com.example.rescueagency.agency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.SOSRequestRVFragment.FindRouteMapsActivity;
import com.example.rescueagency.apiresponse.CommonResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Agent;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.GetNewEmergencyRequestRootClass;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.IncidentInformation;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Result;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.User;
import com.example.rescueagency.databinding.FragmentAgencyEmergencyRequestDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyEmergencyRequestDetailFragment extends Fragment {

    FragmentAgencyEmergencyRequestDetailBinding binding;
    Double latitude, longitude;
    String requestId;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            Toast.makeText(requireContext(), "Map Synced", Toast.LENGTH_SHORT).show();
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    if(latitude!=null && longitude!=null){
                        Intent intent = new Intent(getContext(), FindRouteMapsActivity.class);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }else{
                        Toast.makeText(requireContext(), "Latitude Or Longitude is Null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgencyEmergencyRequestDetailBinding.inflate(inflater, container, false);
        Click();
         requestId = getArguments().getString("requestId");
        getEmergencyAlertMessage(requestId);
        SupportMapFragment mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void Click() {
        binding.idAgencyEmergencyReqDetailBackButIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
        binding.acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusApi(requestId,"ONGOING");
            }
        });
    }
    private void updateStatusApi(String requestId,String status){
        Call<CommonResponse> responseCall = RestClient.makeAPI().updateRequest(requestId,status);
        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
    private void getEmergencyAlertMessage(String requestId){
        Call<GetNewEmergencyRequestRootClass> responseCall= RestClient.makeAPI().getRequestInfo(requestId);
        responseCall.enqueue(new Callback<GetNewEmergencyRequestRootClass>() {
            @Override
            public void onResponse(@NonNull Call<GetNewEmergencyRequestRootClass> call, @NonNull Response<GetNewEmergencyRequestRootClass> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus()==200){
                        Result result = response.body().getResult();
                        User user = result.getUser();
                        IncidentInformation info = result.getIncident_information();
                        Agent agent = result.getAgent();
                        latitude = info.getLatitude();
                        longitude = info.getLongitude();
                        binding.idAgencyEmergencyReqDetailName.setText(user.getUser_name());
                        binding.idAgencyEmergencyReqDetailDateTV.setText(info.getDate());
                        binding.idAgencyEmergencyReqDetailDescribe.setText(info.getDescribe_incident());
                        try {
                            binding.showIncidentImages.setAdapter(new ImagePreviewAdapter(info.getIncident_images(), requireContext()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(requireContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "Error "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNewEmergencyRequestRootClass> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static class ImagePreviewAdapter extends PagerAdapter {

        List<String> uris;
        Context context;
        LayoutInflater mLayoutInflater;

        public ImagePreviewAdapter(List<String> images, Context context) {
            this.uris = images;
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return uris.size();
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            // inflating the item.xml
            View itemView = mLayoutInflater.inflate(R.layout.image_preview_layout, container, false);

            // referencing the image view from the item.xml file
            ImageView imageView = itemView.findViewById(R.id.imagePreview);

            // setting the image in the imageView
            Glide.with(context).load(uris.get(position)).into(imageView);

            // Adding the View
            Objects.requireNonNull(container).addView(itemView);

            return itemView;
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((LinearLayout) object);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

            container.removeView((LinearLayout) object);
        }
    }


}