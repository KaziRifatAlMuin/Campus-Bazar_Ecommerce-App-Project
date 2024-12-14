package com.rifat.campusbazar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.rifat.campusbazar.databinding.FragmentProfileEditBinding;

public class ProfileEditFragment extends Fragment {
    private FragmentProfileEditBinding binding;
    private ProfileViewModel viewModel;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            userEmail = getArguments().getString("userEmail");
        }

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        viewModel.init(userEmail);

        viewModel.getProfileInfo().observe(getViewLifecycleOwner(), new Observer<ProfileInfo>() {
            @Override
            public void onChanged(ProfileInfo profileInfo) {
                if (profileInfo != null) {
                    binding.editTvEmail.setText(profileInfo.getEmail());
                    binding.editEtName.setText(profileInfo.getName());
                    binding.editEtMobile.setText(profileInfo.getMobileNo());
                    binding.editEtUniversity.setText(profileInfo.getUniversity());
                    binding.editEtHallName.setText(profileInfo.getHallName());
                    binding.editEtAddress.setText(profileInfo.getAddress());
                }
            }
        });

        binding.saveProfileButton.setOnClickListener(v -> saveProfile());

        return binding.getRoot();
    }

    private void saveProfile() {
        String name = binding.editEtName.getText().toString().trim();
        String mobile = binding.editEtMobile.getText().toString().trim();
        String university = binding.editEtUniversity.getText().toString().trim();
        String hallName = binding.editEtHallName.getText().toString().trim();
        String address = binding.editEtAddress.getText().toString().trim();
        String email = binding.editTvEmail.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(university) ||
                TextUtils.isEmpty(hallName) || TextUtils.isEmpty(address)) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        ProfileInfo updatedProfile = new ProfileInfo(
                name,
                email,
                mobile,
                university,
                hallName,
                address
        );

        viewModel.updateProfileInfo(updatedProfile, new ProfileViewModel.UpdateCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                // Go back to ProfileFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .commit();
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
