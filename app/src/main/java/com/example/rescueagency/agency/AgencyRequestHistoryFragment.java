package com.example.rescueagency.agency;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.ContextCompat;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryList;
import com.example.rescueagency.agency.AgencyReqHistoryList.AgencyReqHistoryListHolder;
import com.example.rescueagency.agency.SOSRequestRVFragment.AgencySOSReqList;
import com.example.rescueagency.apiresponse.newrequest.Data;
import com.example.rescueagency.apiresponse.newrequest.NewRequestRootClass;
import com.example.rescueagency.databinding.FragmentAgencyRequestHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgencyRequestHistoryFragment extends Fragment {

    FragmentAgencyRequestHistoryBinding binding;
    SharedPreferences sf;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgencyRequestHistoryBinding.inflate(inflater, container, false);
        setupTabLayout();
        sf = requireActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(animation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        String agentId = sf.getString(Constant.SF_USERID, "");
        getRequest(agentId);
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        List<AgencyReqHistoryList> data = new ArrayList<>();
        data.add(new AgencyReqHistoryList("Status :", "dsf", "sdf", "Sdf"));

        binding.idAgencyRequestRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idAgencyRequestRV.setAdapter(new AgencyReqHistoryListHolder(data, requireActivity()));
    }

    private void setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) { // "Rejected" tab position
                    tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                } else {
                    tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle reselection if needed
            }
        });
    }
    private void getRequest(String agentId){
        Call<NewRequestRootClass> call= RestClient.makeAPI().getRequest(agentId,"ONGOING");
        call.enqueue(new Callback<NewRequestRootClass>() {
            @Override
            public void onResponse(@NonNull Call<NewRequestRootClass> call, @NonNull Response<NewRequestRootClass> response) {
                if(response.isSuccessful()){
                    NewRequestRootClass newRequestRootClass=response.body();
                    List<AgencyReqHistoryList> list = new ArrayList<>();
                    for(int i = 0; i< Objects.requireNonNull(newRequestRootClass).getData().size(); i++){
                        Data data = newRequestRootClass.getData().get(i);
                        list.add(new AgencyReqHistoryList(data.getStatus(), ""+data.getRequest_id(), data.getType_of_incident(), data.getDate()));
                    }
                    binding.idAgencyRequestRV.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.idAgencyRequestRV.setAdapter(new AgencyReqHistoryListHolder(list, requireActivity()));
                }else{
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewRequestRootClass> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error",t.getMessage());
            }
        });
    }
}
