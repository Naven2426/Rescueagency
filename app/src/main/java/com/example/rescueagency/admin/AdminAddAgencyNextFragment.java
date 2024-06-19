package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentAdminAddAgencyNextBinding;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddAgencyNextFragment extends Fragment {

    private String email,username,password,confirmpassword;

    FragmentAdminAddAgencyNextBinding binding;
    private String agencyName,teamType,mobile,address,totalMember,proof;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAddAgencyNextBinding.inflate(inflater, container, false);
        regesterAgency();
        Bundle bundle=getArguments();
        if(bundle!=null){
            agencyName=bundle.getString("agencyName");
            teamType=bundle.getString("teamType");
            mobile=bundle.getString("mobile");
            address=bundle.getString("address");
            totalMember=bundle.getString("totalMember");
            proof=bundle.getString("proof");

        }
        return binding.getRoot();
    }
    public void regesterAgency(){
        binding.idAddAgencyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        binding.idAddAgencyRegisterButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    RequestBody requestBody=RequestBody.create(MultipartBody.FORM,new File(proof));
                    MultipartBody.Part part=MultipartBody.Part.createFormData("file",new File(proof).getName(),requestBody);
                   api(agencyName,teamType,mobile,address,totalMember,part,email,username,password, Constant.LOGIN_AS_AGENCY);
                }
            }

            private boolean getTextField() {
                email = binding.idAddAgencyAgencyEmailET.getText().toString();
                username = binding.idAddAgencyUsernameET.getText().toString();
                password = binding.idAddAgencyPasswordET.getText().toString();
                confirmpassword = binding.idAddAgencyConfirmPasswordET.getText().toString();
                if (email.isEmpty()) {
                    binding.idAddAgencyAgencyEmailET.setError("Email is required");
                    return false;
                }
                if (username.isEmpty()) {
                    binding.idAddAgencyUsernameET.setError("Username is required");
                    return false;
                }
                if (password.isEmpty()) {
                    binding.idAddAgencyPasswordET.setError("Password is required");
                    return false;
                }
                if (confirmpassword.isEmpty()) {
                    binding.idAddAgencyConfirmPasswordET.setError("Confirm Password is required");
                    return false;
                }
                if (password.length() < 6) {
                    binding.idAddAgencyPasswordET.setError("Password must be at least 6 characters");
                    return false;
                }
                if (!password.equals(confirmpassword)) {
                    binding.idAddAgencyConfirmPasswordET.setError("Password does not match");
                    return false;
                }
                binding.idAddAgencyAgencyEmailET.setError(null);
                binding.idAddAgencyUsernameET.setError(null);
                binding.idAddAgencyPasswordET.setError(null);
                binding.idAddAgencyConfirmPasswordET.setError(null);
                binding.idAddAgencyRegisterButton2.setEnabled(true);
                return true;

            }
        });
    }
    private void api(String agencyName, String teamType, String mobile, String address, String totalMember,
                     MultipartBody.Part part , String email, String userName, String password,String userType){

        Call<SignUpResponse> responseCall= RestClient.makeAPI().agencyRegister(agencyName,teamType,mobile,address,totalMember,part,email,userName,password,userType);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    SignUpResponse signUpResponse=response.body();
                    if(signUpResponse.getStatus()==200){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                                R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.frameLayout, new AdminHomeFragment()).commit();
                    }else{
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error",t.getMessage());
            }
        });
    }


}