package com.example.rescueagency.LoginActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPasswordFragment extends Fragment {

private AppCompatEditText personUsername, personPassword, personConfirmPassword;
private String username, password, confirmPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_password, container, false);
        textField(view);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton button = view.findViewById(R.id.Register_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    Bundle bundle=getArguments();
                    if(bundle!=null){
                        String name=bundle.getString("name");
                        String phone=bundle.getString("mobile");
                        String email=bundle.getString("email");
                        String address=bundle.getString("address");
                        String dob=bundle.getString("dateOfBirth");
                        String gender=bundle.getString("gender");
                        apiCall(name,phone,email,address,dob,gender,username,password);
                    }
                    else{
                        Toast.makeText(getContext(), "Values are null", Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
            }
        });


        ImageView imageView = view.findViewById(R.id.SignUp_password_Backbutton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
            }
        });

    return view;
    }
    private void apiCall(String name, String phone, String email,String address,String dob,String gender,
                         String username, String password){
        Call<SignUpResponse> responseCall= RestClient.makeAPI().signUp(name, phone, email,
                address, dob,gender, username, password);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    SignUpResponse signUpResponse=response.body();
                    if(signUpResponse.getStatus()==200){
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStack();
                        manager.popBackStack();
                    }
                    else{
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean getTextField(){
        username=personUsername.getText().toString();
        password=personPassword.getText().toString();
        confirmPassword=personConfirmPassword.getText().toString();
        if(username.isEmpty()){
            personUsername.setError("Please enter your name");
            return false;
        }else{
            personUsername.setError(null);
        }
        if(password.isEmpty()){
            personPassword.setError("Please enter your mobile number");
            return false;
        }else{
            personPassword.setError(null);
        }
        if(confirmPassword.isEmpty()){
            personConfirmPassword.setError("Please enter your email");
            return false;
        }else{
            personConfirmPassword.setError(null);
        }
        return true;
    }
private void textField(View view){
        personUsername=view.findViewById(R.id.id_edittext_username_Text);
        personPassword=view.findViewById(R.id.id_edittext_signup_pasword_Text);
        personConfirmPassword=view.findViewById(R.id.id_edittext_confirm_password_Text);
    }
}