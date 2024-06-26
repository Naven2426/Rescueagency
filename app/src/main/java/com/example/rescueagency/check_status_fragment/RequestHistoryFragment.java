package com.example.rescueagency.check_status_fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentRequestHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryFragment extends Fragment {

    private FragmentRequestHistoryBinding binding;
    private Button buttonCheckStatus, buttonHistory;
    private TabItem history,checkStatus;
    List<CheckStatusList> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);
//        binding.checkStatus.setSelected(true);
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
        setupRecyclerView(data);
        tabLayout();
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
    private void tabLayout(){
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    List<CheckStatusList> data = new ArrayList<>();

                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","status"));
                    setupRecyclerView(data);
                }else{
                    List<CheckStatusList> data = new ArrayList<>();
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","history"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","history"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","history"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","history"));
                    data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs","history"));
                    setupRecyclerView(data);
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

    private void setupRecyclerView(List<CheckStatusList> data) {

        CheckStatusListHolder object = new CheckStatusListHolder(data, requireActivity());
        binding.idRequestHistoryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.idRequestHistoryList.setAdapter(object);
    }
}
