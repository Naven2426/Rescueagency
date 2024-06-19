package com.example.rescueagency;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentRescueTeamDetailBinding;
import com.example.rescueagency.databinding.FragmentRescueTeamSelcetionInMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import kotlin.jvm.internal.ByteSpreadBuilder;

public class RescueTeamSelcetionInMapFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap mMap;
    LatLng tambaram = new LatLng(12.9249,  80.1000);
    LatLng guindy = new LatLng(13.0067,80.2206);
    FragmentRescueTeamSelcetionInMapBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding= FragmentRescueTeamSelcetionInMapBinding.inflate(inflater,container,false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.my_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                PolylineOptions options = new PolylineOptions();
                options.add(guindy);
                options.add(tambaram);
                options.color(Color.BLUE); // Set your desired color
                options.width(5);
                googleMap.addPolyline(options);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options
//                        MarkerOptions markerOptions=new MarkerOptions();
//                        // Set position of marker
//                        markerOptions.position(latLng);
//                        // Set title of marker
//                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
//                        // Remove all marker
//                        googleMap.clear();
//                        // Animating to zoom the marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                        // Add marker on map
//                        googleMap.addMarker(markerOptions);


                    }
                });
            }
        });
        onClickListener();
        return binding.getRoot();
    }
    private void onClickListener(){
        binding.idMapBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

    }
    public void onMapReady(@NonNull GoogleMap googleMap) {
        PolylineOptions options = new PolylineOptions();
        options.add(guindy);
        options.add(tambaram);
        options.color(Color.BLUE); // Set your desired color
        options.width(5);
        googleMap.addPolyline(options);
    }
}
