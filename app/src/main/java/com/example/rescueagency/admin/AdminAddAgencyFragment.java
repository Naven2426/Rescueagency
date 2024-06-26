package com.example.rescueagency.admin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rescueagency.FileUtils;
import com.example.rescueagency.MainActivity;
import com.example.rescueagency.R;
import com.example.rescueagency.admin.spinner.CustomSpinner;
import com.example.rescueagency.admin.spinner.Data;
import com.example.rescueagency.admin.spinner.FruitAdapter;
import com.example.rescueagency.databinding.FragmentAdminAddAgencyBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AdminAddAgencyFragment extends Fragment  implements CustomSpinner.OnSpinnerEventsListener{

    private String agengyname,mobile,address,totalmember;
    private Uri proof;
    private static final int CREATE_FILE = 1;
    FragmentAdminAddAgencyBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAddAgencyBinding.inflate(inflater, container, false);
        addAgency();
        dropDown();
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        return binding.getRoot();
    }
    private void dropDown(){
        binding.spinnerFruits.setSpinnerEventsListener(this);
        FruitAdapter adapter = new FruitAdapter(requireContext(), Data.getFruitList());
        binding.spinnerFruits.setAdapter(adapter);
    }
    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                       Intent intent= o.getData();
                       proof=intent.getData();

                }
            });

    private void requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED}, 100);

    }
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                }
            });

    public void addAgency() {
        binding.idAddAgencyRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");
                    resultLauncher.launch(intent);
                }else{
//                    Toast.makeText(getContext(), "Please Allow Permission", Toast.LENGTH_SHORT).show();
//                    requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED);
                    requestPermission();
                }

            }
        });
        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (getTextField()) {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_left);
                    AdminAddAgencyNextFragment newFragment = new AdminAddAgencyNextFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("agencyName",agengyname);
                    bundle.putString("mobile",mobile);
                    bundle.putString("address",address);
                    bundle.putString("teamType","");
                    bundle.putString("totalMember",totalmember);
                    File file=new File(proof.toString());
                    String[] pathArr = file.getAbsolutePath().split(":/");
                    String path = pathArr[pathArr.length - 1];
                    bundle.putString("proof",path);
                    binding.idAddAgencyDescriptionTV.setText(file.getName());
                    newFragment.setArguments(bundle);
                    transaction.replace(R.id.frameLayout, newFragment).addToBackStack("AdminAddAgencyNextFragment").commit();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent resultData) {
        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                proof = resultData.getData();

            }
        }
    }
    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        binding.spinnerFruits.setBackground(getResources().getDrawable(R.drawable.bg_spinner_fruit_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        binding.spinnerFruits.setBackground(getResources().getDrawable(R.drawable.bg_spinner_fruit));
    }

    private boolean getTextField(){
        agengyname = binding.idAddAgencyAgencyNameET.getText().toString().trim();
        mobile = binding.idAddAgencyMobileET.getText().toString().trim();
        address = binding.idAddAgencyAddressET.getText().toString().trim();
        totalmember = binding.idAddAgencyTotalMemberET.getText().toString().trim();
        if(agengyname.isEmpty()){
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
        if(totalmember.isEmpty()){
            binding.idAddAgencyTotalMemberET.setError("Enter Total Member");
            return false;
        }
        if(proof==null){
            Toast.makeText(getContext(), "Please Select Proof", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}