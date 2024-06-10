package com.example.rescueagency.user_agency_member_list_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.databinding.FragmentRescueTeamDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class RescueTeamDetailFragment extends Fragment {

    FragmentRescueTeamDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRescueTeamDetailBinding.inflate(inflater, container, false);
        rever();
        recyclerView();
        return binding.getRoot();

    }

    public void rever() {
        binding.idRescueTeamViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

    }
    private void recyclerView(){
        List<user_rescue_team_member_list> data=new ArrayList<>();
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        data.add(new user_rescue_team_member_list("5","dfa","asdf","asdfa"));
        binding.idRescueTeamRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idRescueTeamRecyclerView.setAdapter(new UserRescueTeamMemberListHolder(data,requireActivity()));
    }
}