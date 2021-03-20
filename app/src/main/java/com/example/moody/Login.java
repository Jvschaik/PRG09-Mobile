package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText rEmail, rPassword;
    Button rLoginBtn;
    TextView rCreateBtn;
    FirebaseAuth rAuth;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rEmail = findViewById(R.id.email);
        rPassword = findViewById(R.id.password);
        progressbar = findViewById(R.id.progressBar);
        rAuth = FirebaseAuth.getInstance();
        rLoginBtn = findViewById(R.id.buttonLogin);
        rCreateBtn = findViewById(R.id.createText);

        rLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Email is verplicht");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is verplicht");
                    return;
                }
                if (password.length() < 6) {
                    rPassword.setError("Wachtwoord moet langer dan 6 karakters");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                //Authenticate user

                rAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "U bent ingelogd", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        rCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}