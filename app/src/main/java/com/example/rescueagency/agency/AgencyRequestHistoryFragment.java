package com.example.rescueagency.agency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryList;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryListHolder;
import com.example.rescueagency.databinding.FragmentAgencyRequestHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class AgencyRequestHistoryFragment extends Fragment {

FragmentAgencyRequestHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyRequestHistoryBinding.inflate(inflater,container,false);
        recycle();
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();

    }
    private void recycle() {
        List<AgencyReqHistoryList> data = new ArrayList<>();
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));
        data.add(new AgencyReqHistoryList("Status :","dsf","sdf","Sdf","sdf","wefwe","weeerc"));

        binding.idAgencyRequestRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAgencyRequestRV.setAdapter(new AgencyReqHistoryListHolder(data, requireActivity()));
    }

}