package com.example.rescueagency.agency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.databinding.FragmentAgencySOSRequestDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AgencySOSRequestDetailFragment extends Fragment {

    FragmentAgencySOSRequestDetailBinding binding;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAgencySOSRequestDetailBinding.inflate(inflater, container, false);
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void click() {

        binding.idAgencySOSReqDetailBackButIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }

        });

        binding.idAgencySOSReqAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyRequestHistoryFragment());
                transaction.commit();
            }
        });

        binding.idAgencySOSReqRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyRequestHistoryFragment());
                transaction.commit();
            }
        });




    }


}