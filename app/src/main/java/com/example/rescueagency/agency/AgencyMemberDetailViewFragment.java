package com.example.rescueagency.agency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyMemberDetailViewBinding;


public class AgencyMemberDetailViewFragment extends Fragment {
    FragmentAgencyMemberDetailViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyMemberDetailViewBinding.inflate(inflater, container, false);
        clickListener();
        return binding.getRoot();
    }
    private void clickListener() {
        binding.idAgencyMemberDetailViewBackButton.setOnClickListener(v -> getActivity().onBackPressed());
    }
}