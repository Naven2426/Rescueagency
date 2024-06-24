package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAdminAgencyLeaderProfileBinding;


public class AdminAgencyLeaderProfileFragment extends Fragment {

    FragmentAdminAgencyLeaderProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAgencyLeaderProfileBinding.inflate(inflater,container,false);
        click();
        return binding.getRoot();

    }
    private void click() {
        binding.idAdminAgencyLeaderDetailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }

        });

    }


}