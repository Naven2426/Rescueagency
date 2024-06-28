package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.databinding.FragmentAdminAddCategoryBinding;


public class AdminAddCategoryFragment extends Fragment {
    FragmentAdminAddCategoryBinding binding;

    private String categoryname,categorydescription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(hideAnimation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        binding = FragmentAdminAddCategoryBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }
    public void click(){
        binding.idAddAgencyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getTextField()) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new AdminHomeFragment());
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private boolean getTextField(){
        categoryname = binding.idAddNameET.getText().toString();
        categorydescription = binding.idAddTypeET.getText().toString();
        if(categoryname.isEmpty()){
            binding.idAddNameET.setError("Enter Category Name");
            return false;
        }
        if(categorydescription.isEmpty()){
            binding.idAddTypeET.setError("Enter Category Description");
            return false;
        }
        return true;

    }
}