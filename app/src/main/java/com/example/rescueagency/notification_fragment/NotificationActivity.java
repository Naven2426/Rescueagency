package com.example.rescueagency.notification_fragment;

import static com.bumptech.glide.Glide.init;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.ActivityNotificationBinding;
import com.example.rescueagency.main_menu_fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.notificationFrameLayout,new NotificationFragment()).commit();
    }


}