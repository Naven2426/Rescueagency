package com.example.rescueagency.user_agency_member_list_view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.Constant;
import com.example.rescueagency.MapsFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.agencyinfo.AgencyInfoRoot;
import com.example.rescueagency.apiresponse.agencyinfo.Data;
import com.example.rescueagency.apiresponse.agencyinfo.Member;
import com.example.rescueagency.databinding.FragmentBookingBinding;
import com.example.rescueagency.databinding.FragmentRescueTeamDetailBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RescueTeamDetailFragment extends Fragment {

    FragmentRescueTeamDetailBinding binding;
    SharedPreferences sf;
    private String teamName,agentId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRescueTeamDetailBinding.inflate(inflater, container, false);
        sf=requireActivity().getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        assert getArguments() != null;
        String id=getArguments().getString("agentId");
        rever();
        apishow(id);
        return binding.getRoot();

    }


    public void rever() {
        binding.idRescueTeamViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        binding.idRescueTeamViewConfirmAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agentId!=null && teamName!=null ){
                    SharedPreferences.Editor editor=sf.edit();
                    editor.putString(Constant.SF_AGENT_ID_FOR_NEW_REQUEST,agentId);
                    editor.putString(Constant.SF_TEAM_NAME_FOR_NEW_REQUEST,teamName);
                    editor.apply();
                }
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
                transaction.popBackStack();
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
                        teamName=data.getTeam_name();
                        agentId=""+data.getTeam_id();
                        binding.idRescueTeamViewTeamName.setText(data.getTeam_name());
                        binding.idRescueTeamViewAddress.setText(data.getTeam_address());
                        binding.idRescueTeamViewServices.setText(data.getType_of_service());
                        binding.idRescueTeamViewPhoneNumber.setText(""+data.getTeam_contact());
                        List<user_rescue_team_member_list> memberList = new ArrayList<>();
                        for(Member member : data.getMember()) {
                            memberList.add(new user_rescue_team_member_list(""+member.getMember_experience(),member.getMember_profile(),member.getMember_name(),member.getMember_role()));
                        }
                        try {
                            binding.idRescueTeamRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            binding.idRescueTeamRecyclerView.setAdapter(new UserRescueTeamMemberListHolder(memberList, requireActivity()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else {
                            Toast.makeText(getContext(), agencyInfoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Response Not Successful", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure (Call < AgencyInfoRoot > call, Throwable t){
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
