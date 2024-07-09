package com.example.rescueagency.agency.SOSRequestRVFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.agency.AgencyNotificationFragment;
import com.example.rescueagency.agency.NewRequestData;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Agent;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.GetNewEmergencyRequestRootClass;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.IncidentInformation;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Result;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.User;
import com.example.rescueagency.apiresponse.newrequest.Data;
import com.example.rescueagency.apiresponse.newrequest.NewRequestRootClass;
import com.example.rescueagency.databinding.FragmentAgencyHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgencyHomeFragment extends Fragment {

    FragmentAgencyHomeBinding binding;
    NewRequestRootClass responseBody;
    SharedPreferences sf;
    String agentId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyHomeBinding.inflate(inflater, container, false);
        sf = requireActivity().getSharedPreferences(Constant.SF_NAME, Context.MODE_PRIVATE);
        agentId = sf.getString(Constant.SF_USERID, "");
        MainActivity mainActivity=(MainActivity) getActivity();
        assert mainActivity != null;
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(showAnimation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        getNewRequest(agentId);
        return binding.getRoot();
    }

    private void tabLayout(){

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                        List<AgencySOSReqList> data = new ArrayList<>();
                    data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","sos") );
                    data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","sos") );
                    data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","sos") );
                        recycle(data);
                    }
                  else{
                    getNewRequest(agentId);
                    if(responseBody!=null){
                        List<AgencySOSReqList> list = new ArrayList<>();
                        for(int i=0;i<responseBody.getData().size();i++){
                            Data data = responseBody.getData().get(i);
                            list.add(new AgencySOSReqList(data.getUser_name(),""+data.getRequest_id(),data.getDate(),data.getType_of_incident()));
                        }
                        recycle(list);
                        tabLayout();
                    }
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


    private void recycle(List<AgencySOSReqList> data){
        binding.idHomeSOSReqListRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idHomeSOSReqListRecyclerView.setAdapter(new AgencySOSReqListHolder(data, requireActivity()));
    }
    private void getNewRequest(String agentId){
        Call<NewRequestRootClass> responseCall= RestClient.makeAPI().getRequest(agentId);
        responseCall.enqueue(new Callback<NewRequestRootClass>() {
            @Override
            public void onResponse(@NonNull Call<NewRequestRootClass> call, @NonNull Response<NewRequestRootClass> response) {
                if(response.isSuccessful()){
                    NewRequestRootClass responseBody=response.body();
                    List<AgencySOSReqList> list = new ArrayList<>();
                    for(int i = 0; i< Objects.requireNonNull(responseBody).getData().size(); i++){
                        Data data = responseBody.getData().get(i);
                        list.add(new AgencySOSReqList(data.getUser_name(),""+data.getRequest_id(),data.getDate(),data.getType_of_incident()));
                    }
                    recycle(list);
                    tabLayout();
                    assert response.body() != null;
                    Toast.makeText(requireContext(), "Success "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireContext(), "Error "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<NewRequestRootClass> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}