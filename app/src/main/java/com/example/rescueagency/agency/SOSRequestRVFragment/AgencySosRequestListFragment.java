package com.example.rescueagency.agency.SOSRequestRVFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencySosRequestListBinding;


public class AgencySosRequestListFragment extends Fragment {

FragmentAgencySosRequestListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencySosRequestListBinding.inflate(inflater, container, false);
        Click();
        return binding.getRoot();
    }
    private void Click(){
        binding.idSOSReqBackArrowIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

    }
}