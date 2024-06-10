package com.example.rescueagency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.databinding.FragmentMapsBinding;
import com.example.rescueagency.user_agency_member_list_view.RescueTeamDetailFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    FragmentTransaction transaction=requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout,new RescueTeamDetailFragment()).addToBackStack("RescueTeamDetailFragment")
                                    .commit();
                    return false;
                }
            });

        }
    };
    FragmentMapsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater,container,false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        Onclick();
        return binding.getRoot();
    }

  public void Onclick(){
        binding.idMapBackReqBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
  }

}