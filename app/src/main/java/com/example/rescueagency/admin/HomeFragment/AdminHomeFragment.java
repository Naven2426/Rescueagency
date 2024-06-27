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

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
//        toogle();
        recyclerview();MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }

//    private void toogle() {
//        binding.idAdminAgencyToggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
//            @Override
//            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
//                if (isChecked) {
//                    if (checkedId == R.id.idAdminSelAgency) {
//                        showToast("Robot that looks like human.");
//                    } else if (checkedId == R.id.idAdminSelUser) {
//                        showToast("It's a Butterfly thing.");
//                    }
//                } else {
//                    showToast("Nothing Selected");
//                }
//            }
//        });
//    }

    private void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void recyclerview() {
        List<AdminAgencyViewList> data = new ArrayList<>();
        data.add(new AdminAgencyViewList("Agency1", "10 rating", "Location1", "100000", "Description1"));
        data.add(new AdminAgencyViewList("Agency2", "9 rating", "Location2", "90000", "Description2"));
        data.add(new AdminAgencyViewList("Agency3", "8 rating", "Location3", "80000", "Description3"));
        data.add(new AdminAgencyViewList("Agency4", "7 rating", "Location4", "70000", "Description4"));
        data.add(new AdminAgencyViewList("Agency5", "6 rating", "Location5", "60000", "Description5"));
        data.add(new AdminAgencyViewList("Agency6", "5 rating", "Location6", "50000", "Description6"));
        data.add(new AdminAgencyViewList("Agency7", "4 rating", "Location7", "40000", "Description7"));

        // Add more data as needed

        binding.idAdminAgencyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAdminAgencyRecyclerView.setAdapter(new AdminAgencyListHolder(data, requireActivity()));
    }
}
