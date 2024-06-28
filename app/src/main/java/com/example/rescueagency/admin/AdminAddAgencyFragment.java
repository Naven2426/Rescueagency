package com.example.rescueagency.admin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rescueagency.Constant;
import com.example.rescueagency.FileUtils;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.admin.spinner.CustomSpinner;
import com.example.rescueagency.admin.spinner.Data;
import com.example.rescueagency.admin.spinner.Fruit;
import com.example.rescueagency.admin.spinner.FruitAdapter;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentAdminAddAgencyBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddAgencyFragment extends Fragment  implements CustomSpinner.OnSpinnerEventsListener{

    private String agencyName,mobile,address,totalMember,proofName,email,userName,password
            ,latitude,longitude,confirmPassword,categoryName,categoryId;
    private Uri proof;
    private MultipartBody.Part part;
    private static final int CREATE_FILE = 1;
    private SupportMapFragment supportMapFragment;
    private SharedPreferences sf;
    FragmentAdminAddAgencyBinding binding;
    private OnMapReadyCallback mapCallBack= new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            String latitude=sf.getString(Constant.SF_LATITUDE,null);
            String longitude = sf.getString(Constant.SF_LONGITUDE,null);
            if(latitude!=null && longitude!=null){
                LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
            }
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout,new MarkLocationFragment()).addToBackStack("MarkLocationFragment").commit();
//                    startActivity(new Intent(requireContext(),MarkLocationActivity.class));
                }
            });
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        binding = FragmentAdminAddAgencyBinding.inflate(inflater, container, false);
        Toast.makeText(requireContext(), "Fragment", Toast.LENGTH_SHORT).show();
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idAddAgencyMarkLocation);
        sf=requireContext().getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(mapCallBack);
        }
        addAgency();
        dropDown();
        MainActivity mainActivity=(MainActivity) getActivity();
        Animation Animation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(Animation);
        Animation hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.hide_bottom_navigation);
        mainActivity.findViewById(R.id.bottomNavigationView).startAnimation(hideAnimation);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        apiGetCategory();
        return binding.getRoot();
    }
    private void dropDown(){

    }
    private void apiGetCategory(){
        Call<GetCategoryResponse> responseCall= RestClient.makeAPI().getCategory();
        responseCall.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                if(response.isSuccessful()){
                    List<Fruit> data=new ArrayList<>();
                    for(int i=0;i<response.body().getData().size();i++){
                        GetCategoryResponse.Data d=response.body().getData().get(i);
                        data.add(new Fruit(d.getCategory_name(),d.getImage(),d.getCategory_id()));
                    }
                    dropDown(data);
                }
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dropDown(List<Fruit> data){
        binding.spinnerFruits.setSpinnerEventsListener(this);
        FruitAdapter adapter = new FruitAdapter(requireContext(), data);
        binding.spinnerFruits.setAdapter(adapter);
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
                                   part=MultipartBody.Part.createFormData("file",file.getName(),requestBody);
                               }else Toast.makeText(requireContext(), "unable to get file path", Toast.LENGTH_SHORT).show();
                           }
                       }else{
                        Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show();
                       }
                }
            });


    public void addAgency() {
        binding.idAddAgencyRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED ||
//                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");
                    resultLauncher.launch(intent);
//                }else{
////                    Toast.makeText(getContext(), "Please Allow Permission", Toast.LENGTH_SHORT).show();
////                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED);
//                    requestPermission();
//                }

            }
        });
        binding.idAddAgencyBackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
            });
        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (getTextField()) {

                }
            }
        });
    }
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        binding.spinnerFruits.setBackground(getResources().getDrawable(R.drawable.bg_spinner_fruit_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        binding.spinnerFruits.setBackground(getResources().getDrawable(R.drawable.bg_spinner_fruit));
    }
    private void apiRegisterAgency(String agencyName,String typeOfService,String address,
                                   String mobile,String totalMembers,MultipartBody.Part pdf,String email,
                                   String userName,String password,String latitude,String longitude,String categoryId){
        Call<SignUpResponse> responseCall= RestClient.makeAPI().agencyRegister(agencyName,typeOfService,address,
                mobile,totalMembers,pdf,email,userName,password,"",latitude,longitude,categoryId);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus()==200){
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

            }
        });

    }

    private boolean getTextField(){
        agencyName = binding.idAddAgencyAgencyNameET.getText().toString().trim();
        mobile = binding.idAddAgencyMobileET.getText().toString().trim();
        address = binding.idAddAgencyAddressET.getText().toString().trim();
        totalMember = binding.idAddAgencyTotalMemberET.getText().toString().trim();
        email=binding.idAddAgencyAgencyEmailET.getText().toString();
        userName=binding.idAddAgencyUsernameET.getText().toString();
        password=binding.idAddAgencyPasswordET.getText().toString();
        confirmPassword=binding.idAddAgencyConfirmPasswordET.getText().toString();

        if(agencyName.isEmpty()){
            binding.idAddAgencyAgencyNameET.setError("Enter Agency Name");
            return false;
        }
        if(mobile.isEmpty()){
            binding.idAddAgencyMobileET.setError("Enter Mobile Number");
            return false;
        }
        if(address.isEmpty()){
            binding.idAddAgencyAddressET.setError("Enter Address");
            return false;
        }
        if(totalMember.isEmpty()){
            binding.idAddAgencyTotalMemberET.setError("Enter Total Member");
            return false;
        }
        if(proof==null){
            Toast.makeText(getContext(), "Please Select Proof", Toast.LENGTH_SHORT).show();
            return false;
        }
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