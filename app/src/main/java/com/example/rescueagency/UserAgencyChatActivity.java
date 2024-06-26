package com.example.rescueagency;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.databinding.ActivityUserAgencyChatBinding;


public class UserAgencyChatActivity extends AppCompatActivity {

    ActivityUserAgencyChatBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityUserAgencyChatBinding.inflate(getLayoutInflater());
        click();
        setContentView(binding.getRoot());
//        return binding.getRoot();
    }



    private void click() {
        binding.idChatBackButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }
}