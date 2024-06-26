package com.example.rescueagency.agency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.HomeFragment.AdminAgencyListHolder;
import com.example.rescueagency.agency.MessageList.AgencyMessageList;
import com.example.rescueagency.agency.MessageList.AgencyMessageListHolder;
import com.example.rescueagency.databinding.FragmentAgencyNotificationBinding;

import java.util.ArrayList;
import java.util.List;


public class AgencyNotificationFragment extends Fragment {

FragmentAgencyNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyNotificationBinding.inflate(inflater, container, false);
        click();
        recycleView();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }
    private void click(){
        binding.idAgencyNotificationBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }
    private void recycleView(){
        List<AgencyMessageList> data = new ArrayList<>();
        data.add(new AgencyMessageList("dsjk","jdn","jhdbsf"));
        data.add(new AgencyMessageList("dsjk","jdn","jhdbsf"));
        data.add(new AgencyMessageList("dsjk","jdn","jhdbsf"));
        data.add(new AgencyMessageList("dsjk","jdn","jhdbsf"));
        data.add(new AgencyMessageList("dsjk","jdn","jhdbsf"));


        binding.idAgencyNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAgencyNotificationRecyclerView.setAdapter(new AgencyMessageListHolder(data,requireActivity()));


    }
}