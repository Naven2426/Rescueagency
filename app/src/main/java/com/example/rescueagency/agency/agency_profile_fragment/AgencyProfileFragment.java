package com.example.rescueagency.agency.agency_profile_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.LoginActivity;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.AgencyAddMemberFragment;
import com.example.rescueagency.agency.AgencyLeaderProfileViewFragment;
import com.example.rescueagency.agency.AgencyReviewFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.agencyinfo.AgencyInfoRoot;
import com.example.rescueagency.apiresponse.agencyinfo.Data;
import com.example.rescueagency.apiresponse.agencyinfo.Member;
import com.example.rescueagency.databinding.FragmentAgencyProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyProfileFragment extends Fragment {

    private String id;
    private FragmentAgencyProfileBinding binding;
    private SharedPreferences sf;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgencyProfileBinding.inflate(inflater, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        sf = requireActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
        id = sf.getString(Constant.SF_USERID, "");
        setupLogoutButton();
        fetchAgencyInfo(id);
        setupLeaderButton();
        return binding.getRoot();
    }

    private void setupLogoutButton() {
        binding.idLogoutIV.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.logout_dialog_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();

            dialog.findViewById(R.id.idAgencyAddedBTNButton).setOnClickListener(v1 -> {
                SharedPreferences sf = requireActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
                sf.edit().clear().apply();
                requireActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            });

            dialog.findViewById(R.id.idAgencyAddedBTHButton).setOnClickListener(v12 -> dialog.dismiss());
        });
    }

    private void setupLeaderButton() {
        binding.idAgentProfileImageIV.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, new AgencyLeaderProfileViewFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.idAgencyAddMemberCV.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, new AgencyAddMemberFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.idAgencyReviews.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, new AgencyReviewFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    private void fetchAgencyInfo(String id) {
        Call<AgencyInfoRoot> responseCall = RestClient.makeAPI().getTeam(id);
        responseCall.enqueue(new Callback<AgencyInfoRoot>() {
            @Override
            public void onResponse(@NonNull Call<AgencyInfoRoot> call, @NonNull Response<AgencyInfoRoot> response) {
                if (!isAdded()) {
                    return;
                }
                if (response.isSuccessful()) {
                    AgencyInfoRoot agencyInfoRoot = response.body();
                    if (agencyInfoRoot != null && agencyInfoRoot.getStatus() == 200) {
                        Data data = agencyInfoRoot.getData();
                        List<AgencyProfileList> profileLists = new ArrayList<>();
                        for (Member member : data.getMember()) {
                            profileLists.add(new AgencyProfileList(member.getMember_name(), member.getMember_profile(),""+member.getMember_id(),member.getMember_email(),""+member.getMember_mobile(),member.getMember_address(),member.getMember_dob(),member.getMember_role(),""+member.getMember_experience()));
                        }
                        binding.idAgencyMemberRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                        binding.idAgencyMemberRecyclerView.setAdapter(new AgencyProfileListHolder(profileLists, requireActivity()));
                    } else {
                        Toast.makeText(getContext(), agencyInfoRoot != null ? agencyInfoRoot.getMessage() : "Response not successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AgencyInfoRoot> call, @NonNull Throwable t) {
                if (!isAdded()) {
                    return;
                }
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
