package com.example.rescueagency.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.ChangePasswordFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.UpdateProfileFragment;
import com.example.rescueagency.alertsentFragment;
import com.example.rescueagency.databinding.FragmentAdminProfileBinding;

public class AdminProfileFragment extends Fragment {

    FragmentAdminProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        change();
        return binding.getRoot();
    }
    private void change() {
        binding.idAdminProfileUpdateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new UpdateProfileFragment()).commit();
            }
        });

        binding.idAdminProfileChangePasswordB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new ChangePasswordFragment()).commit();
            }
        });
    }
}