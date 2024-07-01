package com.example.rescueagency;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rescueagency.agency.NewRequestData;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentBookingBinding;

import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {
    FragmentBookingBinding binding;
    private String describe;
    private String selectedAgency;
    String categoryId;
    Bundle bundle;
    List<MultipartBody.Part> images;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(15), uri -> {
            if (uri != null) {
                Toast.makeText(requireContext(), "Media Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "No Media Selected", Toast.LENGTH_SHORT).show();
            }
        });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        clickListener();

         bundle=getArguments();
//         categoryId= bundle.getString("categoryId",null);
        MainActivity mainActivity=(MainActivity) getActivity();

        assert mainActivity != null;
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Listen for the result from MapsFragment
        getParentFragmentManager().setFragmentResultListener("agencySelection", this, (requestKey, result) -> {
            selectedAgency = result.getString("selectedAgency");
            binding.idRequestChooseTeamButton.setText(selectedAgency);
        });
    }

    private void clickListener() {
        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(requireContext(),TakePhotoActivity.class));
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.frameLayout, new alertsentFragment()).commit();
                }
            }
        });

        binding.idRequestChooseTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);

                MapsFragment mapsFragment=new MapsFragment();
                mapsFragment.setArguments(bundle);
                transaction.replace(R.id.frameLayout, mapsFragment).addToBackStack("RescueTeamSelcetionInMapFragment").commit();
//                Intent intent=new Intent(requireContext(),MapsActivity.class);
//                startActivity(intent);

            }
        });

        binding.idBookingBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
    }
    private void apiNewRequest(NewRequestData data,List<MultipartBody.Part> parts){
        Call<SignUpResponse> responseCall= RestClient.makeAPI().newRequest(data,parts);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure_new_request", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private boolean getTextField() {
        describe = Objects.requireNonNull(binding.idEdittextRequestDescribe.getText()).toString().trim();
        if (describe.isEmpty()) {
            binding.idEdittextRequestDescribe.setError("Please describe your problem");
            return false;
        }
        return true;
    }
}
