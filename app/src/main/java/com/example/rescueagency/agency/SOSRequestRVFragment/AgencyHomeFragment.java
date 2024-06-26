package com.example.rescueagency.agency.SOSRequestRVFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.ProfileFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.HomeFragment.AdminAgencyListHolder;
import com.example.rescueagency.agency.AgencyEmergencyReqListFragment;
import com.example.rescueagency.agency.AgencyEmergencyRequestDetailFragment;
import com.example.rescueagency.agency.AgencyMemberProfileUpdateFragment;
import com.example.rescueagency.agency.AgencyNotificationFragment;
import com.example.rescueagency.agency.AgencySOSRequestDetailFragment;
import com.example.rescueagency.alertsentFragment;
import com.example.rescueagency.databinding.FragmentAgencyHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class AgencyHomeFragment extends Fragment {

    FragmentAgencyHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyHomeBinding.inflate(inflater, container, false);
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        List<AgencySOSReqList> data = new ArrayList<>();
        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
        recycle(data);
        tabLayout();
        return binding.getRoot();
    }
    private void tabLayout(){
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){

                        List<AgencySOSReqList> data = new ArrayList<>();
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","sos","medical") );
                        recycle(data);
                    }
                  else{

                        List<AgencySOSReqList> data = new ArrayList<>();
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        data.add(new AgencySOSReqList("Name :","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf","Alert Type :","medical") );
                        recycle(data);

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
    private void click(){
        binding.idChatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new AgencyNotificationFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }




    private void recycle(List<AgencySOSReqList> data){
        binding.idHomeSOSReqListRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idHomeSOSReqListRecyclerView.setAdapter(new AgencySOSReqListHolder(data, requireActivity()));

    }
}