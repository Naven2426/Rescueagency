package com.example.rescueagency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.admin.ChooseAgencyLocationFragment;
import com.example.rescueagency.check_status_fragment.RequestHistoryFragment;
import com.example.rescueagency.databinding.FragmentAlertsentBinding;


public class alertsentFragment extends Fragment {

    FragmentAlertsentBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlertsentBinding.inflate(inflater, container, false);
        clickListeners();
        return binding.getRoot();
    }
    private void clickListeners() {
        binding.idAlertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new RequestHistoryFragment());
                transaction.commit();
            }
        });
    }
}