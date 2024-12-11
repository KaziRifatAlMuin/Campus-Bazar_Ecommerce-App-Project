package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rifat.campusbazar.databinding.FragmentMyOrdersBinding;

public class MyOrdersFragment extends Fragment {

    private FragmentMyOrdersBinding binding;
    private MyOrdersViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.myOrdersList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}
