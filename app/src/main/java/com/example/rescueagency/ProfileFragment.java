package com.example.rescueagency;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

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

import com.example.rescueagency.LoginActivityFragments.LoginFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
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

    AppCompatImageView logoutButton;
    //textview
    AppCompatTextView nameAppCompatTextView;
    AppCompatTextView emailAppCompatTextView;
    AppCompatTextView phoneAppCompatTextView;
    AppCompatTextView addressAppCompatTextView;
    AppCompatTextView dobAppCompatTextView;
    AppCompatImageView imageView;
    SharedPreferences sf;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    profileImageUpdateApi(uri);
                } else {
                    Toast.makeText(requireContext(), "No Media Selected", Toast.LENGTH_SHORT).show();
                }
            });

    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        clickListener();
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

    private void init(View view){
        //appcompat button
        updateButton = view.findViewById(R.id.id_profile_update_button);
        changepassword = view.findViewById(R.id.id_profile_change_password_button);
        //textview
        nameAppCompatTextView= view.findViewById(R.id.idProfileNameTV);
        emailAppCompatTextView = view.findViewById(R.id.idProfileEmailTV);
        phoneAppCompatTextView = view.findViewById(R.id.idProfileMobileTV);
        addressAppCompatTextView = view.findViewById(R.id.idProfileAddressTV);
        dobAppCompatTextView = view.findViewById(R.id.idProfileDOBTV);
        //imageview
        profilename = view.findViewById(R.id.id_profile_image);
        profileemail = view.findViewById(R.id.id_profile_email);
        imageView= view.findViewById(R.id.id_booking_back_arrow);
        profilephone = view.findViewById(R.id.id_profile_mobile);
        profileaddress = view.findViewById(R.id.id_profile_address);
        profiledob = view.findViewById(R.id.id_profile_dob);
        logoutButton = view.findViewById(R.id.idProfileLogoutIV);


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

    private void profileImageUpdateApi(Uri uri){
        String userId=sf.getString(Constant.SF_USERID,null);
        assert userId != null;
        RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain"),userId);
        File file = new File(FileUtils.getPath(getContext(),uri));
        RequestBody requestBody1= RequestBody.create(MediaType.parse("image/*"),file);
        Call<SignUpResponse> responseCall = RestClient.makeAPI().updateProfileImage(requestBody, MultipartBody.Part.createFormData("file",file.getName(),requestBody1));
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    SignUpResponse signUpResponse=response.body();

                        Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), "response false", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming you want to replace the current ProfileFragment with a new instance
                Fragment newUpadteProfileFragment = new UpdateProfileFragment();
              FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, newUpadteProfileFragment ); // replace with the container id of your fragment
                transaction.addToBackStack(null); // add to back stack if you want to allow 'back' navigation
                transaction.commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
