package com.example.rescueagency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rescueagency.databinding.FragmentBookingBinding;

public class BookingFragment extends Fragment {
    FragmentBookingBinding binding;
    private String describe;
    private String selectedAgency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        clickListener();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Listen for the result from MapsFragment
        getParentFragmentManager().setFragmentResultListener("agencySelection", this, (requestKey, result) -> {
            selectedAgency = result.getString("selectedAgency");
            binding.idRequestChooseTeamButton.setText(selectedAgency);
        });
    }

    private void clickListener() {
        binding.idRequestSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frameLayout, new alertsentFragment()).commit();
                }
            }
        });

        binding.idRequestChooseTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new MapsFragment()).addToBackStack("MapsFragment").commit();
            }
        });

        binding.idBookingBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }

    private boolean getTextField() {
        describe = binding.idEdittextRequestDescribe.getText().toString().trim();
        if (describe.isEmpty()) {
            binding.idEdittextRequestDescribe.setError("Please describe your problem");
            return false;
        }
        return true;
    }
}
