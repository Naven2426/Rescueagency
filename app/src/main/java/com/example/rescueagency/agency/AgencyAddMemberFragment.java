package com.example.rescueagency.agency;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rescueagency.BookingFragment;
import com.example.rescueagency.R;
import com.example.rescueagency.databinding.FragmentAgencyAddMemberBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AgencyAddMemberFragment extends Fragment {

    FragmentAgencyAddMemberBinding binding;
    public static List<Uri> uriImages;
    private final ActivityResultLauncher<String> pickMedia =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    uriImages = new ArrayList<>();
                    uriImages.add(uri);
                    updateImagePreview();
                } else {
                    Toast.makeText(requireContext(), "No Media Selected", Toast.LENGTH_SHORT).show();
                }
            });

    private String name, email, phone, address, dob, role, year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgencyAddMemberBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }

    private void click() {
        binding.idAgencyAddMemberBackButton.setOnClickListener(v -> {
            FragmentManager transaction = requireActivity().getSupportFragmentManager();
            transaction.popBackStack();
        });

        binding.idAgencyAddMemberCard.setOnClickListener(v ->
                pickMedia.launch("image/*")
        );

        binding.idAgencyAddMemberPreviewButton.setOnClickListener(v -> {
            if (getTextField()) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("phone", phone);
                bundle.putString("email", email);
                bundle.putString("address", address);
                bundle.putString("dob", dob);
                bundle.putString("role", role);
                bundle.putString("year", year);
                bundle.putParcelableArrayList("uriImages", new ArrayList<>(uriImages));

                AgencyMemberDetailPreviewFragment previewFragment = new AgencyMemberDetailPreviewFragment();
                previewFragment.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, previewFragment);
                transaction.addToBackStack("AgencyMemberDetailPreviewFragment");
                transaction.commit();
            }
        });

        binding.idEdittextAgencyAddDateText.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (DatePicker view1, int year1, int monthOfYear, int dayOfMonth) ->
                            binding.idEdittextAgencyAddDateText.setText(year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                    year, month, day);
            datePickerDialog.show();
        });
    }

    private boolean getTextField() {
        name = binding.idEdittextAgencyAddNameText.getText().toString().trim();
        phone = binding.idEdittextAgentMobileText.getText().toString().trim();
        email = binding.idEdittextAgentEmailText.getText().toString().trim();
        address = binding.idEdittextAgentAddressText.getText().toString().trim();
        dob = binding.idEdittextAgencyAddDateText.getText().toString().trim();
        role = binding.idEdittextAddMemberRollText.getText().toString().trim();
        year = binding.idEdittextAddMemberYoeText.getText().toString().trim();

        if (name.isEmpty()) {
            binding.idEdittextAgencyAddNameText.setError("Enter Name");
            return false;
        }
        if (phone.isEmpty()) {
            binding.idEdittextAgentMobileText.setError("Enter Mobile Number");
            return false;
        }
        if (email.isEmpty()) {
            binding.idEdittextAgentEmailText.setError("Enter Email");
            return false;
        }
        if (address.isEmpty()) {
            binding.idEdittextAgentAddressText.setError("Enter Address");
            return false;
        }
        if (dob.isEmpty()) {
            binding.idEdittextAgencyAddDateText.setError("Enter DOB");
            return false;
        }
        if (role.isEmpty()) {
            binding.idEdittextAddMemberRollText.setError("Enter Role");
            return false;
        }
        if (year.isEmpty()) {
            binding.idEdittextAddMemberYoeText.setError("Enter Year");
            return false;
        }
        return true;
    }

    private void updateImagePreview() {
        if (uriImages != null && !uriImages.isEmpty()) {
            binding.showImagesVP.setVisibility(View.VISIBLE);
            BookingFragment.ImagePreviewAdapter imagePreviewAdapter = new BookingFragment.ImagePreviewAdapter(uriImages, requireContext());
            binding.showImagesVP.setAdapter(imagePreviewAdapter); // Make sure ImagePreviewAdapter extends PagerAdapter
        } else {
            binding.showImagesVP.setVisibility(View.GONE);
        }
    }
}
