package com.rifat.campusbazar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rifat.campusbazar.databinding.FragmentMyCartBinding;

import java.util.ArrayList;

public class MyCartFragment extends Fragment {

    private FragmentMyCartBinding binding;
    private MyCartViewModel viewModel;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        binding = FragmentMyCartBinding.inflate(inflater, container, false);

        // Setup RecyclerView
        RecyclerView recyclerView = binding.cartItemsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartAdapter = new CartAdapter(getContext(), new ArrayList<>(), product -> {
            // Delete item callback
            viewModel.removeItem(product);
        });

        recyclerView.setAdapter(cartAdapter);

        // Observe cart items
        viewModel.getCartItems().observe(getViewLifecycleOwner(), products -> {
            cartAdapter.updateCartItems(products);
        });

        // Observe total price
        viewModel.getTotalPrice().observe(getViewLifecycleOwner(), total -> {
            binding.headerTotalPrice.setText("Total: " + total + " ৳");
        });

        // Checkout button click
        binding.checkoutButton.setOnClickListener(v -> {
            // For demonstration, open SSLCommerz sandbox URL in a browser
            // In a real app, integrate with SSLCommerz API using their SDK or API calls
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.sslcommerz.com/"));
            startActivity(browserIntent);
        });

        return binding.getRoot();
    }
}
