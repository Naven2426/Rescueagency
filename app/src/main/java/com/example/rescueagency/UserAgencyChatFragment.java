package com.example.rescueagency;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.databinding.FragmentUserAgencyChatBinding;


public class UserAgencyChatFragment extends Fragment {

    FragmentUserAgencyChatBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserAgencyChatBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.idChatBackButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }
}