package com.rifat.campusbazar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rifat.campusbazar.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        // Setup notification switch if needed
        // e.g., observe from viewModel and set checked state or
        // save state changes when toggled

        binding.logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());
        binding.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());

        return binding.getRoot();
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                .setNegativeButton("No", null)
                .show();
    }

    private void logoutUser() {
        // Perform logout from Firebase
        auth.signOut();
        // Redirect to start page
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete Account")
                .setMessage("This will permanently remove your profile and all associated data. Proceed?")
                .setPositiveButton("Yes", (dialog, which) -> deleteUserAccount())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteUserAccount() {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "No user is currently logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the authenticated user's email
        String userEmail = auth.getCurrentUser().getEmail();
        if (userEmail == null) {
            Toast.makeText(getContext(), "Failed to retrieve user email.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Replace '.' with ',' as per rules
        String emailKey = userEmail.replace(".", ",");

        // Delete user data from the database
        FirebaseDatabase.getInstance().getReference("users")
                .child(emailKey)
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Once data is deleted from DB, delete the user from Auth
                        auth.getCurrentUser().delete()
                                .addOnCompleteListener(delTask -> {
                                    if (delTask.isSuccessful()) {
                                        // Redirect to start page
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getContext(), "Failed to delete authentication account: " + delTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Exception e = task.getException();
                        String errorMessage = (e != null) ? e.getMessage() : "Unknown error occurred";
                        Toast.makeText(getContext(), "Failed to delete user data from database: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Log or show failure reason
                    Toast.makeText(getContext(), "Failed to delete user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


}
