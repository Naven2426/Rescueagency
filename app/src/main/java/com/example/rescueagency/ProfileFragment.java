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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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




    AppCompatButton updateButton;
    AppCompatButton changepassword;
    //imageview
    AppCompatImageView profilename;
    AppCompatImageView profileemail;
    AppCompatImageView profilephone;
    AppCompatImageView profileaddress;
    AppCompatImageView profiledob;

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
        MainActivity mainActivity=(MainActivity) getActivity();
        BottomNavigationView bottomNavigationView =mainActivity.findViewById(R.id.bottomNavigationView);
        if(bottomNavigationView.getVisibility()==View.GONE){
            Animation showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.show_bottom_navigation);
            bottomNavigationView.startAnimation(showAnimation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
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
        imageView= view.findViewById(R.id.idProfileImage);
        profilephone = view.findViewById(R.id.id_profile_mobile);
        profileaddress = view.findViewById(R.id.id_profile_address);
        profiledob = view.findViewById(R.id.id_profile_dob);
        logoutButton = view.findViewById(R.id.idProfileLogoutIV);

    }
    private void setText(){
        SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
        userId=sf.getString(Constant.SF_USERID,null);
        String name=sf.getString(Constant.SF_NAME,null);
        String email=sf.getString(Constant.SF_EMAIL,null);
        String phone=sf.getString(Constant.SF_PHONE,null);
        String address=sf.getString(Constant.SF_ADDRESS,null);
        String dob=sf.getString(Constant.SF_DOB,null);
        String username=sf.getString(Constant.SF_USERNAME,null);
        nameAppCompatTextView.setText(name);
        emailAppCompatTextView.setText(email);
        phoneAppCompatTextView.setText(phone);
        addressAppCompatTextView.setText(address);
        dobAppCompatTextView.setText(dob);

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
    private void clickListener(){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.logout_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.show();

                androidx.appcompat.widget.AppCompatButton okay_text = dialog.findViewById(R.id.idAgencyAddedBTNButton);
                androidx.appcompat.widget.AppCompatButton cancel_text = dialog.findViewById(R.id.idAgencyAddedBTHButton);
                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sf=getActivity().getSharedPreferences(Constant.SF_NAME,MODE_PRIVATE);
                        sf.edit().clear().apply();
                        getActivity().finish();
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));

                    }

                });
                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
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

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newChangePasswordFragment = new ChangePasswordFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, newChangePasswordFragment ); // replace with the container id of your fragment
                transaction.addToBackStack(null); // add to back stack if you want to allow 'back' navigation
                transaction.commit();

            }
        });
    }

}
