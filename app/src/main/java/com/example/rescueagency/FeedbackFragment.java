package com.example.rescueagency;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.databinding.FragmentFeedbackBinding;
//import com.example.rescueagency.databinding.FragmentTrackingMapBinding;


public class FeedbackFragment extends Fragment {

    FragmentFeedbackBinding binding;
    private String describe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.idFeedbackBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }


        });
        binding.idFeedbackSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.feedback_sumitted);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                if(getTextField()) {
                    dialog.show();
                }


                    androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.id_feedback_back_to_notification);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            dialog.dismiss();
                            getActivity().finish();

                    }
                });


            }
        });

    }
    private boolean getTextField() {
        describe = binding.idFeedbackFillText.getText().toString().trim();
        if (describe.isEmpty()) {
            binding.idFeedbackFillText.setError("Please enter your feedback");
            return false;
        }
        return true;
    }
}


