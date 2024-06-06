package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.LoginActivityFragments.LoginFragment;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        AppCompatButton logoutButton = view.findViewById(R.id.id_profile_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                sf.edit().clear().apply();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        AppCompatButton updateButton = view.findViewById(R.id.id_profile_update_button);
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

        AppCompatButton changepassword = view.findViewById(R.id.id_profile_change_password_button);
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

        return view;
    }
}
