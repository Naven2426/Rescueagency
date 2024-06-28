package com.example.rescueagency.admin.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAdminHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        List<AdminAgencyViewList> data = new ArrayList<>();
        data.add(new AdminAgencyViewList("Agency1", "10 rating", "Location1", "100000", "Description1"));
        data.add(new AdminAgencyViewList("Agency2", "9 rating", "Location2", "90000", "Description2"));
        data.add(new AdminAgencyViewList("Agency3", "8 rating", "Location3", "80000", "Description3"));
        data.add(new AdminAgencyViewList("Agency4", "7 rating", "Location4", "70000", "Description4"));
        data.add(new AdminAgencyViewList("Agency5", "6 rating", "Location5", "60000", "Description5"));
        data.add(new AdminAgencyViewList("Agency6", "5 rating", "Location6", "50000", "Description6"));
        data.add(new AdminAgencyViewList("Agency7", "4 rating", "Location7", "40000", "Description7"));
        recycle(data);
        tabLayout();
        return binding.getRoot();
    }





    private void tabLayout() {

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    List<AdminAgencyViewList> data = new ArrayList<>();
                    data.add(new AdminAgencyViewList("Agency1", "10 rating", "Location1", "100000", "Description1"));
                    data.add(new AdminAgencyViewList("Agency2", "9 rating", "Location2", "90000", "Description2"));
                    data.add(new AdminAgencyViewList("Agency3", "8 rating", "Location3", "80000", "Description3"));
                    data.add(new AdminAgencyViewList("Agency4", "7 rating", "Location4", "70000", "Description4"));
                    data.add(new AdminAgencyViewList("Agency5", "6 rating", "Location5", "60000", "Description5"));
                    data.add(new AdminAgencyViewList("Agency6", "5 rating", "Location6", "50000", "Description6"));
                    data.add(new AdminAgencyViewList("Agency7", "4 rating", "Location7", "40000", "Description7"));

                    binding.idAdminAgencyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.idAdminAgencyRecyclerView.setAdapter(new AdminAgencyListHolder(data, requireActivity()));


                } else if (tab.getPosition() == 1) {
                    List<AdminUserViewList> data = new ArrayList<>();
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));
                    data.add(new AdminUserViewList("User1", "10 rating", "Location1"));

                    binding.idAdminAgencyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.idAdminAgencyRecyclerView.setAdapter(new AdminUserListHolder(data, requireActivity()));

                } else {
                    List<AdminCategoryList> data = new ArrayList<>();
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));
                    data.add(new AdminCategoryList("Category1", "10 rating", "Location1"));

                    binding.idAdminAgencyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.idAdminAgencyRecyclerView.setAdapter(new AdminCategoryListHolder(data, requireActivity(),binding.idAdminAgencyRecyclerView));
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }







        });
    }
    private void recycle(List<AdminAgencyViewList> data) {
        binding.idAdminAgencyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAdminAgencyRecyclerView.setAdapter(new AdminAgencyListHolder(data, requireActivity()));
    }
}


