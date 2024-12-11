package com.rifat.campusbazar.ui.mycart;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rifat.campusbazar.R;
import com.rifat.campusbazar.databinding.FragmentMyCartBinding;

public class MyCartFragment extends Fragment {

    private FragmentMyCartBinding binding;
    private MyCartViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        binding = FragmentMyCartBinding.inflate(inflater, container, false);

        // Setup RecyclerView
        RecyclerView recyclerView = binding.cartItemsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: Set an adapter for RecyclerView (not implemented here)

        return binding.getRoot();
    }
}
