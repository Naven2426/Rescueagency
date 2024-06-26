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
import com.example.rescueagency.R;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.databinding.FragmentAgencyMemberProfileUpdateBinding;

import java.util.Calendar;


public class AgencyMemberProfileUpdateFragment extends Fragment {

    FragmentAgencyMemberProfileUpdateBinding binding;

    private String name, email, phone, address,dob,role,year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyMemberProfileUpdateBinding.inflate(inflater, container, false);
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void click() {
        binding.idAgencyUpdateProfileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();

            }

        });

        binding.idAgencyMemberUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getTextField()){
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new AgencyProfileFragment());
                    transaction.commit();

                }
            }
        });

        binding.idAgencyMemberUpdateProfileDob.setOnClickListener(new View.OnClickListener() {
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
                                binding.idAgencyMemberUpdateProfileDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private boolean getTextField(){
        name = binding.idAgencyMemberUpdateProfileName.getText().toString().trim();
        phone = binding.idAgencyMemberUpdateProfileMobile.getText().toString().trim();
        email = binding.idAgencyMemberUpdateProfileEmail.getText().toString().trim();
        address = binding.idAgencyMemberUpdateProfileAddress.getText().toString().trim();
        dob = binding.idAgencyMemberUpdateProfileDob.getText().toString().trim();
        role = binding.idAgencyMemberUpdateProfileRole.getText().toString().trim();
        year = binding.idAgencyMemberUpdateProfileYoe.getText().toString().trim();

        if(name.isEmpty()){
            binding.idAgencyMemberUpdateProfileName.setError("Enter Name");
            return false;
        }
        if(phone.isEmpty()){
            binding.idAgencyMemberUpdateProfileMobile.setError("Enter Mobile Number");
            return false;
        }
        if(email.isEmpty()){
            binding.idAgencyMemberUpdateProfileEmail.setError("Enter Email");
            return false;
        }
        if(address.isEmpty()){
            binding.idAgencyMemberUpdateProfileAddress.setError("Enter Address");
            return false;
        }
        if(dob.isEmpty()){
            binding.idAgencyMemberUpdateProfileDob.setError("Enter DOB");
            return false;
        }
        if(role.isEmpty()){
            binding.idAgencyMemberUpdateProfileRole.setError("Enter Role");
            return false;
        }
        if(year.isEmpty()){
            binding.idAgencyMemberUpdateProfileYoe.setError("Enter Year");
            return false;
        }
        return true;
    }
}