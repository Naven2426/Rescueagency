package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.databinding.FragmentAdminEditCategoryBinding;


public class AdminEditCategoryFragment extends Fragment {

FragmentAdminEditCategoryBinding binding;

    private String categoryname,categorydescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminEditCategoryBinding.inflate(inflater, container, false);
        clickListeners();
        return binding.getRoot();
    }
    private void clickListeners() {
        binding.idAddAgencyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTextField()) {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new AdminHomeFragment());
                    transaction.commit();
                }
            }
        });
    }

    private boolean getTextField() {
        categoryname = binding.idAddNameET.getText().toString();
        categorydescription = binding.idAddTypeET.getText().toString();
        if (categoryname.isEmpty()) {
            binding.idAddNameET.setError("Please enter category name");
            return false;
        }
        if (categorydescription.isEmpty()) {
            binding.idAddTypeET.setError("Please enter category description");
            return false;
        }
        return true;

    }

}