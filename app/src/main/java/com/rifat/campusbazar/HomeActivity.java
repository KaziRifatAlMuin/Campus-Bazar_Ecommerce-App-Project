package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rifat.campusbazar.ui.category.CategoryFragment;
import com.rifat.campusbazar.ui.offers.OffersFragment;
import com.rifat.campusbazar.ui.shop.ShopFragment;
import com.rifat.campusbazar.ui.newproducts.NewProductsFragment;
import com.rifat.campusbazar.ui.myorders.MyOrdersFragment;
import com.rifat.campusbazar.ui.mycart.MyCartFragment;
import com.rifat.campusbazar.ui.settings.SettingsFragment;
import com.rifat.campusbazar.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;

    // Views from main layout
    private TextView headerText;
    private EditText searchBar;
    private ConstraintLayout mainContentLayout; // Parent layout that holds toolbar, headerText, searchBar, RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_home);

            // Initialize DrawerLayout and NavigationView
            drawerLayout = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            // Set up the Toolbar and Drawer Toggle
            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            // Add user details to the navigation header
            setNavigationHeader(navigationView);

            // Handle navigation item clicks
            navigationView.setNavigationItemSelectedListener(this::handleNavigationItemClick);

            // Initialize main content views
            mainContentLayout = findViewById(R.id.mainContentLayout);
            headerText = findViewById(R.id.headerText);
            searchBar = findViewById(R.id.searchBar);
            productsRecyclerView = findViewById(R.id.productsRecyclerView);

            // Setup RecyclerView
            productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
            productAdapter = new ProductAdapter(this, getDummyProductList());
            productsRecyclerView.setAdapter(productAdapter);

        } catch (Exception e) {
            Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setNavigationHeader(NavigationView navigationView) {
        try {
            TextView userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
            TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                userName.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "User Name");
                userEmail.setText(currentUser.getEmail());
            } else {
                userName.setText("Guest");
                userEmail.setText("guest@example.com");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean handleNavigationItemClick(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Keep the if-else structure as requested
        if (id == R.id.nav_home) {
            Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
            // When Home is selected, show the main view and hide fragment container
            showMainContent();
        } else if (id == R.id.nav_shop) {
            Toast.makeText(this, "Shop Selected", Toast.LENGTH_SHORT).show();
            // Load Shop Fragment
            loadFragment(new ShopFragment());
        } else if (id == R.id.nav_category) {
            Toast.makeText(this, "Category Selected", Toast.LENGTH_SHORT).show();
            // Load Category Fragment
            loadFragment(new CategoryFragment());
        } else if (id == R.id.nav_offers) {
            Toast.makeText(this, "Offers Selected", Toast.LENGTH_SHORT).show();
            // Load Offers Fragment
            loadFragment(new OffersFragment());
        } else if (id == R.id.nav_new_products) {
            Toast.makeText(this, "New Products Selected", Toast.LENGTH_SHORT).show();
            // Load New Products Fragment
            loadFragment(new NewProductsFragment());
        } else if (id == R.id.nav_my_orders) {
            Toast.makeText(this, "My Orders Selected", Toast.LENGTH_SHORT).show();
            // Load My Orders Fragment
            loadFragment(new MyOrdersFragment());
        } else if (id == R.id.nav_my_cart) {
            Toast.makeText(this, "My Cart Selected", Toast.LENGTH_SHORT).show();
            // Load My Cart Fragment
            loadFragment(new MyCartFragment());
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
            // Load Settings Fragment
            loadFragment(new SettingsFragment());
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
            // Load Profile Fragment
            loadFragment(new ProfileFragment());
        } else {
            Toast.makeText(this, "Unknown Option Selected", Toast.LENGTH_SHORT).show();
            // If unknown, just close drawer
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer after item selection
        return true;
    }

    private void loadFragment(Fragment fragment) {
        // Hide main content and show fragment container
        hideMainContent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void hideMainContent() {
        // Hide the main views
        headerText.setVisibility(android.view.View.GONE);
        searchBar.setVisibility(android.view.View.GONE);
        productsRecyclerView.setVisibility(android.view.View.GONE);

        // Show fragment container
        findViewById(R.id.fragment_container).setVisibility(android.view.View.VISIBLE);
    }

    private void showMainContent() {
        // Show main views
        headerText.setVisibility(android.view.View.VISIBLE);
        searchBar.setVisibility(android.view.View.VISIBLE);
        productsRecyclerView.setVisibility(android.view.View.VISIBLE);

        // Hide fragment container
        findViewById(R.id.fragment_container).setVisibility(android.view.View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private List<Product> getDummyProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Laptop", "₹45,000", R.drawable.laptop));
        productList.add(new Product("Smartphone", "₹15,000", R.drawable.smartphone));
        productList.add(new Product("Headphones", "₹2,500", R.drawable.headphones));
        productList.add(new Product("Smartwatch", "₹3,999", R.drawable.smartwatch));
        return productList;
    }
}
