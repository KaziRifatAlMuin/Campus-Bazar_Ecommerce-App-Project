package com.rifat.campusbazar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        // Dummy Product List
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Laptop", "₹45,000", R.drawable.laptop));
        productList.add(new Product("Smartphone", "₹15,000", R.drawable.smartphone));
        productList.add(new Product("Headphones", "₹2,500", R.drawable.headphones));
        productList.add(new Product("Smartwatch", "₹3,999", R.drawable.smartwatch));

        // Set Adapter
        productAdapter = new ProductAdapter(this, productList);
        productsRecyclerView.setAdapter(productAdapter);
    }
}
