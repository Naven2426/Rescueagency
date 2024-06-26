package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.LoginActivityFragments.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {




    AppCompatButton updateButton;
    AppCompatButton changepassword;
    //imageview
    AppCompatImageView profilename;
    AppCompatImageView profileemail;
    AppCompatImageView profilephone;
    AppCompatImageView profileaddress;
    AppCompatImageView profiledob;

    AppCompatImageView logoutButton;
    //textview
    AppCompatTextView nameAppCompatTextView;
    AppCompatTextView emailAppCompatTextView;
    AppCompatTextView phoneAppCompatTextView;
    AppCompatTextView addressAppCompatTextView;
    AppCompatTextView dobAppCompatTextView;

    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        clickListener();
        setText();
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(showAnimation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return view;
    }
    private void init(View view){
        //appcompat button
        updateButton = view.findViewById(R.id.id_profile_update_button);
        changepassword = view.findViewById(R.id.id_profile_change_password_button);
        //textview
        nameAppCompatTextView= view.findViewById(R.id.idProfileNameTV);
        emailAppCompatTextView = view.findViewById(R.id.idProfileEmailTV);
        phoneAppCompatTextView = view.findViewById(R.id.idProfileMobileTV);
        addressAppCompatTextView = view.findViewById(R.id.idProfileAddressTV);
        dobAppCompatTextView = view.findViewById(R.id.idProfileDOBTV);
        //imageview
        profilename = view.findViewById(R.id.id_profile_image);
        profileemail = view.findViewById(R.id.id_profile_email);
        profilephone = view.findViewById(R.id.id_profile_mobile);
        profileaddress = view.findViewById(R.id.id_profile_address);
        profiledob = view.findViewById(R.id.id_profile_dob);
        logoutButton = view.findViewById(R.id.idProfileLogoutIV);

    }
    private void setText(){
        SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        userId=sf.getString(Constant.SF_USERID,null);
        String name=sf.getString(Constant.SF_NAME,null);
        String email=sf.getString(Constant.SF_EMAIL,null);
        String phone=sf.getString(Constant.SF_PHONE,null);
        String address=sf.getString(Constant.SF_ADDRESS,null);
        String dob=sf.getString(Constant.SF_DOB,null);
        String username=sf.getString(Constant.SF_USERNAME,null);
        nameAppCompatTextView.setText(name);
        emailAppCompatTextView.setText(email);
        phoneAppCompatTextView.setText(phone);
        addressAppCompatTextView.setText(address);
        dobAppCompatTextView.setText(dob);

    }
    private void clickListener(){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                sf.edit().clear().apply();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming you want to replace the current ProfileFragment with a new instance
                Fragment newUpadteProfileFragment = new UpdateProfileFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, newUpadteProfileFragment ); // replace with the container id of your fragment
                transaction.addToBackStack(null); // add to back stack if you want to allow 'back' navigation
                transaction.commit();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newChangePasswordFragment = new ChangePasswordFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, newChangePasswordFragment ); // replace with the container id of your fragment
                transaction.addToBackStack(null); // add to back stack if you want to allow 'back' navigation
                transaction.commit();

            }
        });
    }

}
