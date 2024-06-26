package com.example.rescueagency.notification_fragment;

import static com.bumptech.glide.Glide.init;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.example.rescueagency.R;
import com.example.rescueagency.databinding.ActivityNotificationBinding;
import com.example.rescueagency.main_menu_fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        clickListener();
        recycle();
        setContentView(binding.getRoot());
//        return binding.getRoot();
    }

        private void clickListener() {
            binding.idNotificationBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
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
        NotificationListHolder object = new NotificationListHolder(data,NotificationActivity.this);
        binding.idNotificationRequestList.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        binding.idNotificationRequestList.setAdapter(object);
    }

}