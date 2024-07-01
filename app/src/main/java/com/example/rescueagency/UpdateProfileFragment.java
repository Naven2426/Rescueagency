package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rescueagency.admin.AdminAddAgencyNextFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.UpdateProfile;
import com.example.rescueagency.databinding.FragmentAdminAddAgencyBinding;
import com.example.rescueagency.databinding.FragmentUpdateProfileBinding;

import java.util.Calendar;

import retrofit2.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileFragment extends Fragment {

    FragmentUpdateProfileBinding binding;
    private String name,mobile,email,address,dob,gender,id;
    private RadioGroup radioGroup;
    private RadioButton radioButtonGender;
    private SharedPreferences sf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false);
        click();
        radioGroup = binding.idGroupRadio;
        sf=requireActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        id=sf.getString(Constant.SF_USERID,"");
        name = sf.getString(Constant.SF_NAME, "");
        mobile = sf.getString(Constant.SF_PHONE, "");
        email = sf.getString(Constant.SF_EMAIL, "");
        address = sf.getString(Constant.SF_ADDRESS, "");
        dob = sf.getString(Constant.SF_DOB, "");
        gender = sf.getString(Constant.SF_GENDER, "");
        binding.idUpdateProfileNameEText.setText(name);
        binding.idUpdateProfileMobileNumberEText.setText(mobile);
        binding.idUpdateProfileEmailEText.setText(email);
        binding.idUpdateProfileAddressEText.setText(address);
        binding.idUpdateProfileDOBEText.setText(dob);
        if (gender.equals("Male")) {
            binding.idSelectMale.setChecked(true);
        } else {
            binding.idSelectFemale.setChecked(true);
        }
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(hideAnimation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
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
               apiupdateProfile(name,mobile,email,address,dob,gender,id);

            }
            }
        });
        binding.idUpdateProfileDOBEText.setOnClickListener(new View.OnClickListener() {
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
                                binding.idUpdateProfileDOBEText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
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
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            if (gender == null) {
                Toast.makeText(getContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            radioButtonGender = radioGroup.findViewById(selectedId);
            gender = radioButtonGender.getText().toString();
        }
        return true;
    }
    private void apiupdateProfile(String name, String mobile, String email, String address, String dob, String gender, String id) {

        UpdateProfile updateProfile=new UpdateProfile();
        updateProfile.setName(name);
        updateProfile.setPhone(mobile);
        updateProfile.setEmail(email);
        updateProfile.setAddress(address);
        updateProfile.setDob(dob);
        updateProfile.setGender(gender);
        updateProfile.setId(id);
        Call<SignUpResponse> responseCall = RestClient.makeAPI().updateProfile(updateProfile);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    assert signUpResponse != null;
                    if (signUpResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager manager = requireActivity().getSupportFragmentManager();
                        manager.popBackStack();
                    } else {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}