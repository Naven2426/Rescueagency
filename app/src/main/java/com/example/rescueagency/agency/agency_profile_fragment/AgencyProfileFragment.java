package com.example.rescueagency.agency.agency_profile_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.Constant;
import com.example.rescueagency.LoginActivity;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyAddMemberFragment;
import com.example.rescueagency.agency.AgencyLeaderProfileViewFragment;
import com.example.rescueagency.agency.AgencyReviewFragment;
import com.example.rescueagency.databinding.FragmentAgencyProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class AgencyProfileFragment extends Fragment {

    FragmentAgencyProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyProfileBinding.inflate(inflater, container, false);
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        logout();
        recycle();
        leader();
        return binding.getRoot();
    }
    public void logout(){
        binding.idLogoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                sf.edit().clear().apply();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }

    private void leader() {
        binding.idAgentProfileImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyLeaderProfileViewFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.idAgencyAddMemberCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyAddMemberFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.idAgencyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyReviewFragment());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }
    private void recycle() {
        List<AgencyProfileList> data = new ArrayList<>();
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        data.add(new AgencyProfileList("Tobi", "df"));
        AgencyProfileListHolder adapter = new AgencyProfileListHolder(data,requireActivity());
        binding.idAgencyMemberRecyclerView.setAdapter(adapter);
        binding.idAgencyMemberRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
}