package com.example.rescueagency.notification_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.FeedbackFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater,container,false);
        clickListener();
        recycle();
        return binding.getRoot();
    }
    private void clickListener() {
        binding.idNotificationBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();

            }
        });

    }
    private void recycle(){
        List<NotificationList> data = new ArrayList<>();
        data.add(new NotificationList("sdh","Feedback  :","sdhf","jdshf","dfs","1546","Feedback","jb"));
        data.add(new NotificationList("sdh","Feedback  :","sdhf","jdshf","dfs","1546","Feedback","jb"));
        data.add(new NotificationList("sdh","Feedback  :","sdhf","jdshf","dfs","1546","Feedback","jb"));
        data.add(new NotificationList("sdh","Feedback  :","sdhf","jdshf","dfs","1546","Feedback","jb"));
        data.add(new NotificationList("sdh","Feedback  :","sdhf","jdshf","dfs","1546","Feedback","jb"));

        binding.idNotificationRequestList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idNotificationRequestList.setAdapter(new NotificationListHolder(data,requireActivity()));
    }
}