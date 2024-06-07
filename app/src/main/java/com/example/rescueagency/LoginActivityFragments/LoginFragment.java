package com.example.rescueagency.LoginActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.main_menu_fragments.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private AppCompatEditText personName, personPassword;
    private String name, password;

    AppCompatButton appCompatButton;
    TextView signup;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textField(view);
        appCompatButton = view.findViewById(R.id.Login_button);
        signup = view.findViewById(R.id.SignUPNewAccount);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.dropdown_item, Constant.LOGIN_TYPE);
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.Login_text);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(arrayAdapter);
        sharedPreferences = requireActivity().getSharedPreferences(Constant.SF_NAME, 0);
        editor = sharedPreferences.edit();
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    apiCall(name,password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.loginFrameLayout, new SignUpFragment()).addToBackStack("signup").commit();
            }
        });
        return view;
    }
        private boolean getTextField() {
            name = personName.getText().toString();
            password = personPassword.getText().toString();
            if (name.isEmpty()) {
                personName.setError("Please enter your name");
                return false;
            } else if (password.isEmpty()) {
                personPassword.setError("Please enter your password");
                return false;
            } else {
                return true;
            }

        }
            private void textField(View view) {
                personName = view.findViewById(R.id.id_edittext_login_username_Text);
                personPassword = view.findViewById(R.id.id_edittext_login_password_Text);



    }


    private void apiCall(String username, String password){
        Call<SignUpResponse> responseCall= RestClient.makeAPI().login(username, password);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    SignUpResponse signUpResponse=response.body();
                    if(signUpResponse.getStatus()==200){
                        SignUpResponse.User data=response.body().getUser();
                        editor.putString(Constant.SF_USERID,data.getId());
                        editor.putString(Constant.SF_NAME,data.getName());
                        editor.putString(Constant.SF_EMAIL,data.getEmail());
                        editor.putString(Constant.SF_PHONE,data.getPhone());
                        editor.putString(Constant.SF_DOB,data.getDob());
                        editor.putString(Constant.SF_ADDRESS,data.getAddress());
                        editor.apply();
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getContext().startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                    else{
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG","on failure "+t.getMessage());
            }
        });
    }
}






