package com.example.rescueagency.LoginActivityFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
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

import com.example.rescueagency.Constant;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private AppCompatEditText personName, personMobile, personEmail, personAddress, personDateOfBirth, personUsername, personPassword, personConfirmPassword;
    private RadioGroup radioGroup;
    private RadioButton radioButtonGender;
    private String name, mobile, email, address, dateOfBirth, gender, username, password, confirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        textField(view);
        radioGroup = view.findViewById(R.id.groupradio);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton button = view.findViewById(R.id.id_button_signup_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_animation_for_button);
                button.startAnimation(animation);
                if (getTextField()) {
                    apiCall(name, mobile, email, address, dateOfBirth, gender, username, password, Constant.USER_TYPE);
                }
            }
        });

        ImageView backPressimageView = view.findViewById(R.id.SignUpBackbutton);
        backPressimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.popBackStack();
            }
        });

        personDateOfBirth.setFocusable(false);
        personDateOfBirth.setOnClickListener(new View.OnClickListener() {
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
                                personDateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        return view;
    }

    private boolean getTextField() {
        name = personName.getText().toString();
        mobile = personMobile.getText().toString();
        email = personEmail.getText().toString();
        address = personAddress.getText().toString();
        dateOfBirth = personDateOfBirth.getText().toString();
        username = personUsername.getText().toString();
        password = personPassword.getText().toString();
        confirmPassword = personConfirmPassword.getText().toString();

        if (name.isEmpty()) {
            personName.setError("Please enter your name");
            return false;
        } else {
            personName.setError(null);
        }
        if (mobile.isEmpty()) {
            personMobile.setError("Please enter your mobile number");
            return false;
        } else {
            personMobile.setError(null);
        }
        if (email.isEmpty()) {
            personEmail.setError("Please enter your email");
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                personEmail.setError(null);
            } else {
                personEmail.setError("Please enter a valid email address");
                return false;
            }
        }
        if (address.isEmpty()) {
            personAddress.setError("Please enter your address");
            return false;
        } else {
            personAddress.setError(null);
        }
        if (dateOfBirth.isEmpty()) {
            personDateOfBirth.setError("Please enter your date of birth");
            return false;
        } else {
            personDateOfBirth.setError(null);
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
        if (username.isEmpty()) {
            personUsername.setError("Please enter your username");
            return false;
        } else {
            personUsername.setError(null);
        }
        if (password.isEmpty()) {
            personPassword.setError("Please enter your password");
            return false;
        } else {
            personPassword.setError(null);
        }
        if (confirmPassword.isEmpty()) {
            personConfirmPassword.setError("Please confirm your password");
            return false;
        } else {
            personConfirmPassword.setError(null);
        }
        if (!password.equals(confirmPassword)) {
            personConfirmPassword.setError("Passwords do not match");
            return false;
        } else {
            personConfirmPassword.setError(null);
        }

        return true;
    }

    private void apiCall(String name, String phone, String email, String address, String dob, String gender,
                         String username, String password, String userType) {
        Call<SignUpResponse> responseCall = RestClient.makeAPI().signUp(name, phone, email,
                address, dob, gender, username, password, userType);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse signUpResponse = response.body();
                    if (signUpResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.popBackStack();
                        manager.popBackStack();
                    } else {
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

    private void textField(View view) {
        personName = view.findViewById(R.id.id_edittext_signup_name_Text);
        personMobile = view.findViewById(R.id.id_edittext_signup_mobile_Text);
        personEmail = view.findViewById(R.id.id_edittext_signup_email_Text);
        personAddress = view.findViewById(R.id.id_edittext_signup_address_Text);
        personDateOfBirth = view.findViewById(R.id.id_edittext_signup_date_Text);
        personUsername = view.findViewById(R.id.id_edittext_username_Text);
        personPassword = view.findViewById(R.id.id_edittext_signup_pasword_Text);
        personConfirmPassword = view.findViewById(R.id.id_edittext_confirm_password_Text);
    }
}
