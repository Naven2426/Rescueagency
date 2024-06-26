package com.example.rescueagency.agency;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.ProfileFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyLeaderProfileViewBinding;


public class AgencyLeaderProfileViewFragment extends Fragment {

  FragmentAgencyLeaderProfileViewBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyLeaderProfileViewBinding.inflate(inflater, container, false);
        clickListeners();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }
    private void clickListeners() {
        binding.idAdminAgencyLeaderDetailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
            });


        binding.idAdminAgencyInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyLocationDetailsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.idProfileUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyLeaderProfileUpdateFragment());
                transaction.addToBackStack("AgencyLeaderProfileUpdateFragment");
                transaction.commit();

            }
        });


    }


}