package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.admin.AdminAgencyViewDetailList.AdminAgencyViewDetailList;
import com.example.rescueagency.admin.AdminAgencyViewDetailList.AdminAgencyViewDetailListHolder;
import com.example.rescueagency.admin.HomeFragment.AdminAgencyListHolder;
import com.example.rescueagency.admin.HomeFragment.AdminAgencyViewList;
import com.example.rescueagency.agency.AgencyReviewFragment;
import com.example.rescueagency.databinding.FragmentAdminAgencyDetailViewBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminAgencyDetailViewFragment extends Fragment {

FragmentAdminAgencyDetailViewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAgencyDetailViewBinding.inflate(inflater,container,false);
        click();
        recyclerView();
        return binding.getRoot();
    }
    private void click() {
        binding.idAdminAgencyBackArrowIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        binding.idAdminAgentProfileImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AdminAgencyLeaderProfileFragment());
                transaction.addToBackStack("AdminAgencyLeaderProfileFragment").commit();
            }
        });

        binding.idAdminAgencyInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AdminAgencyLocationDetailFragment());
                transaction.addToBackStack("AdminAgencyLocationDetailFragment").commit();
            }
        });

        binding.idAgencyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout,new AgencyReviewFragment());
                transaction.addToBackStack("AgencyReviewFragment").commit();
            }
        });

    }

    private void recyclerView() {
        List<AdminAgencyViewDetailList> data = new ArrayList<>();
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));
        data.add(new AdminAgencyViewDetailList("Agency1", "10 rating"));

        // Add more data as needed

        binding.idAdminAgencyMemberRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.idAdminAgencyMemberRecyclerView.setAdapter(new AdminAgencyViewDetailListHolder(data, requireActivity()));
    }

}