package com.rifat.campusbazar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp;
    TextView email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signUp = findViewById(R.id.login_btn);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        name = findViewById(R.id.name);

        signUp.setOnClickListener(v ->
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));
    }
}
