package com.example.rescueagency.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.rescueagency.databinding.ActivityMarkLocationBinding;

public class MarkLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMarkLocationBinding binding;
    private LatLng location;
    private SharedPreferences sharedPreferences;
    static GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMarkLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sharedPreferences=getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location!=null){
                    googleMap.addMarker(new MarkerOptions().position(location));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14));
                    Toast.makeText(MarkLocationActivity.this, "lat "+location.latitude+" long "+location.longitude, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Constant.SF_LATITUDE,""+location.latitude);
                    editor.putString(Constant.SF_LONGITUDE,""+location.longitude);
                    editor.apply();
                    finish();
                }else{
                    Toast.makeText(MarkLocationActivity.this, "Mark Location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                location=latLng;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }
}