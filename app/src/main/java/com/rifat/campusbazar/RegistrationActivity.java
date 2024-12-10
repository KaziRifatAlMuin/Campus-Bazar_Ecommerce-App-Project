package com.rifat.campusbazar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Handle "Sign Up" button click
        findViewById(R.id.login_btn).setOnClickListener(view -> {
            // After registration, redirect to LoginActivity
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle "Sign In" text click
        TextView signInText = findViewById(R.id.sign_in);
        signInText.setOnClickListener(view -> {
            // Redirect to LoginActivity directly
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
