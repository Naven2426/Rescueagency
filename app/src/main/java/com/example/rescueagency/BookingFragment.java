package com.example.rescueagency;

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
import android.widget.ImageView;

import com.example.rescueagency.LoginActivityFragments.LoginFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentBookingBinding;
import com.example.rescueagency.main_menu_fragments.HomeFragment;


public class BookingFragment extends Fragment {
    FragmentBookingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingBinding.inflate(inflater,container,false);
        clickListener();
        return binding.getRoot();
    }
    private void clickListener(){
        binding.idRequestSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new alertsentFragment()).commit();
            }
        });

        binding.idRequestChooseTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.frameLayout, new MapsFragment()).addToBackStack("RescueTeamSelcetionInMapFragment").commit();
//                Intent intent=new Intent(requireContext(),MapsActivity.class);
//                startActivity(intent);
            }
        });


        binding.idBookingBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the previous fragment
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }
}