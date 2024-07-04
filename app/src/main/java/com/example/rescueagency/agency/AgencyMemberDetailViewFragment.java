package com.example.rescueagency.agency;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.agency_profile_fragment.AgencyProfileFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentAgencyMemberDetailViewBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyMemberDetailViewFragment extends Fragment {
    FragmentAgencyMemberDetailViewBinding binding;
    SharedPreferences sf;
    private String memberId,memberName,memberEmail, memberPhone, memberAddress, memberdob, memberRole, memberExperience, memeberProfileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAgencyMemberDetailViewBinding.inflate(inflater, container, false);
        clickListener();
        sf= getActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        Bundle bundle= getArguments();
        memberId = bundle.getString("memberId", null);
        memberName= bundle.getString("memberName", null);;
        memberEmail= bundle.getString("memberEmail", null);;
        memberPhone= bundle.getString("memberMobile", null);;
        memberAddress= bundle.getString("memberAddress", null);;
        memberdob= bundle.getString("memberDob", null);
        memberRole= bundle.getString("memberRole", null);
        memberExperience= bundle.getString("memberExperience", null);
        memeberProfileImage= bundle.getString("memberProfile", null);

        binding.idAgentProfileNameTV.setText(memberName);
        binding.idProfileMobileTV.setText(memberPhone);
        binding.idProfileEmailTV.setText(memberEmail);
        binding.idProfileAddressTV.setText(memberAddress);
        binding.idProfileDOBTV.setText(memberdob);
        binding.idProfileRollTV.setText(memberRole);
        binding.idProfileYOETV2.setText(memberExperience);
        Glide.with(getActivity()).load(memeberProfileImage).placeholder(R.mipmap.imagenotfound)
                .error(R.mipmap.error).into(binding.idAgencyAddMemberViewProfileImage);

        MainActivity mainActivity = (MainActivity) getActivity();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void clickListener() {
        binding.idAgencyMemberDetailViewBackButton.setOnClickListener(view -> {
            FragmentManager transaction = requireActivity().getSupportFragmentManager();
            transaction.popBackStack();
        });

        binding.idAgencyProfileViewEditUpdateButton.setOnClickListener(view -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            AgencyMemberProfileUpdateFragment agencyMemberProfileUpdateFragment = new AgencyMemberProfileUpdateFragment();
            Bundle bundle = new Bundle();
            bundle.putString("memberId",memberId);
            agencyMemberProfileUpdateFragment.setArguments(bundle);
            transaction.replace(R.id.frameLayout,agencyMemberProfileUpdateFragment );

            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.idAgencyMemberDetailRemoveMemberButton.setOnClickListener(view -> {

            deleteMember(memberId);
        });
    }

    private void deleteMember(String id) {
        Call<SignUpResponse> call = RestClient.makeAPI().deleteMember(id);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Member removed successfully", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new AgencyProfileFragment());
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Failed to remove member", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
