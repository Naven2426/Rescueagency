package com.example.rescueagency;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueagency.apiresponse.agencyinfo.AgencyInfoRoot;
import com.example.rescueagency.apiresponse.agencyinfo.Data;
import com.example.rescueagency.apiresponse.agencyinfo.Member;
import com.example.rescueagency.apiresponse.map.mydistance.Elements;
import com.example.rescueagency.apiresponse.map.mydistance.GetDistanceRootResponse;
import com.example.rescueagency.apiresponse.map.mydistance.Rows;
import com.example.rescueagency.user_agency_member_list_view.UserRescueTeamMemberListHolder;
import com.example.rescueagency.user_agency_member_list_view.user_rescue_team_member_list;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheet extends BottomSheetDialogFragment {
    BottomSheetDialog dialog;
    BottomSheetBehavior sheetBehavior;
    String id;
    SharedPreferences sf;
    private String teamName,agentId;
    public BottomSheet(String id){
        this.id=id;
    }
    View rootView;

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle bundle){
        dialog = (BottomSheetDialog)  super.onCreateDialog(bundle);
        return dialog;
    }
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle){
        rootView=layoutInflater.inflate(R.layout.bottom_sheet_layout_for_maps_activity,viewGroup,false);
        return rootView;
    }
    public void onViewCreated(@NonNull View view,@Nullable Bundle bundle)
    {
        super.onViewCreated(view,bundle);
        sf=requireActivity().getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        sheetBehavior= BottomSheetBehavior.from((View) view.getParent());
        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        ConstraintLayout relativeLayout=dialog.findViewById(R.id.parentLayout);
        assert relativeLayout != null;
        relativeLayout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        apishow(id,dialog);

    }
    private void calculateDistance(LatLng origin, LatLng destination){
        Map<String, String> myMapQuery = new HashMap<>();
        myMapQuery.put("key", Constant.KEY);
        myMapQuery.put("origins", origin.latitude + "," + origin.longitude);
        myMapQuery.put("destinations", destination.latitude + "," + destination.longitude);
        myMapQuery.put("mode", "Driving");
        Call<GetDistanceRootResponse> responseCall = RestClient.makeMapAPI().getDistanceInfoMyWay(myMapQuery);
        responseCall.enqueue(new Callback<GetDistanceRootResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDistanceRootResponse> call, @NonNull Response<GetDistanceRootResponse> response) {
                if(response.isSuccessful()){
                    GetDistanceRootResponse main=response.body();
                    assert main != null;
                    if(main.getStatus().equalsIgnoreCase("OK")){
                        Rows rows=main.getRows().get(0);
                        Elements elements=rows.getElements().get(0);
                        if(elements.getStatus().equalsIgnoreCase("OK")) {
                            String distance=elements.getDistance().getText();
                            String duration=elements.getDuration().getText();
                            Log.e("TAG", "distance : " + distance + " duration: " + duration);
                            Log.e("TAG", "double value : " + distance);
                        }

                    }else{
                        Toast.makeText(requireContext(), main.getError_message(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDistanceRootResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    private void apishow(String id,BottomSheetDialog dialog) {
        Call<AgencyInfoRoot> responseCall = RestClient.makeAPI().getTeam(id);
        responseCall.enqueue(new retrofit2.Callback<AgencyInfoRoot>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AgencyInfoRoot> call, @NonNull Response<AgencyInfoRoot> response) {
                if (response.isSuccessful()) {
                    AgencyInfoRoot agencyInfoRoot = response.body();
                    assert agencyInfoRoot != null;
                    if (agencyInfoRoot.getStatus() == 200) {
                        Data data = agencyInfoRoot.getData();
                        teamName=data.getTeam_name();
                        agentId=""+data.getTeam_id();
                        TextView idRescueTeamViewTeamName = dialog.findViewById(R.id.id_rescue_team_view_team_name);
                        TextView idRescueTeamViewAddress = dialog.findViewById(R.id.id_rescue_team_view_address);
                        TextView idRescueTeamViewServices = dialog.findViewById(R.id.id_rescue_team_view_services);
                        TextView idRescueTeamViewPhoneNumber = dialog.findViewById(R.id.id_rescue_team_view_phone_number);

                        assert idRescueTeamViewTeamName != null;
                        idRescueTeamViewTeamName.setText(data.getTeam_name());
                        assert idRescueTeamViewAddress != null;
                        idRescueTeamViewAddress.setText(data.getTeam_address());
                        assert idRescueTeamViewServices != null;
                        idRescueTeamViewServices.setText(data.getType_of_service());
                        assert idRescueTeamViewPhoneNumber != null;
                        idRescueTeamViewPhoneNumber.setText(""+data.getTeam_contact());
                        List<user_rescue_team_member_list> memberList = new ArrayList<>();
                        AppCompatButton button=dialog.findViewById(R.id.id_rescue_team_view_confirm_agency);
                        assert button != null;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(agentId!=null && teamName!=null ){
                                    SharedPreferences.Editor editor = sf.edit();
                                    editor.putString(Constant.SF_AGENT_ID_FOR_NEW_REQUEST,agentId);
                                    editor.putString(Constant.SF_TEAM_NAME_FOR_NEW_REQUEST,teamName);
                                    editor.apply();
                                }
                                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                                transaction.popBackStack();
//                                transaction.popBackStack();
                                dialog.dismiss();
                            }
                        });
                        for(Member member : data.getMember()) {
                            memberList.add(new user_rescue_team_member_list(""+member.getMember_experience(),member.getMember_profile(),member.getMember_name(),member.getMember_role()));
                        }
                        try {
                            RecyclerView recyclerView=dialog.findViewById(R.id.idRescueTeamRecyclerView);
                            assert recyclerView != null;
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            recyclerView.setAdapter(new UserRescueTeamMemberListHolder(memberList, requireActivity()));
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
            public void onFailure (@NonNull Call <AgencyInfoRoot> call, @NonNull Throwable t){
                try {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}
