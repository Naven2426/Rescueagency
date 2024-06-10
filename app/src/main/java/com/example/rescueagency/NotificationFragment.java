package com.example.rescueagency;

import static com.bumptech.glide.Glide.init;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rescueagency.databinding.FragmentNotificationBinding;
import com.example.rescueagency.main_menu_fragments.HomeFragment;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        clickListener();
        return binding.getRoot();
    }

        private void clickListener(){
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

}