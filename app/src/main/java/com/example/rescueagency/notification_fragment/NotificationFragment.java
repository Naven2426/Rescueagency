package com.example.rescueagency.notification_fragment;

import static com.bumptech.glide.Glide.init;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentNotificationBinding;
import com.example.rescueagency.main_menu_fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        clickListener();
        recycle();
        return binding.getRoot();
    }

        private void clickListener() {
            binding.idNotificationBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frameLayout, new HomeFragment()).commit();
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
        NotificationListHolder object = new NotificationListHolder(data,requireActivity());
        binding.idNotificationRequestList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.idNotificationRequestList.setAdapter(object);
    }

}