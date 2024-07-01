package com.example.rescueagency.admin;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentAdminAddCategoryBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminAddCategoryFragment extends Fragment {
    FragmentAdminAddCategoryBinding binding;

    private String categoryname,proofName,categorydescription;

    private Uri proof;
    private MultipartBody.Part part;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(hideAnimation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        binding = FragmentAdminAddCategoryBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }
    public void click(){
        binding.idAddAgencyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });



        binding.idAddAgencyRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                resultLauncher.launch(intent);
            }
        });

        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getTextField()) {
                    final RequestBody categoryName1=RequestBody.create(MediaType.parse("text/plain"),categoryname);
                    apiAddCategory(categoryName1,part);

                }
            }
        });

    }


    private void apiAddCategory(RequestBody categoryname,MultipartBody.Part pdf){
        Call<SignUpResponse> responseCall= RestClient.makeAPI().addCategory(categoryname,pdf);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus()==200){
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                    }
                    else{
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "Response Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Intent intent= o.getData();
                    if(intent!=null){
                        proof=intent.getData();
                        if(proof!=null){
                            String path=getPathFromUri(proof);
                            if(path!=null){
                                File file=new File(path);
                                proofName=file.getName();
                                binding.idAddAgencyDescriptionTV.setText(proofName);
                                RequestBody requestBody=RequestBody.create(MediaType.parse("*/*"),file);
                                part= MultipartBody.Part.createFormData("file",file.getName(),requestBody);
                            }else Toast.makeText(requireContext(), "unable to get file path", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });




    private boolean getTextField(){
        categoryname = binding.idAddNameET.getText().toString();
//        categorydescription = binding.idAddTypeET.getText().toString();
        if(categoryname.isEmpty()){
            binding.idAddNameET.setError("Enter Category Name");
            return false;
        }
        if(proof==null){
            Toast.makeText(getContext(), "Please Select Proof", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(categorydescription.isEmpty()){
//            binding.idAddTypeET.setError("Enter Category Description");
//            return false;
//        }
        return true;

    }


    private String getPathFromUri(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        try (Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                File file = new File(requireActivity().getExternalFilesDir(null), displayName);
                try (InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                     OutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    filePath = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }



}