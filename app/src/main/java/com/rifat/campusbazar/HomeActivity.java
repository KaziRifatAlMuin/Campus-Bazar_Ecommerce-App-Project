package com.rifat.campusbazar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Toolbar setup
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set user details in nav header
        setNavigationHeader(navigationView);

        navigationView.setNavigationItemSelectedListener(this::handleNavigationItemClick);

        // Load HomeFragment by default
        loadFragment(new HomeFragment(), "Home");
    }

    private void setNavigationHeader(NavigationView navigationView) {
        try {
            TextView userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
            TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                userName.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "User Name");
                userEmail.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "user@example.com");
            } else {
                userName.setText("Guest");
                userEmail.setText("guest@example.com");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private boolean handleNavigationItemClick(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new HomeFragment(), "Home");
        } else if (id == R.id.nav_shop) {
            Toast.makeText(this, "Shop Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new ShopFragment(), "Shop");
        } else if (id == R.id.nav_category) {
            Toast.makeText(this, "Category Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new CategoryFragment(), "Category");
        } else if (id == R.id.nav_offers) {
            Toast.makeText(this, "Offers Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new OffersFragment(), "Offers");
        } else if (id == R.id.nav_my_orders) {
            Toast.makeText(this, "My Orders Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new MyOrdersFragment(), "My Orders");
        } else if (id == R.id.nav_my_cart) {
            Toast.makeText(this, "My Cart Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new MyCartFragment(), "My Cart");
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new ProfileFragment(), "Profile");
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
            loadFragment(new SettingsFragment(), "Settings");
        } else {
            Toast.makeText(this, "Unknown Option Selected", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment, String title) {
        updateToolbarTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
