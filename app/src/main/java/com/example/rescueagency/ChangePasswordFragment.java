package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentChangePasswordBinding;
import com.example.rescueagency.databinding.FragmentUpdateProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;
    private String oldpassword, newpassword, confirmpassword,id;
    private SharedPreferences sf;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        click();
        sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
        id = sf.getString(Constant.SF_USERID,"");
        MainActivity mainActivity = (MainActivity) getActivity();
        Animation hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(hideAnimation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();

    }

    private void click() {

        binding.idChangePasswordBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navigate back to the previous fragment
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
            }

        });


        binding.idChangePasswordSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    apiCall(oldpassword, newpassword,id);
                }
            }
        });

    }

    private void apiCall(String oldpassword, String newpassword, String id) {

        Log.d("checking", "apiCall: old"+oldpassword+"new"+newpassword);

        Call<SignUpResponse> responseCall = RestClient.makeAPI().changePassword(oldpassword,newpassword,id);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()){
                    SignUpResponse signUpResponse = response.body();
                    if (signUpResponse.getStatus() == 200){
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStack();
                    }
                    else {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Response Not Successful", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private boolean getTextField() {
        oldpassword = binding.idChangePasswordCurrentPasswordText.getText().toString();
        newpassword = binding.idChangePasswordPasswordText.getText().toString();
        confirmpassword = binding.idChangePasswordConfirmText.getText().toString();

        if (oldpassword.isEmpty()) {
            binding.idChangePasswordCurrentPasswordText.setError("Enter Old Password");
            return false;
        }
        if (newpassword.isEmpty()) {
            binding.idChangePasswordPasswordText.setError("Enter New Password");
            return false;
        }
        if (confirmpassword.isEmpty()) {
            binding.idChangePasswordConfirmText.setError("Enter Confirm Password");
            return false;
        }
        if (!newpassword.equals(confirmpassword)) {
            binding.idChangePasswordConfirmText.setError("Password Not Match");
            return false;
        }
        return true;
    }




}
