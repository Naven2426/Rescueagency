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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.DatePicker;


import com.example.rescueagency.R;

import java.util.Calendar;

public class SignUpFragment extends Fragment {

    private AppCompatEditText personName, personMobile, personEmail, personAddress, personDateOfBirth;
    private RadioGroup radioGroup;
    private RadioButton radioButtonGender;
    private String name, mobile, email, address, dateOfBirth,gender;


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
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_left);
                    SignUpPasswordFragment sign=new SignUpPasswordFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("mobile",mobile);
                    bundle.putString("email",email);
                    bundle.putString("address",address);
                    bundle.putString("dateOfBirth",dateOfBirth);
                    bundle.putString("gender",gender);
                    sign.setArguments(bundle);
                    transaction.replace(R.id.loginFrameLayout, sign)
                            .addToBackStack("SignUpPasswordFragment").commit();
                } else {

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

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                personDateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
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
                if(gender==null)
                {
                    Toast.makeText(getContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else {
                radioButtonGender = radioGroup.findViewById(selectedId);
                gender = radioButtonGender.getText().toString();
            }

            return true;

        }

    }
    private boolean datePicker()
    {

        return true;
    }

    private void textField(View view){
        personName = view.findViewById(R.id.id_edittext_signup_name_Text);
        personMobile = view.findViewById(R.id.id_edittext_signup_mobile_Text);
        personEmail = view.findViewById(R.id.id_edittext_signup_email_Text);
        personAddress = view.findViewById(R.id.id_edittext_signup_address_Text);
        personDateOfBirth = view.findViewById(R.id.id_edittext_signup_date_Text);
    }
}
