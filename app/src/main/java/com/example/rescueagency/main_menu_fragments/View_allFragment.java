package com.example.rescueagency.main_menu_fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rescueagency.LoginActivityFragments.LoginFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.RestClient;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class View_allFragment extends Fragment {


    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_all, container, false);
        imageView=view.findViewById(R.id.viewAllImageBackPress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                manager.popBackStack();
            }
        });
        apiCall(view);
        return view;
    }
    private void recyclerView(View view){

    }
    private void apiCall(View view) {
        Call<GetCategoryResponse> responseCall = RestClient.makeAPI().getCategory();
        responseCall.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                if (response.isSuccessful()) {
                    GetCategoryResponse imageResponse = response.body();
                    if (imageResponse.getStatus() == 200) {
                        RecyclerView recyclerView=view.findViewById(R.id.viewAllRecyclerView);
                        List<View_all> dataList=new ArrayList<>();
                        for(int i=0;i<imageResponse.getData().size();i++){
                            GetCategoryResponse.Data data = response.body().getData().get(i);
                            dataList.add(new View_all(data.getCategory_name(),data.getImage()));
                        }
                        GridLayoutManager grid=new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(grid);
                        recyclerView.setAdapter(new View_allHolder(dataList,getActivity()));
                    } else {
                        Toast.makeText(getContext(), imageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


