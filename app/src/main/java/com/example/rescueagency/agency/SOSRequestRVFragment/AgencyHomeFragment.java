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

import java.util.ArrayList;
import java.util.List;


public class AgencyHomeFragment extends Fragment {

    FragmentAgencyHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyHomeBinding.inflate(inflater, container, false);
//        recycle();
        click();
        return binding.getRoot();
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




    private void recycle(){
        List<AgencySOSReqList> data = new ArrayList<>();
        data.add(new AgencySOSReqList("1234567890","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf") );
        data.add(new AgencySOSReqList("1234567890","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf") );
        data.add(new AgencySOSReqList("1234567890","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf") );
        data.add(new AgencySOSReqList("1234567890","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf") );
        data.add(new AgencySOSReqList("1234567890","bhjb","jhrgv","zfds","fsdf","sdfs","sdfdf") );
        binding.idHomeSOSReqListRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.idHomeSOSReqListRecyclerView.setAdapter(new AgencySOSReqListHolder(data, requireActivity()));

    }
}