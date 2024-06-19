package com.example.rescueagency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rescueagency.admin.AdminAddAgencyNextFragment;
import com.example.rescueagency.databinding.FragmentAdminAddAgencyBinding;
import com.example.rescueagency.databinding.FragmentUpdateProfileBinding;

public class UpdateProfileFragment extends Fragment {

    FragmentUpdateProfileBinding binding;
    private String name,mobile,email,address,dob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }
    private  void click(){


        binding.idUpdateProfileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        binding.idUpdateProfileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(getTextField()){
             FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
             ProfileFragment newFragment = new ProfileFragment();

            }
            }
        });


    }
    private boolean getTextField(){
        name = binding.idUpdateProfileNameEText.getText().toString().trim();
        mobile = binding.idUpdateProfileMobileNumberEText.getText().toString().trim();
        email = binding.idUpdateProfileEmailEText.getText().toString().trim();
        address = binding.idUpdateProfileAddressEText.getText().toString().trim();
        dob = binding.idUpdateProfileDOBEText.getText().toString().trim();
        if(name.isEmpty()){
            binding.idUpdateProfileNameEText.setError("Enter Name");
            return false;
        }
        if(mobile.isEmpty()){
            binding.idUpdateProfileMobileNumberEText.setError("Enter Mobile Number");
            return false;
        }
        if(email.isEmpty()){
            binding.idUpdateProfileEmailEText.setError("Enter Email");
            return false;
        }
        if(address.isEmpty()){
            binding.idUpdateProfileAddressEText.setError("Enter Address");
            return false;
        }
        if(dob.isEmpty()){
            binding.idUpdateProfileDOBEText.setError("Enter DOB");
            return false;
        }
        return true;
    }
}