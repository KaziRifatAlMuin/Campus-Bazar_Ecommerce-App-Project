package com.rifat.campusbazar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Handle "Sign Up" text click
        TextView signUpText = findViewById(R.id.sign_up);
        signUpText.setOnClickListener(view -> {
            // Navigate to RegistrationActivity
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle "Sign In" button click
        findViewById(R.id.signin_btn).setOnClickListener(view -> {
            // Logic for signing in the user
            // Example: Navigate to DashboardActivity
            // Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            // startActivity(intent);
            // finish();
        });
    }
}
