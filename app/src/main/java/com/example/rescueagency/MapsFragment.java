package com.example.rescueagency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.apiresponse.GetAgencies;
import com.example.rescueagency.apiresponse.map.GoogleMapResponse;
import com.example.rescueagency.databinding.FragmentBookingBinding;
import com.example.rescueagency.databinding.FragmentMapsBinding;
import com.example.rescueagency.user_agency_member_list_view.RescueTeamDetailFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {

    LatLng tambaram = new LatLng(12.9249,  80.1000);
    LatLng guindy = new LatLng(13.0067,80.2206);
    private SupportMapFragment mapFragment;
    private final int FINE_PERMISSION_CODE = 1;
    private FragmentMapsBinding binding;
    private List<GetAgencies.Data> latlang;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {

            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        FINE_PERMISSION_CODE);
                return;
            }
            if(latlang!=null){
                for(int i=0;i<latlang.size();i++){
                    GetAgencies.Data data=latlang.get(i);
                    MarkerOptions markerOptions=new MarkerOptions().position(new LatLng(data.getLatitude(),data.getLongitude())).title(data.getName());
                    Objects.requireNonNull(googleMap.addMarker(markerOptions)).setTag(""+data.getId());
                }
            }
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang.get(latlang.size()-1),15));
            assert latlang != null;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlang.get(0).getLatitude(),latlang.get(0).getLongitude()),14));
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Toast.makeText(requireContext(), ""+Integer.parseInt(Objects.requireNonNull(marker.getTag()).toString()), Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction=requireActivity().getSupportFragmentManager().beginTransaction();
                    RescueTeamDetailFragment res =new RescueTeamDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("agentId",marker.getTag().toString());
                    res.setArguments(bundle);
                    transaction.replace(R.id.frameLayout,res).addToBackStack("RescueTeamDetailFragment").commit();
                    return false;
                }
            });

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater,container,false);
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Bundle bundle=getArguments();
        assert bundle != null;
        String categoryId=bundle.getString("categoryId",null);
        if(categoryId!=null){
            apiCallGetAgencies(categoryId);
        }
        Onclick();
        return binding.getRoot();
    }
    private void getDistanceInfo(LatLng origin,LatLng destination) {

        Map<String, String> myMapQuery = new HashMap<>();
        myMapQuery.put("key", "");
        myMapQuery.put("origins", origin.latitude + "," + origin.longitude);
        myMapQuery.put("destinations", destination.latitude + "," + destination.longitude);
        myMapQuery.put("mode", "Driving");

        Call<GoogleMapResponse> call = RestClient.makeMapAPI().getDistanceInfo(myMapQuery);
        call.enqueue(new Callback<GoogleMapResponse>() {
            @Override
            public void onResponse(Call<GoogleMapResponse> call, Response<GoogleMapResponse> response) {
                if (response.body() != null
                       /* &&
                        response.body().getRows() != null &&
                        response.body().getRows().size() > 0 &&
                        response.body().getRows().get(0) != null &&
                        response.body().getRows().get(0).getElements() != null &&
                        response.body().getRows().get(0).getElements().size() > 0 &&
                        response.body().getRows().get(0).getElements().get(0) != null &&
                        response.body().getRows().get(0).getElements().get(0).getDistance() != null &&
                        response.body().getRows().get(0).getElements().get(0).getDuration() != null */
                ) {
                    GoogleMapResponse.Row.Element element = response.body().getRows().get(0).getElements().get(0);
                    String address = response.body().getDestination_addresses().get(0);
                    String duration=element.getDuration().getText()+" "+element.getDistance().getText();
//                    showTravelDistance(duration);
//                    showTravelDistance(element.getDistance().getText() + "\n" + element.getDuration().getText());
                    Log.e("TAG", "distance : " + element.getDistance().getText() + "\n "
                            + element.getDuration().getText());
                    Toast.makeText(requireContext(), "success " + element.getDistance().getText()
                            + "\n" + element.getDuration().getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "else part " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "onFailure : " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GoogleMapResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onFailure : " + t.getMessage());
            }
        });
    }


  public void Onclick(){
        binding.idMapBackReqBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

  }

  private void apiCallGetAgencies(String categoryId){
      Call<GetAgencies> responseCall=RestClient.makeAPI().getAgencies(Integer.parseInt(categoryId));
      responseCall.enqueue(new Callback<GetAgencies>() {
          @Override
          public void onResponse(@NonNull Call<GetAgencies> call, @NonNull Response<GetAgencies> response) {
              if(response.isSuccessful()){
                  latlang=new ArrayList<>();
                  assert response.body() != null;
                  latlang.addAll(response.body().getData());
                  if (mapFragment != null) {
                      mapFragment.getMapAsync(callback);
                  }
              }else{
                  Toast.makeText(requireContext(), "Response "+response.isSuccessful(), Toast.LENGTH_SHORT).show();
              }
          }

          @SuppressLint("LongLogTag")
          @Override
          public void onFailure(@NonNull Call<GetAgencies> call, @NonNull Throwable t) {
              Log.e("onFailureGetAgeniesApiCall","onFailureGetAgeniesApiCall "+t.getMessage());
              Toast.makeText(requireContext(), "Response "+t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
  }

}