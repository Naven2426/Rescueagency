package com.example.rescueagency.agency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.databinding.FragmentAgencyMemberDetailViewBinding;


public class AgencyMemberDetailViewFragment extends Fragment {
    FragmentAgencyMemberDetailViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyMemberDetailViewBinding.inflate(inflater, container, false);
        clickListener();
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }
    private void clickListener() {
        binding.idAgencyMemberDetailViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
        binding.idAgencyProfileViewEditUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyMemberProfileUpdateFragment());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        binding.idAgencyMemberDetailRemoveMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyProfileFragment());
                transaction.commit();
            }
        });
    }
}