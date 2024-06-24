package com.example.rescueagency.check_status_fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentRequestHistoryBinding;
import java.util.ArrayList;
import java.util.List;

public class RequestHistoryFragment extends Fragment {

    private FragmentRequestHistoryBinding binding;
    private Button buttonCheckStatus, buttonHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);

        setupRecyclerView();
        return binding.getRoot();
    }

//    private void setupToggleButtons() {
//        buttonCheckStatus = binding.getRoot().findViewById(R.id.idAdminSelAgency);
//        buttonHistory = binding.getRoot().findViewById(R.id.idAdminSelUser);
//
//        // Default selection
//        buttonCheckStatus.setSelected(true);
//        buttonCheckStatus.setBackgroundResource(R.drawable.toggle_checked);
//        buttonHistory.setBackgroundResource(R.drawable.toggle_unchecked);
//
//        buttonCheckStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCheckStatus.setSelected(true);
//                buttonHistory.setSelected(false);
//                buttonCheckStatus.setBackgroundResource(R.drawable.toggle_checked);
//                buttonHistory.setBackgroundResource(R.drawable.toggle_unchecked);
//            }
//        });
//
//        buttonHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonCheckStatus.setSelected(false);
//                buttonHistory.setSelected(true);
//                buttonCheckStatus.setBackgroundResource(R.drawable.toggle_unchecked);
//                buttonHistory.setBackgroundResource(R.drawable.toggle_checked);
//            }
//        });
//    }

    private void setupRecyclerView() {
        List<CheckStatusList> data = new ArrayList<>();
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs"));
        data.add(new CheckStatusList("bsd","Solved","dsfc","sddsf","dfsdf","8287","sdfs"));
        CheckStatusListHolder object = new CheckStatusListHolder(data, requireActivity());
        binding.idRequestHistoryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.idRequestHistoryList.setAdapter(object);
    }
}
