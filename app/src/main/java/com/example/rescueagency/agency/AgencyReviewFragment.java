package com.example.rescueagency.agency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.AdminAgencyViewDetailList.AdminAgencyViewDetailList;
import com.example.rescueagency.admin.AdminAgencyViewDetailList.AdminAgencyViewDetailListHolder;
import com.example.rescueagency.agency.agency_review_list.AgencyReviewList;
import com.example.rescueagency.agency.agency_review_list.AgencyReviewListHolder;
import com.example.rescueagency.databinding.FragmentAgencyReviewBinding;

import java.util.ArrayList;
import java.util.List;


public class AgencyReviewFragment extends Fragment {

    FragmentAgencyReviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyReviewBinding.inflate(inflater, container, false);
        recycleView();
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void click(){
        binding.idAgencyReviewBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
                }
        });
    }

    private void recycleView(){
        List<AgencyReviewList> data = new ArrayList<>();

        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));
        data.add(new AgencyReviewList("hgv","jhv","ghdv","dce","ece"));

        binding.idAgencyReviewRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAgencyReviewRecyclerView.setAdapter(new AgencyReviewListHolder(data, requireActivity()));

    }
}