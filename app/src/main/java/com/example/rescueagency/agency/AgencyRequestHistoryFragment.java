package com.example.rescueagency.agency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.ContextCompat;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryList;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryListHolder;
import com.example.rescueagency.databinding.FragmentAgencyRequestHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AgencyRequestHistoryFragment extends Fragment {

    FragmentAgencyRequestHistoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgencyRequestHistoryBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupTabLayout();
        MainActivity mainActivity = (MainActivity) getActivity();
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        List<AgencyReqHistoryList> data = new ArrayList<>();
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf", "sdf", "wefwe", "weeerc"));

        binding.idAgencyRequestRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAgencyRequestRV.setAdapter(new AgencyReqHistoryListHolder(data, requireActivity()));
    }

    private void setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) { // "Rejected" tab position
                    tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                } else {
                    tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle reselection if needed
            }
        });
    }
}
