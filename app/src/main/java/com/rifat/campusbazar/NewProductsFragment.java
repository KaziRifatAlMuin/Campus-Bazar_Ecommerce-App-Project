package com.rifat.campusbazar.ui.newproducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rifat.campusbazar.databinding.FragmentNewProductsBinding;

public class NewProductsFragment extends Fragment {

    private FragmentNewProductsBinding binding;
    private NewProductsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(NewProductsViewModel.class);
        binding = FragmentNewProductsBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.newProductsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}
