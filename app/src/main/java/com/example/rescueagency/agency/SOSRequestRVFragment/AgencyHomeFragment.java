package com.example.rescueagency.agency.SOSRequestRVFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyEmergencyReqListFragment;
import com.example.rescueagency.agency.AgencyEmergencyRequestDetailFragment;
import com.example.rescueagency.agency.AgencySOSRequestDetailFragment;
import com.example.rescueagency.alertsentFragment;
import com.example.rescueagency.databinding.FragmentAgencyHomeBinding;


public class AgencyHomeFragment extends Fragment {

FragmentAgencyHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyHomeBinding.inflate(inflater, container, false);
        clickListener();
        return binding.getRoot();
    }
    private void clickListener(){
        binding.idAgencySosReqViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new AgencySosRequestListFragment()).addToBackStack("AgencySosRequestListFragment").commit();
            }
        });

        binding.idAgencyEmergencyReqViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new AgencyEmergencyReqListFragment()).addToBackStack("AgencyEmergencyReqListFragment").commit();
            }

        });

        binding.idAgencyEmergencyReqInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new AgencyEmergencyRequestDetailFragment()).addToBackStack("AgencyEmergencyRequestDetailFragment").commit();
            }
        });

        binding.idAgencySosReqInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new AgencySOSRequestDetailFragment()).addToBackStack("AgencySosRequestDetailFragment").commit();
            }
        });
    }
}