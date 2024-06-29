package com.example.rescueagency.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentMarkLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkLocationFragment extends Fragment {

    SharedPreferences sharedPreferences;
    LatLng location;
    FragmentMarkLocationBinding binding;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    location=latLng;
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMarkLocationBinding.inflate(inflater,container,false);
        SupportMapFragment mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        sharedPreferences=requireContext().getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        AppCompatButton button=binding.confirm;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location!=null){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(Constant.SF_LATITUDE,""+location.latitude);
                    editor.putString(Constant.SF_LONGITUDE,""+location.longitude);

                    editor.apply();
                    FragmentManager manager=requireActivity().getSupportFragmentManager();
                    manager.popBackStack();
                }else{
                    Toast.makeText(requireContext(), "Mark Location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }

}