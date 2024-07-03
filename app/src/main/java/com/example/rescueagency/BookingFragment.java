package com.example.rescueagency;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.rescueagency.agency.NewRequestData;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.databinding.FragmentBookingBinding;

import java.util.ArrayList;
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
    private static ImagePreviewAdapter imagePreviewAdapter;
    String categoryId;
    Bundle bundle;
    List<MultipartBody.Part> images;
    public static AppCompatTextView teamName;
    public static List<Uri> uriImages=new ArrayList<>();

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
//                    setImageViewPager(uri,imagePreviewAdapter);
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
         bundle=getArguments();
         teamName=binding.teamNameTV;
        SharedPreferences sf= requireActivity().getSharedPreferences(Constant.SF_LAT_LONG_NAME,Context.MODE_PRIVATE);
        String teamName=sf.getString(Constant.SF_TEAM_NAME_FOR_NEW_REQUEST,null);
        String agentId=sf.getString(Constant.SF_AGENT_ID_FOR_NEW_REQUEST,null);
        if(teamName!=null && agentId!=null){
            binding.agencyNameACTV.setVisibility(View.VISIBLE);
            binding.agencyNameACTV.setText(teamName);
        }
//         categoryId= bundle.getString("categoryId",null);
        MainActivity mainActivity=(MainActivity) getActivity();
        assert mainActivity != null;


        TakePhotoActivity.fragment=requireContext();

        imagePreviewAdapter=new ImagePreviewAdapter(uriImages,requireContext());
        binding.showImagesVP.setAdapter(imagePreviewAdapter);
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
    public static void setImageViewPager(List<Uri> uris,Context context,ImagePreviewAdapter adapter){
//       if(uriImages==null) {
//           Toast.makeText(context, "new images of array list", Toast.LENGTH_SHORT).show();
//           uriImages=uris;
//       }else{
//           Toast.makeText(context, "add all array list", Toast.LENGTH_SHORT).show();
//           uriImages.addAll(uris);
//       }
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
                transaction.replace(R.id.frameLayout, mapsFragment).addToBackStack("RescueTeamSelectionInMapFragment").commit();
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
    private void apiNewRequest(NewRequestData data,List<MultipartBody.Part> parts) {
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
            binding.idEdittextRequestDescribe.setError("Please Describe Your Problem");
            return false;
        }
        return true;
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
            notifyDataSetChanged();
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
