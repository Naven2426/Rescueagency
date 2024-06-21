package com.example.rescueagency.agency.agency_profile_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.Constant;
import com.example.rescueagency.LoginActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyProfileBinding;

import java.util.ArrayList;
import java.util.List;


public class AgencyProfileFragment extends Fragment {

    FragmentAgencyProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyProfileBinding.inflate(inflater, container, false);
        logout();
        recycle();
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
    private void recycle() {
        List<AgencyProfileList> data = new ArrayList<>();
        data.add(new AgencyProfileList("df", "df"));
        data.add(new AgencyProfileList("df", "df"));
        data.add(new AgencyProfileList("df", "df"));
        data.add(new AgencyProfileList("df", "df"));
        data.add(new AgencyProfileList("df", "df"));
        AgencyProfileListHolder adapter = new AgencyProfileListHolder(data,requireActivity());
        binding.idAgencyMemberRecyclerView.setAdapter(adapter);
        binding.idAgencyMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}