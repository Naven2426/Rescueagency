package com.example.rescueagency.agency;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.UpdateProfile;
import com.example.rescueagency.databinding.FragmentAgencyMemberProfileUpdateBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyMemberProfileUpdateFragment extends Fragment {

    FragmentAgencyMemberProfileUpdateBinding binding;

    public static List<Uri> uriImages;
    private final ActivityResultLauncher<String> pickMedia =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    uriImages = new ArrayList<>();
                    uriImages.add(uri);
                    updateImagePreview();
                } else {
                    Toast.makeText(requireContext(), "No Media Selected", Toast.LENGTH_SHORT).show();
                }
            });

    private String name, email, phone, address, dob, role, year;
    private String memberId; // Add memberId to identify the member

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyMemberProfileUpdateBinding.inflate(inflater, container, false);
        click();
        memberId = getArguments().getString("memberId"); // Get memberId from arguments
        MainActivity mainActivity = (MainActivity) getActivity();
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

        binding.idAgencyAddMemberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch("image/*");
            }
        });

        binding.idAgencyMemberUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTextField()) {
                    updateMemberProfile(name, phone, email, address, dob, role, year, memberId); // Add API call
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

    private boolean getTextField() {
        name = binding.idAgencyMemberUpdateProfileName.getText().toString().trim();
        phone = binding.idAgencyMemberUpdateProfileMobile.getText().toString().trim();
        email = binding.idAgencyMemberUpdateProfileEmail.getText().toString().trim();
        address = binding.idAgencyMemberUpdateProfileAddress.getText().toString().trim();
        dob = binding.idAgencyMemberUpdateProfileDob.getText().toString().trim();
        role = binding.idAgencyMemberUpdateProfileRole.getText().toString().trim();
        year = binding.idAgencyMemberUpdateProfileYoe.getText().toString().trim();

        if (name.isEmpty()) {
            binding.idAgencyMemberUpdateProfileName.setError("Enter Name");
            return false;
        }
        if (phone.isEmpty()) {
            binding.idAgencyMemberUpdateProfileMobile.setError("Enter Mobile Number");
            return false;
        }
        if (email.isEmpty()) {
            binding.idAgencyMemberUpdateProfileEmail.setError("Enter Email");
            return false;
        }
        if (address.isEmpty()) {
            binding.idAgencyMemberUpdateProfileAddress.setError("Enter Address");
            return false;
        }
        if (dob.isEmpty()) {
            binding.idAgencyMemberUpdateProfileDob.setError("Enter DOB");
            return false;
        }
        if (role.isEmpty()) {
            binding.idAgencyMemberUpdateProfileRole.setError("Enter Role");
            return false;
        }
        if (year.isEmpty()) {
            binding.idAgencyMemberUpdateProfileYoe.setError("Enter Year");
            return false;
        }
        return true;
    }

    private void updateMemberProfile(String name, String phone, String email, String address, String dob, String role, String year, String memberId) {
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.setName(name);
        updateProfile.setPhone(phone);
        updateProfile.setEmail(email);
        updateProfile.setAddress(address);
        updateProfile.setDob(dob);
        updateProfile.setRole(role);
        updateProfile.setYear(year);
        updateProfile.setId(memberId);

        Call<SignUpResponse> responseCall = RestClient.makeAPI().updateProfile(updateProfile);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    assert signUpResponse != null;
                    if (signUpResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, new AgencyProfileFragment());
                        transaction.commit();
                    } else {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateImagePreview() {
        if (uriImages != null && !uriImages.isEmpty()) {
            binding.showImagesVP.setVisibility(View.VISIBLE);
            BookingFragment.ImagePreviewAdapter imagePreviewAdapter = new BookingFragment.ImagePreviewAdapter(uriImages, requireContext());
            binding.showImagesVP.setAdapter(imagePreviewAdapter); // Make sure ImagePreviewAdapter extends PagerAdapter
        } else {
            binding.showImagesVP.setVisibility(View.GONE);
        }
    }
}
