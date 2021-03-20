package com.example.moody;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText fullName, email, password, phoneNumber;
    Button registerBtn;
    TextView loginBtn;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        registerBtn = findViewById(R.id.buttonRegis);
        loginBtn = findViewById(R.id.createText);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
    }
}