package com.example.rescueagency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.databinding.FragmentChangePasswordBinding;
import com.example.rescueagency.databinding.FragmentUpdateProfileBinding;


public class ChangePasswordFragment extends Fragment {
    FragmentChangePasswordBinding binding;
    private String oldpassword, newpassword, confirmpassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        click();
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
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new ProfileFragment());
                    fragmentTransaction.commit();
                }
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
        return true;
    }

}
