package com.example.rescueagency.agency;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.UserAgencyChatActivity;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Agent;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.GetNewEmergencyRequestRootClass;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.IncidentInformation;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.Result;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.User;
import com.example.rescueagency.databinding.FragmentAgencyStatusUpdateBinding;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgencyStatusUpdateFragment extends Fragment {

    FragmentAgencyStatusUpdateBinding binding;
    String roomId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgencyStatusUpdateBinding.inflate(inflater, container, false);
        click();
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        assert mainActivity != null;
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        String requestId = getArguments().getString("requestId");
        getEmergencyAlertMessage(requestId);
        return binding.getRoot();
    }
    private void click(){
        binding.idAgencyStatusUpdateDetailBackButIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        binding.idChatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                AgencyUserChatFragment agencyUserChatFragment = new AgencyUserChatFragment();
//                Bundle bundle = new Bundle();
                Intent intent=new Intent(requireContext(), UserAgencyChatActivity.class);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });

        binding.idAppCompatButtonRequestSolvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.agency_req_status_update_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idBackToRequestButton);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    }
                });
            }
        });

        binding.idAppCompatButtonRequestUnsolvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.agency_req_status_update_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idBackToRequestButton);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    }
                });
            }
        });
    }
    private void getEmergencyAlertMessage(String requestId){
        Call<GetNewEmergencyRequestRootClass> responseCall= RestClient.makeAPI().getRequestInfo(requestId);
        responseCall.enqueue(new Callback<GetNewEmergencyRequestRootClass>() {
            @Override
            public void onResponse(@NonNull Call<GetNewEmergencyRequestRootClass> call, @NonNull Response<GetNewEmergencyRequestRootClass> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus()==200){
                        Result result = response.body().getResult();
                        User user = result.getUser();
                        IncidentInformation info = result.getIncident_information();
                        Agent agent = result.getAgent();
                        roomId = info.getRoom_id();
                        binding.idAgencyEmergencyReqDetailName.setText(user.getUser_name());
                        binding.idAgencyEmergencyReqDetailDateTV.setText(info.getDate());
                        binding.idAgencyEmergencyReqDetailDescribe.setText(info.getDescribe_incident());
                        try {
                            binding.viewPager.setAdapter(new ImagePreviewAdapter(info.getIncident_images(), requireContext()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(requireContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "Error "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNewEmergencyRequestRootClass> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static class ImagePreviewAdapter extends PagerAdapter {

        List<String> uris;
        Context context;
        LayoutInflater mLayoutInflater;

        public ImagePreviewAdapter(List<String> images, Context context) {
            this.uris = images;
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return uris.size();
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            // inflating the item.xml
            View itemView = mLayoutInflater.inflate(R.layout.image_preview_layout, container, false);

            // referencing the image view from the item.xml file
            ImageView imageView = itemView.findViewById(R.id.imagePreview);

            // setting the image in the imageView
            Glide.with(context).load(uris.get(position)).into(imageView);

            // Adding the View
            Objects.requireNonNull(container).addView(itemView);

            return itemView;
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((LinearLayout) object);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {

            container.removeView((LinearLayout) object);
        }
    }


}