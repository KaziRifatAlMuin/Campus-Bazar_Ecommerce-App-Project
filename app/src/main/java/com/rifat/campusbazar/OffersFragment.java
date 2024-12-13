package com.rifat.campusbazar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rifat.campusbazar.databinding.FragmentOffersBinding;

public class OffersFragment extends Fragment {

    private FragmentOffersBinding binding;
    private OffersViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(OffersViewModel.class);
        binding = FragmentOffersBinding.inflate(inflater, container, false);

        setupClickListeners();

        return binding.getRoot();
    }

    private void setupClickListeners() {
        binding.banner1.setOnClickListener(v -> navigateToHomeActivity("Offer 1"));
        binding.banner2.setOnClickListener(v -> navigateToHomeActivity("Offer 2"));
        binding.banner3.setOnClickListener(v -> navigateToHomeActivity("Offer 3"));
        binding.banner4.setOnClickListener(v -> navigateToHomeActivity("Offer 4"));
    }

    private void navigateToHomeActivity(String offerDetails) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("offerDetails", offerDetails);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
