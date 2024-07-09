package com.example.rescueagency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.apiresponse.GetAgencies;
import com.example.rescueagency.apiresponse.agencyinfo.AgencyInfoRoot;
import com.example.rescueagency.apiresponse.agencyinfo.Data;
import com.example.rescueagency.apiresponse.agencyinfo.Member;
import com.example.rescueagency.apiresponse.map.GoogleMapResponse;
import com.example.rescueagency.apiresponse.map.getcurrentlocation.CurrentLocationRootClass;
import com.example.rescueagency.apiresponse.map.getcurrentlocation.Results;
import com.example.rescueagency.apiresponse.map.mydistance.Elements;
import com.example.rescueagency.apiresponse.map.mydistance.GetDistanceRootResponse;
import com.example.rescueagency.apiresponse.map.mydistance.Rows;
import com.example.rescueagency.databinding.FragmentBookingBinding;
import com.example.rescueagency.databinding.FragmentMapsBinding;
import com.example.rescueagency.databinding.FragmentRescueTeamDetailBinding;
import com.example.rescueagency.user_agency_member_list_view.RescueTeamDetailFragment;
import com.example.rescueagency.user_agency_member_list_view.UserRescueTeamMemberListHolder;
import com.example.rescueagency.user_agency_member_list_view.user_rescue_team_member_list;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements Callback<GetDistanceRootResponse>{

    private SupportMapFragment mapFragment;
    private final int FINE_PERMISSION_CODE = 1;
    private FragmentMapsBinding binding;
    private List<GetAgencies.Data> agencyInfo;
    SharedPreferences sf;
    List<LatLng> nearBy;
    List<String> km;
    List<String> min;
    private GoogleMap mMap;
    String categoryId;
    public static Location myLocation = null;
    private FusedLocationProviderClient mFusedLocationClient;
    int i=0;
    int kmACET;
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
//            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };
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
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap=googleMap;

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        FINE_PERMISSION_CODE);
                Toast.makeText(requireContext(), "Unable To Get Current Location", Toast.LENGTH_SHORT).show();
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        moveCamera(location);
                        if(agencyInfo!=null){
                            nearBy=new ArrayList<>();
                            for(int i=0;i<agencyInfo.size();i++){
                                GetAgencies.Data data=agencyInfo.get(i);
                                nearBy.add(new LatLng(data.getLatitude(),data.getLongitude()));
                            }
                            if(location!=null){
                                LatLng myLocation=new LatLng(location.getLatitude(),location.getLongitude());
                                Log.d("myLocation","myLocation "+location.getLatitude()+" "+location.getLongitude());
                                findNearbyAgency(myLocation,nearBy);
                            }else{
                                Toast.makeText(requireContext(), "Location is null", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(requireContext(), "AgencyInformation Is Null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
//                  binding.idChooseYourTeam.setText(""+location.getLatitude()+" "+location.getLongitude());
                    moveCamera(location);
                    Log.e("TAG", "onMyLocationClick: " + location.getLatitude() + " " + location.getLongitude());
                }
            });


//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang.get(latlang.size()-1),15));

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    LatLng location=new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                    moveCamera(location);
                    Toast.makeText(requireContext(), ""+Integer.parseInt(Objects.requireNonNull(marker.getTag()).toString()), Toast.LENGTH_SHORT).show();
                    BottomSheet bottomSheet=new BottomSheet(marker.getTag().toString());
                    bottomSheet.show(requireActivity().getSupportFragmentManager(),bottomSheet.getTag());
                    return false;
                }
            });

        }
    };
    private void moveCamera(Location location){
        if(location!=null){
            LatLng coordinate=new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate myLocation= CameraUpdateFactory.newLatLngZoom(coordinate, 15);
            mMap.animateCamera(myLocation);
        }
    }
    private void moveCamera(LatLng location){
        if(location!=null){
            CameraUpdate myLocation= CameraUpdateFactory.newLatLngZoom(location, 15);
            mMap.animateCamera(myLocation);
        }
    }
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater,container,false);
        binding.searchACET.setText("100");
        kmACET=Integer.parseInt(Objects.requireNonNull(binding.searchACET.getText()).toString());
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Bundle bundle=getArguments();
        sf=requireActivity().getSharedPreferences(Constant.SF_LAT_LONG_NAME, Context.MODE_PRIVATE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        assert getArguments() != null;
        assert bundle != null;
         categoryId=bundle.getString("categoryId",null);
        if(checkPermissions()) {
            if (isLocationEnabled()) {
                if(categoryId!=null){
                    apiCallGetAgencies(categoryId);
                }
            } else{
                Toast.makeText(requireContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        Onclick();
        return binding.getRoot();
    }

    private void calculateDistance(LatLng origin,LatLng destination){
        Map<String, String> myMapQuery = new HashMap<>();
        myMapQuery.put("key", Constant.KEY);
        myMapQuery.put("origins", origin.latitude + "," + origin.longitude);
        myMapQuery.put("destinations", destination.latitude + "," + destination.longitude);
        myMapQuery.put("mode", "Driving");
        Call<GetDistanceRootResponse> responseCall = RestClient.makeMapAPI().getDistanceInfoMyWay(myMapQuery);
        responseCall.enqueue(this);
    }

  public void Onclick(){
        binding.idMapBackReqBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });
        binding.searchIcon.setOnClickListener(v->{
            kmACET=Integer.parseInt(Objects.requireNonNull(binding.searchACET.getText()).toString());
            apiCallGetAgencies(categoryId);
        });

  }

    private Location getCurrentLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

  private void apiCallGetAgencies(String categoryId) {
        binding.searchIcon.setClickable(false);
      Call<GetAgencies> responseCall=RestClient.makeAPI().getAgencies(Integer.parseInt(categoryId),"LIVE");
      responseCall.enqueue(new Callback<GetAgencies>() {
          @Override
          public void onResponse(@NonNull Call<GetAgencies> call, @NonNull Response<GetAgencies> response) {
              if(response.isSuccessful()){
                  agencyInfo=new ArrayList<>();
                  assert response.body() != null;
                  agencyInfo.addAll(response.body().getData());
                  if (mapFragment != null) {
                      mapFragment.getMapAsync(callback);
                  }
                  binding.searchIcon.setClickable(true);
              }else{
                  binding.searchIcon.setClickable(true);
                  Toast.makeText(requireContext(), "Response "+response.isSuccessful(), Toast.LENGTH_SHORT).show();
              }
          }

          @SuppressLint("LongLogTag")
          @Override
          public void onFailure(@NonNull Call<GetAgencies> call, @NonNull Throwable t) {
              binding.searchIcon.setClickable(true);
              Log.e("onFailureGetAgenciesApiCall","onFailureGetAgenciesApiCall "+t.getMessage());
              Toast.makeText(requireContext(), "Response "+t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
  }
    private double getDouble(String str) {
        StringBuilder s=new StringBuilder();
        if(!str.isEmpty())
        {
            for(int j = 0; j < str.length(); j++)
            {
                char ch = str.charAt(j);
                if (!(ch==' ' ) && !((ch<91 && ch>64)||(ch<123 && ch>96)))
                {
                    s.append(ch);
                }
            }
        }
        else{
            Toast.makeText(requireContext(), "KM is empty", Toast.LENGTH_SHORT).show();
        }
        return Double.parseDouble(s.toString());
    }

    private void findNearbyAgency(LatLng myLocation,List<LatLng> nearBy) {
        km=new ArrayList<>();
        min=new ArrayList<>();
        try {
            for (int i = 0; i < nearBy.size(); i++) {
                calculateDistance(myLocation,nearBy.get(i));

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(),"Exception: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResponse(@NonNull Call<GetDistanceRootResponse> call, Response<GetDistanceRootResponse> response) {
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
                    Log.e("TAG", "double value : " + getDouble(distance));
                    km.add(distance);
                    min.add(duration);
                    i++;
                    if(kmACET>getDouble(distance)){
                        GetAgencies.Data data = agencyInfo.get(i);
//                        Toast.makeText(requireContext(),data.getName(),Toast.LENGTH_SHORT).show();
                        MarkerOptions marker = new MarkerOptions().position(nearBy.get(i)).title(data.getName());
                        Objects.requireNonNull(mMap.addMarker(marker)).setTag(""+data.getId());
                    }
                }

            }else{
                Toast.makeText(requireContext(), main.getError_message(), Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getContext(), "Response was not successful", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<GetDistanceRootResponse> call, Throwable t) {
        Toast.makeText(requireContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("Error", Objects.requireNonNull(t.getMessage()));
    }

}