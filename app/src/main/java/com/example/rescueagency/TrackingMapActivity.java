package com.example.rescueagency;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.view.View;

import com.example.rescueagency.databinding.ActivityTrackingMapBinding;


public class TrackingMapActivity extends AppCompatActivity {

    ActivityTrackingMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityTrackingMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        click();
//        return binding.getRoot();
    }

    private void click(){
        binding.idTrackingMapBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

            });

    }
}