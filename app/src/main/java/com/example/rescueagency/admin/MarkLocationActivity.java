package com.example.rescueagency.admin;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.google.android.gms.maps.CameraUpdate;
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

        sharedPreferences = getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location != null) {
                    googleMap.addMarker(new MarkerOptions().position(location));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
                    Toast.makeText(MarkLocationActivity.this, "lat " + location.latitude + " long " + location.longitude, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.SF_LATITUDE, "" + location.latitude);
                    editor.putString(Constant.SF_LONGITUDE, "" + location.longitude);
                    editor.apply();
                    finish();
                } else {
                    Toast.makeText(MarkLocationActivity.this, "Mark Location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.idMarkLocationBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void moveCamera(Location location) {
        if (location != null) {
            LatLng coordinate = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate myLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
            mMap.animateCamera(myLocation);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                location = latLng;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
//                  binding.idChooseYourTeam.setText(""+location.getLatitude()+" "+location.getLongitude());
                moveCamera(location);
                Log.e("TAG", "onMyLocationClick: " + location.getLatitude() + " " + location.getLongitude());
            }
        });
    }
}