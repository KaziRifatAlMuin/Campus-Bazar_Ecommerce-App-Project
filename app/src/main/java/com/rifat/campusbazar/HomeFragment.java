package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView productsRecyclerView;
    private ViewPager2 sliderViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set up RecyclerView for products
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Set adapter with an OnProductClickListener
        productsRecyclerView.setAdapter(new ProductAdapter(
                requireContext(),
                getDummyProductList(),
                this::onProductClick
        ));

        // Set up ViewPager2 for slider
        sliderViewPager = view.findViewById(R.id.sliderViewPager);
        sliderViewPager.setAdapter(new SliderAdapter(getSliderImages()));

        return view;
    }

    // Handle product clicks
    private void onProductClick(Product product) {
        // Add the product to the cart
        CartRepository.getInstance().addItemToCart(product);
        // Show a Toast message as feedback
        Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }

    // Dummy data for products
    private List<Product> getDummyProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Laptop", "45,000 ৳", R.drawable.laptop, "Tech"));
        productList.add(new Product("Smartphone", "15,000 ৳", R.drawable.smartphone, "Tech"));
        productList.add(new Product("Headphones", "2,500 ৳", R.drawable.headphones, "Tech"));
        productList.add(new Product("Smartwatch", "3,999 ৳", R.drawable.smartwatch, "Tech"));
        productList.add(new Product("Algorithms", "500 ৳", R.drawable.algorithms, "Books"));
        productList.add(new Product("T-shirt", "800 ৳", R.drawable.tshirt, "Clothes"));
        productList.add(new Product("Arduino Kit", "3,000 ৳", R.drawable.arduinokit, "Equipments"));
        productList.add(new Product("Desk Lamp", "1,200 ৳", R.drawable.desklamp, "Others"));
        return productList;
    }

    // Dummy data for slider images
    private List<Integer> getSliderImages() {
        List<Integer> sliderImages = new ArrayList<>();
        sliderImages.add(R.drawable.slider2);
        sliderImages.add(R.drawable.slider1);
        sliderImages.add(R.drawable.slider3);
        sliderImages.add(R.drawable.slider4);
        return sliderImages;
    }
}
