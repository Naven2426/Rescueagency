package com.example.rescueagency.check_status_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.checkstatus.Agent;
import com.example.rescueagency.apiresponse.checkstatus.CheckStatusResponseRootClass;
import com.example.rescueagency.apiresponse.checkstatus.Data;
import com.example.rescueagency.apiresponse.checkstatus.IncidentInformation;
import com.example.rescueagency.apiresponse.checkstatus.User;
import com.example.rescueagency.apiresponse.oldrequest.OldRequestRootClass;
import com.example.rescueagency.databinding.FragmentRequestHistoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHistoryFragment extends Fragment {

    private FragmentRequestHistoryBinding binding;
    private Button buttonCheckStatus, buttonHistory;
    private TabItem history,checkStatus;
    List<CheckStatusList> data = new ArrayList<>();

    SharedPreferences sf;
    String userId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);
//        binding.checkStatus.setSelected(true);
//        setupRecyclerView(data);
        tabLayout();
        sf = requireActivity().getSharedPreferences(Constant.SF_NAME , Context.MODE_PRIVATE);
         userId=sf.getString(Constant.SF_USERID,"");

        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
    private void tabLayout(){
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    checkStatusApi(userId);
                    Toast.makeText(requireContext(), "check status", Toast.LENGTH_SHORT).show();
                }else{
                    oldRequestApi(userId);
                    Toast.makeText(requireContext(), "history", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void oldRequestApi(String userId){
        Call<OldRequestRootClass> response= RestClient.makeAPI().oldRequest(userId);
        response.enqueue(new Callback<OldRequestRootClass>() {
            @Override
            public void onResponse(@NonNull Call<OldRequestRootClass> call, @NonNull Response<OldRequestRootClass> response) {
                if(response.isSuccessful()){
                    OldRequestRootClass checkStatusResponseRootClass=response.body();
                    List<CheckStatusList> data = new ArrayList<>();
                    
                    setupRecyclerView(data);
                }else{
                    Toast.makeText(requireContext(), "response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<OldRequestRootClass> call, @NonNull Throwable t) {
                Log.e("error",t.getMessage());
                Toast.makeText(requireContext(), "Something went wrong "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkStatusApi(String userId){
        Call<CheckStatusResponseRootClass> response= RestClient.makeAPI().checkStatus(userId);
        response.enqueue(new Callback<CheckStatusResponseRootClass>() {
            @Override
            public void onResponse(@NonNull Call<CheckStatusResponseRootClass> call, @NonNull Response<CheckStatusResponseRootClass> response) {
                if(response.isSuccessful()){
                    CheckStatusResponseRootClass checkStatusResponseRootClass=response.body();
                    List<CheckStatusList> data = new ArrayList<>();
                    assert checkStatusResponseRootClass != null;
                    for(Data data1:checkStatusResponseRootClass.getData()){
                        IncidentInformation incidentInfo=data1.getIncident_information();
                        Agent agent = data1.getAgent();
                        User user = data1.getUser();
                        data.add(new CheckStatusList(""+incidentInfo.getRoom_id(),incidentInfo.getStatus(),incidentInfo.getDescribe_incident(),incidentInfo.getDate(),""+incidentInfo.getRequest_id()));
                    }
                    setupRecyclerView(data);
                }else{
                    Toast.makeText(requireContext(), "response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckStatusResponseRootClass> call, @NonNull Throwable t) {
                Log.e("error",t.getMessage());
                Toast.makeText(requireContext(), "Something went wrong "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerView(List<CheckStatusList> data) {
        CheckStatusListHolder object = new CheckStatusListHolder(data, requireActivity());
        binding.idRequestHistoryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.idRequestHistoryList.setAdapter(object);
    }
}
