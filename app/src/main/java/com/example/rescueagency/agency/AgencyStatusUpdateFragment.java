package com.example.rescueagency.agency;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyStatusUpdateBinding;


public class AgencyStatusUpdateFragment extends Fragment {

FragmentAgencyStatusUpdateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyStatusUpdateBinding.inflate(inflater, container, false);
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }
    private void click(){
        binding.idAgencyStatusUpdateDetailBackButIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        binding.idAppCompatButtonRequestSolvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.agency_req_status_update_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idBackToRequestButton);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    }
                });
            }
        });

        binding.idAppCompatButtonRequestUnsolvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.agency_req_status_update_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idBackToRequestButton);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    }
                });
            }
        });
    }
}