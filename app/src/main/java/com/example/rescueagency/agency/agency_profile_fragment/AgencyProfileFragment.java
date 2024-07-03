package com.example.rescueagency.agency.agency_profile_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.rescueagency.user_agency_member_list_view.user_rescue_team_member_list;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class AgencyProfileFragment extends Fragment {

    private String id ;

    SharedPreferences sf;

    FragmentAgencyProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyProfileBinding.inflate(inflater, container, false);
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
        id = sf.getString(Constant.SF_USERID,"");
        logout();
        apishow(id);
        leader();
        return binding.getRoot();
    }
    public void logout(){
        binding.idLogoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.logout_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idAgencyAddedBTNButton);
                androidx.appcompat.widget.AppCompatButton cancel_text = dialog.findViewById(R.id.idAgencyAddedBTHButton);
                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                        sf.edit().clear().apply();
                        getActivity().finish();
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));

                    }

                });
                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void leader() {
        binding.idAgentProfileImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyLeaderProfileViewFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.idAgencyAddMemberCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyAddMemberFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.idAgencyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyReviewFragment());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }


    private void apishow(String id) {
        Call<AgencyInfoRoot> responseCall = RestClient.makeAPI().getTeam(id);
        responseCall.enqueue(new retrofit2.Callback<AgencyInfoRoot>() {
            @Override
            public void onResponse(Call<AgencyInfoRoot> call, Response<AgencyInfoRoot> response) {
                if (response.isSuccessful()) {
                    AgencyInfoRoot agencyInfoRoot = response.body();
                    assert agencyInfoRoot != null;
                    if (agencyInfoRoot.getStatus() == 200) {
                        Data data = agencyInfoRoot.getData();
                        List<AgencyProfileList> profileLists=new ArrayList<>();
                        for (Member member : data.getMember()) {
                            profileLists.add(new AgencyProfileList(member.getMember_name(),member.getMember_profile()));
                        }
                        binding.idAgencyMemberRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
                        binding.idAgencyMemberRecyclerView.setAdapter(new AgencyProfileListHolder(profileLists,requireActivity()));

                    }else {
                        Toast.makeText(getContext(),agencyInfoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    }else {
                        Toast.makeText(getContext(),"Response not successful", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AgencyInfoRoot> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            });

        }
}