package com.example.rescueagency.agency;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.Constant;
import com.example.rescueagency.LoginActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyProfileBinding;


public class AgencyProfileFragment extends Fragment {

    FragmentAgencyProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyProfileBinding.inflate(inflater, container, false);
        logout();
        return binding.getRoot();
    }
    public void logout(){
        binding.AgencyLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                sf.edit().clear().apply();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }
}