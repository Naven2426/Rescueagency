package com.example.rescueagency.admin;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueagency.R;
import com.example.rescueagency.admin.HomeFragment.AdminHomeFragment;
import com.example.rescueagency.databinding.FragmentAdminEditCategoryBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AdminEditCategoryFragment extends Fragment {

FragmentAdminEditCategoryBinding binding;

    private String categoryname,proofName,categorydescription;

    private Uri proof;
    private MultipartBody.Part part;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminEditCategoryBinding.inflate(inflater, container, false);
        clickListeners();
        return binding.getRoot();
    }
    private void clickListeners() {
        binding.idAddAgencyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = requireActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        binding.idAddAgencyNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTextField()) {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new AdminHomeFragment());
                    transaction.commit();
                }
            }
        });

        binding.idAddAgencyRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                resultLauncher.launch(intent);
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

    private boolean getTextField() {
        categoryname = binding.idAddNameET.getText().toString();
//        categorydescription = binding.idAddTypeET.getText().toString();
        if (categoryname.isEmpty()) {
            binding.idAddNameET.setError("Please enter category name");
            return false;
        }
        if (proof == null) {
            Toast.makeText(requireContext(), "Please select proof", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (categorydescription.isEmpty()) {
//            binding.idAddTypeET.setError("Please enter category description");
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