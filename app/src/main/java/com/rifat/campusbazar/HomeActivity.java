package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProfileViewModel profileViewModel;
    private TextView userName, userEmail;

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

        // Find header views
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

        // Initialize ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Initialize user email (for ProfileViewModel)
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = (currentUser != null && currentUser.getEmail() != null) ? currentUser.getEmail() : "user@example.com";
        profileViewModel.init(currentUserEmail);

        // Observe ProfileInfo changes
        profileViewModel.getProfileInfo().observe(this, new Observer<ProfileInfo>() {
            @Override
            public void onChanged(ProfileInfo profileInfo) {
                if (profileInfo != null) {
                    userName.setText(profileInfo.getName() != null ? profileInfo.getName() : "User Name");
                    userEmail.setText(profileInfo.getEmail() != null ? profileInfo.getEmail() : "user@example.com");
                }
            }
        });

        // Set NavigationItemSelectedListener
        navigationView.setNavigationItemSelectedListener(this::handleNavigationItemClick);

        // Load HomeFragment by default if there's no saved state
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), "Home");
        }
    }

    private void updateToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private boolean handleNavigationItemClick(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        String title = "";

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            selectedFragment = new HomeFragment();
            title = "Home";
        } else if (id == R.id.nav_shop) {
            selectedFragment = new ShopFragment();
            title = "Shop";
        } else if (id == R.id.nav_category) {
            selectedFragment = new CategoryFragment();
            title = "Category";
        } else if (id == R.id.nav_offers) {
            selectedFragment = new OffersFragment();
            title = "Offers";
        } else if (id == R.id.nav_my_orders) {
            selectedFragment = new MyOrdersFragment();
            title = "My Orders";
        } else if (id == R.id.nav_my_cart) {
            selectedFragment = new MyCartFragment();
            title = "My Cart";
        } else if (id == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
            title = "Profile";
        } else if (id == R.id.nav_settings) {
            selectedFragment = new SettingsFragment();
            title = "Settings";
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment, title);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment, String title) {
        updateToolbarTitle(title);
        getSupportFragmentManager()
                .beginTransaction()
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
