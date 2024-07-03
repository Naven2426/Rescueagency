package com.example.rescueagency.agency;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rescueagency.Constant;
import com.example.rescueagency.FileUtils;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.agencymemberinfo.MemberDetails;
import com.example.rescueagency.databinding.FragmentAgencyMemberDetailPreviewBinding;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyMemberDetailPreviewFragment extends Fragment {

    private FragmentAgencyMemberDetailPreviewBinding binding;
    private String agencyId,name, email, phone, address, dob, role, year;
    private RequestBody agencyIDBody, nameBody, emailBody, phoneBody, addressBody, dobBody, roleBody, yearBody;
    private ArrayList<Uri> uriImages;
    private MemberDetails memberDetails;
    private SharedPreferences sf;
    MultipartBody.Part part;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgencyMemberDetailPreviewBinding.inflate(inflater, container, false);
        retrieveData();
        loadImage();
        click();
//        sf = requireActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    private void retrieveData() {
        if (getArguments() != null) {
            sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
            agencyId = sf.getString(Constant.SF_USERID,null);
//            agencyId = sf.getString(Constant.SF_USERID,null);
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
            address = getArguments().getString("address");
            dob = getArguments().getString("dob");
            role = getArguments().getString("role");
            year = getArguments().getString("year");
            uriImages = getArguments().getParcelableArrayList("uriImages");

            binding.idAgentProfileNameTV.setText(name);
            binding.idProfileEmailTV.setText(email);
            binding.idProfileMobileTV.setText(phone);
            binding.idProfileAddressTV.setText(address);
            binding.idProfileDOBTV.setText(dob);
            binding.idProfileRollTV.setText(role);
            binding.idProfileYOETV2.setText(year);


            agencyIDBody = RequestBody.create(MediaType.parse("text/plain"),agencyId);
            nameBody = RequestBody.create(MultipartBody.FORM,name);
            emailBody = RequestBody.create(MultipartBody.FORM,email);
            phoneBody = RequestBody.create(MultipartBody.FORM,phone);
            addressBody = RequestBody.create(MultipartBody.FORM,address);
            dobBody = RequestBody.create(MultipartBody.FORM,dob);
            roleBody = RequestBody.create(MultipartBody.FORM,role);
            yearBody = RequestBody.create(MultipartBody.FORM,year);

            if (uriImages != null && !uriImages.isEmpty()) {
                // Load first image for preview
                String imagePath = FileUtils.getPath(requireContext(),uriImages.get(0));
                File file = new File(imagePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            } else {
                part = null; // or handle accordingly if there are no images
            }



        }
    }

    private void loadImage() {
        if (uriImages != null && !uriImages.isEmpty()) {
            // Load first image for preview
            binding.idAgencyAddMemberProfileImage.setImageURI(uriImages.get(0));
        }
    }

    private void click() {
        binding.idAgencyAddMemberNextButton.setOnClickListener(v -> {
            // Assuming you need to upload member details
            uploadMemberDetails();
        });

        binding.idAgencyMemberDetailBackButton.setOnClickListener(v -> {
            FragmentManager transaction = requireActivity().getSupportFragmentManager();
            transaction.popBackStack();
        });
    }

    private void uploadMemberDetails() {
        memberDetails = new MemberDetails();
        memberDetails.setName(name);
        memberDetails.setEmail(email);
        memberDetails.setMobile(phone);
        memberDetails.setAddress(address);
        memberDetails.setDob(dob);
        memberDetails.setRole(role);
        memberDetails.setYear_of_experience(year);

        Call<SignUpResponse> responseCall = RestClient.makeAPI().addMember(agencyIDBody,nameBody,phoneBody,emailBody,addressBody,dobBody,roleBody,yearBody,part);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse memberDetailsResponse = response.body();
                    if (memberDetailsResponse != null && memberDetailsResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), memberDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), memberDetailsResponse != null ? memberDetailsResponse.getMessage() : "Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ADDMEMBER", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
