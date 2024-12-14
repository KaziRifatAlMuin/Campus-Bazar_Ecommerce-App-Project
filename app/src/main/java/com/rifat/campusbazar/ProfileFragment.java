package com.rifat.campusbazar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rifat.campusbazar.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private String currentUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        // Get current user email
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserEmail = (currentUser != null && currentUser.getEmail() != null) ? currentUser.getEmail() : "user@example.com";

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        viewModel.init(currentUserEmail);

        viewModel.getProfileInfo().observe(getViewLifecycleOwner(), new Observer<ProfileInfo>() {
            @Override
            public void onChanged(ProfileInfo profileInfo) {
                if (profileInfo != null) {
                    binding.tvName.setText("Name: " + profileInfo.getName());
                    binding.tvEmail.setText("Email: " + profileInfo.getEmail());
                    binding.tvMobile.setText("Mobile: " + profileInfo.getMobileNo());
                    binding.tvUniversity.setText("University: " + profileInfo.getUniversity());
                    binding.tvHallName.setText("Hall Name: " + profileInfo.getHallName());
                    binding.tvAddress.setText("Address: " + profileInfo.getAddress());
                }
            }
        });

        binding.editProfileButton.setOnClickListener(v -> {
            ProfileEditFragment editFragment = new ProfileEditFragment();
            Bundle args = new Bundle();
            args.putString("userEmail", currentUserEmail);
            editFragment.setArguments(args);

            // Replace ProfileFragment with ProfileEditFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, editFragment)
                    .commit();
        });


        return binding.getRoot();
    }
}
