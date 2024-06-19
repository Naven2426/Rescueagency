package com.example.rescueagency.admin.HomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAdminHomeBinding;
import com.example.rescueagency.databinding.FragmentAdminHomeUserBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeUserFragment extends Fragment {

    private FragmentAdminHomeUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeUserBinding.inflate(inflater, container, false);
        recyclerview();
        return binding.getRoot();
    }

    private void recyclerview() {
        List<AdminUserViewList> data = new ArrayList<>();
        data.add(new AdminUserViewList("2","name","mark"));
        data.add(new AdminUserViewList("2","name","mark"));
        data.add(new AdminUserViewList("2","name","mark"));
        data.add(new AdminUserViewList("2","name","mark"));
        data.add(new AdminUserViewList("2","name","mark"));
        data.add(new AdminUserViewList("2","name","mark"));



    }
}