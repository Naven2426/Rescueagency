package com.example.rescueagency.agency;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.agencymemberinfo.MemberDetails;
import com.example.rescueagency.databinding.FragmentAgencyMemberDetailPreviewBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyMemberDetailPreviewFragment extends Fragment {

    FragmentAgencyMemberDetailPreviewBinding binding;
    private String name, email, phone, address, dob, role, year;
    SharedPreferences sf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgencyMemberDetailPreviewBinding.inflate(inflater, container, false);
        retrieveData();
        click();
        sf=requireActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    private void retrieveData() {
        if (getArguments() != null) {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
            address = getArguments().getString("address");
            dob = getArguments().getString("dob");
            role = getArguments().getString("role");
            year = getArguments().getString("year");

            binding.idAgentProfileNameTV.setText(name);
            binding.idProfileEmailTV.setText(email);
            binding.idProfileMobileTV.setText(phone);
            binding.idProfileAddressTV.setText(address);
            binding.idProfileDOBTV.setText(dob);
            binding.idProfileRollTV.setText(role);
            binding.idProfileYOETV2.setText(year);
        }
    }

    private void click() {
        binding.idAgencyAddMemberNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicall(name, email, phone, address, dob, role, year);
            }
        });

        binding.idAgencyMemberDetailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }

    private void apicall(String name, String email, String phone, String address, String dob, String role, String year) {
        MemberDetails memberDetails = new MemberDetails();  // Assuming MemberDetails constructor matches these parameters
        memberDetails.setName(name);
        String userId = sf.getString(Constant.SF_USERID,null);
        memberDetails.setAgent_id(userId);
        memberDetails.setEmail(email);
        memberDetails.setMobile(phone);
        memberDetails.setAddress(address);
        memberDetails.setDob(dob);
        memberDetails.setRole(role);
        memberDetails.setYear_of_experience(year);

        Call<SignUpResponse> responseCall = RestClient.makeAPI().addMember(memberDetails);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse memberDetailsResponse = response.body();
                    assert memberDetailsResponse != null;
                    if (memberDetailsResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), memberDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager transaction = requireActivity().getSupportFragmentManager();
                        transaction.popBackStack();
                    } else {
                        Toast.makeText(getContext(), memberDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
