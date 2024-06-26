package com.example.rescueagency.agency;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.ProfileFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.databinding.FragmentAgencyLeaderProfileUpdateBinding;

import java.util.Calendar;


public class AgencyLeaderProfileUpdateFragment extends Fragment {

    FragmentAgencyLeaderProfileUpdateBinding binding;
    private String name, email, phone, address,dob,role,year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyLeaderProfileUpdateBinding.inflate(inflater, container, false);
        clickListeners();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void clickListeners() {
        binding.idAgentLeaderProfileBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        binding.idAgentLeaderProfileUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyLeaderProfileUpdateFragment());
                transaction.addToBackStack("AgencyLeaderProfileUpdateFragment");
                transaction.commit();

            }
        });

        binding.idAgentLeaderProfileUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getTextField()){
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new AgencyProfileFragment());
                    transaction.commit();

                }
            }
        });

        binding.idEdittextAgentDobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding.idEdittextAgentDobText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

    }
    private boolean getTextField(){
        name = binding.idEdittextAgentNameText.getText().toString().trim();
        phone = binding.idEdittextAgentMobileText.getText().toString().trim();
        email = binding.idEdittextAgentEmailText.getText().toString().trim();
        address = binding.idEdittextAgentAddressText.getText().toString().trim();
        dob = binding.idEdittextAgentDobText.getText().toString().trim();
        role = binding.idEdittextAgentRoleText.getText().toString().trim();
        year = binding.idEdittextAgentYoeText.getText().toString().trim();

        if(name.isEmpty()){
            binding.idEdittextAgentNameText.setError("Enter Name");
            return false;
        }
        if(phone.isEmpty()){
            binding.idEdittextAgentMobileText.setError("Enter Mobile Number");
            return false;
        }
        if(email.isEmpty()){
            binding.idEdittextAgentEmailText.setError("Enter Email");
            return false;
        }
        if(address.isEmpty()){
            binding.idEdittextAgentAddressText.setError("Enter Address");
            return false;
        }
        if(dob.isEmpty()){
            binding.idEdittextAgentDobText.setError("Enter DOB");
            return false;
        }
        if(role.isEmpty()){
            binding.idEdittextAgentRoleText.setError("Enter Role");
            return false;
        }
        if(year.isEmpty()){
            binding.idEdittextAgentYoeText.setError("Enter Year");
            return false;
        }
        return true;
    }
}