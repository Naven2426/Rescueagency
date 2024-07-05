package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String userId;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;
    private MYAPI apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize Retrofit service
        apiService = RestClient.getInstance().create(MYAPI.class);

        setText();
        // Load the saved image URI
        MainActivity mainActivity = (MainActivity) getActivity();
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView.getVisibility() == View.GONE) {
            Animation showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(showAnimation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }

        initPickMediaLauncher();
        clickListener();
        return view;
    }

    private void setText() {
        SharedPreferences sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
        userId = sf.getString(Constant.SF_USERID, null);
        binding.idProfileNameTV.setText(sf.getString(Constant.SF_NAME, ""));
        binding.idProfileEmailTV.setText(sf.getString(Constant.SF_EMAIL, ""));
        binding.idProfileMobileTV.setText(sf.getString(Constant.SF_PHONE, ""));
        binding.idProfileAddressTV.setText(sf.getString(Constant.SF_ADDRESS, ""));
        binding.idProfileDOBTV.setText(sf.getString(Constant.SF_DOB, ""));
    }

    private void loadSavedImage() {
        SharedPreferences sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
        String imageUriString = sf.getString(Constant.SF_PROFILE_IMAGE_URI, null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            binding.idProfileImage.setImageURI(imageUri);
        }
    }

    private void clickListener() {
        binding.idProfileLogoutIV.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.logout_dialog_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();

            AppCompatButton okayButton = dialog.findViewById(R.id.idAgencyAddedBTNButton);
            AppCompatButton cancelButton = dialog.findViewById(R.id.idAgencyAddedBTHButton);
            okayButton.setOnClickListener(v1 -> {
                SharedPreferences sf = getActivity().getSharedPreferences(Constant.SF_NAME, MODE_PRIVATE);
                sf.edit().clear().apply();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            });
            cancelButton.setOnClickListener(v12 -> dialog.dismiss());
        });

        binding.idProfileUpdateButton.setOnClickListener(v -> {
            Fragment newUpdateProfileFragment = new UpdateProfileFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, newUpdateProfileFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.idProfileChangePasswordButton.setOnClickListener(v -> {
            Fragment newChangePasswordFragment = new ChangePasswordFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, newChangePasswordFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        binding.idProfileImage.setOnClickListener(v -> pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
    }

    private void initPickMediaLauncher() {
        pickMediaLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                // Handle the selected image URI
                // Save the URI in SharedPreferences
                uploadImage(uri);
            }
        });
    }

    private void uploadImage(Uri imageUri) {
        // Convert Uri to File
        String filePath = FileUtils.getPath(requireContext(),imageUri);
        if (filePath != null) {
            File file = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody userIdRequest = RequestBody.create(MediaType.parse("text/plain"), userId);

            // Make API call to update profile image
            Call<SignUpResponse> responseCall=RestClient.makeAPI().updateProfileImage(userIdRequest,body);
            responseCall.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if (response.isSuccessful()) {
                        SignUpResponse signUpResponse = response.body();
                        binding.idProfileImage.setImageURI(imageUri);
                        Toast.makeText(requireContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Failed to update profile image", Toast.LENGTH_SHORT).show();
                        // Handle error response
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    // Handle network failure
                    Toast.makeText(requireContext(), "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
