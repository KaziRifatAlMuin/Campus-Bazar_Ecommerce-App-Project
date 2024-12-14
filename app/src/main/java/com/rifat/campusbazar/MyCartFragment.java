package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rifat.campusbazar.databinding.FragmentMyCartBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyCartFragment extends Fragment {

    private FragmentMyCartBinding binding;
    private MyCartViewModel viewModel;
    private CartAdapter cartAdapter;

    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        binding = FragmentMyCartBinding.inflate(inflater, container, false);

        db = FirebaseFirestore.getInstance();

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
            List<Product> cartItems = viewModel.getCartItems().getValue();
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                processCheckout(cartItems);
            }
        });

        return binding.getRoot();
    }

    private void processCheckout(List<Product> cartItems) {
        // Save the order to Firebase
        saveOrderToFirebase(cartItems);

        // Remove all items from the cart
        for (Product product : cartItems) {
            viewModel.removeItem(product);
        }

        Toast.makeText(getContext(), "Checkout complete. Cart is now empty!", Toast.LENGTH_SHORT).show();
    }

    private void saveOrderToFirebase(List<Product> cartItems) {
        int total = 0;
        List<Map<String, Object>> itemList = new ArrayList<>();
        for (Product product : cartItems) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", product.getName());
            item.put("price", product.getPrice());
            itemList.add(item);

            String numericPrice = product.getPrice().replaceAll("[^0-9]", "");
            if (!numericPrice.isEmpty()) {
                total += Integer.parseInt(numericPrice);
            }
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : "anonymous";

        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(System.currentTimeMillis());

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("userId", userId);
        orderData.put("items", itemList);
        orderData.put("totalPrice", total);
        orderData.put("timestamp", timestamp);

        db.collection("orders")
                .add(orderData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Order saved to history!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save order!", Toast.LENGTH_SHORT).show();
                });
    }
}
