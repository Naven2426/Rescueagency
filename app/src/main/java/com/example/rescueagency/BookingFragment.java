package com.example.rescueagency;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentBookingBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class    BookingFragment extends Fragment {
    FragmentBookingBinding binding;

    private static ImagePreviewAdapter imagePreviewAdapter;
    private Bundle bundle;
    int PERMISSION_ID = 44;
    List<MultipartBody.Part> images=new ArrayList<>();
    private String agentId,agentName,userId,userName,userMobile,description,categoryId,typeOfIncident,status,latitude,longitude;
    public  List<Uri> uriImages=new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;
    SharedPreferences sharedPreferences;
    SharedPreferences sf;

    private boolean getTextField() {
        description = Objects.requireNonNull(binding.idEdittextRequestDescribe.getText()).toString().trim();
        status = "NEW";
        userId = sharedPreferences.getString(Constant.SF_USERID, null);
        userName = sharedPreferences.getString(Constant.SF_NAME, null);
        userMobile = sharedPreferences.getString(Constant.SF_PHONE, null);
        addMultipartObjects(imagePreviewAdapter.getUris());
        if (description.isEmpty()) {
            binding.idEdittextRequestDescribe.setError("Please Describe Your Problem");
            return false;
        }
        return true;
    }
    private void addMultipartObjects(List<Uri> uris){
         for(Uri uri:uris) {
             File file= new File(FileUtils.getPath(requireContext(),uri));
             RequestBody requestBody= RequestBody.create(MediaType.parse("*/*"),file);
             images.add(MultipartBody.Part.createFormData("file",file.getName(),requestBody));
         }
    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
//            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(15), uri -> {
            if (uri != null) {
//                if(uriImages==null) {
//                    uriImages=uri;
//                }else{
//                    uriImages.addAll(uri);
//                }
                if(imagePreviewAdapter==null){
                    binding.showImagesVP.setAdapter(null);
                }else{
                    imagePreviewAdapter.setUris(uri);
                }
            } else {
                Toast.makeText(requireContext(), "No Media Selected", Toast.LENGTH_SHORT).show();
            }
        });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        clickListener();
        sharedPreferences= getActivity().getSharedPreferences(Constant.SF_NAME,Context.MODE_PRIVATE);
         bundle=getArguments();
        assert bundle != null;
        categoryId = bundle.getString("categoryId",null);
        typeOfIncident = bundle.getString("categoryName",null);
         sf= requireActivity().getSharedPreferences(Constant.SF_LAT_LONG_NAME,Context.MODE_PRIVATE);
         agentName = sf.getString(Constant.SF_TEAM_NAME_FOR_NEW_REQUEST,null);
         agentId   = sf.getString(Constant.SF_AGENT_ID_FOR_NEW_REQUEST,null);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        getLastLocation();
        if(agentName!=null && agentId!=null){
            binding.agencyNameACTV.setVisibility(View.VISIBLE);
            binding.agencyNameACTV.setText(agentName);
        }
//         categoryId= bundle.getString("categoryId",null);
        MainActivity mainActivity=(MainActivity) getActivity();
        assert mainActivity != null;

        TakePhotoActivity.fragment=requireContext();

        imagePreviewAdapter = new ImagePreviewAdapter(uriImages,requireContext());
        binding.showImagesVP.setAdapter(imagePreviewAdapter);
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }

    public static void setImageViewPager(List<Uri> uris,Context context,ImagePreviewAdapter adapter){
       if(imagePreviewAdapter==null){
           Toast.makeText(context, "new Object created", Toast.LENGTH_SHORT).show();
           imagePreviewAdapter=adapter;
       }else{
           Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show();
           imagePreviewAdapter.setUris(uris);
       }

    }
    private void clickListener() {
        binding.idAddProofCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.choose_img_upload_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.setCancelable(true); // Make the dialog cancellable
                dialog.show();

                CardView okay_text = dialog.findViewById(R.id.idCameraCV);
                CardView cancel_text = dialog.findViewById(R.id.idAlbumsCV);

                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(requireContext(), TakePhotoActivity.class));
                    }
                });
                cancel_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                .build());
                    }
                });

// Optional: Handle the back press specifically within the dialog
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                });

            }
        });


        binding.idRequestChooseTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setArguments(bundle);
                transaction.replace(R.id.frameLayout, mapsFragment).addToBackStack("RescueTeamSelectionInMapFragment").commit();
//                Intent intent=new Intent(requireContext(),MapsActivity.class);
//                startActivity(intent);

            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    RequestBody categoryIdRB = RequestBody.create(MediaType.parse("text/plain"), categoryId);
                    RequestBody agentIdRB = RequestBody.create(MediaType.parse("text/plain"), agentId);
                    RequestBody agentNameRB = RequestBody.create(MediaType.parse("text/plain"), agentName);
                    RequestBody userIdRB = RequestBody.create(MediaType.parse("text/plain"), userId);
                    RequestBody userNameRB = RequestBody.create(MediaType.parse("text/plain"), ""+userName);
                    RequestBody userMobileRB = RequestBody.create(MediaType.parse("text/plain"), userMobile);
                    RequestBody descriptionRB = RequestBody.create(MediaType.parse("text/plain"), description);
                    RequestBody typeOfIncidentRB = RequestBody.create(MediaType.parse("text/plain"), typeOfIncident);
                    RequestBody statusRB = RequestBody.create(MediaType.parse("text/plain"), status);
                    RequestBody latitudeRB = RequestBody.create(MediaType.parse("text/plain"), latitude);
                    RequestBody longitudeRB = RequestBody.create(MediaType.parse("text/plain"), longitude);
                    apiNewRequest(agentIdRB,agentNameRB,userIdRB,userNameRB,userMobileRB,descriptionRB,categoryIdRB,typeOfIncidentRB,statusRB,latitudeRB,longitudeRB,images);
                }
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

    private void apiNewRequest(RequestBody agentId, RequestBody agentName, RequestBody userId, RequestBody userName, RequestBody userMobile,
                               RequestBody description,RequestBody categoryId, RequestBody typeOfIncident, RequestBody status, RequestBody latitude
            , RequestBody longitude, List<MultipartBody.Part> parts) {
        Call<SignUpResponse> responseCall= RestClient.makeAPI().newRequest(agentId,agentName,userId,userName,userMobile,description,categoryId,typeOfIncident,status,latitude,longitude,parts);
        responseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpResponse> call, @NonNull Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
//                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
//                    transaction.replace(R.id.frameLayout, new alertsentFragment()).commit();
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
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

        // if location is enabled

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (PermissionClass.checkLocationPermissions(requireContext())) {

            // check if location is enabled
            if (PermissionClass.isLocationEnabled(requireContext())) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude() + "";
                            longitude = location.getLongitude() + "";
                            binding.idEdittextRequestDescribe.setText(latitude+" "+longitude);
                        }
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        }
    }
    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
            }
        }
    }


    public static class ImagePreviewAdapter extends PagerAdapter {

        List<Uri> uris;
        Context context;
        LayoutInflater mLayoutInflater;

        public ImagePreviewAdapter(List<Uri> uris, Context context) {
            this.uris = uris;
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setUris(List<Uri> uris){
            this.uris.addAll(uris);
//            uriImages.addAll(uris);
            notifyDataSetChanged();
        }
        public List<Uri> getUris(){
            return uris;
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
            imageView.setImageURI(uris.get(position));

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
