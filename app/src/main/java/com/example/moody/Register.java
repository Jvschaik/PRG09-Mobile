package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    EditText rFullName, rEmail, rPassword, rPhoneNumber;
    Button rRegisterBtn;
    TextView rLoginBtn;
    FirebaseAuth rAuth;
    ProgressBar progressbar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rFullName = findViewById(R.id.fullName);
        rEmail = findViewById(R.id.email);
        rPassword = findViewById(R.id.password);
        rPhoneNumber = findViewById(R.id.phoneNumber);
        rRegisterBtn = findViewById(R.id.buttonRegis);
        rLoginBtn = findViewById(R.id.createText);

        rAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressbar = findViewById(R.id.progressBar);

        if (rAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        rRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                String fullname = rFullName.getText().toString();
                String phone = rPhoneNumber.getText().toString();

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

                //register the user in firebase

                rAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Gebruiker aangemaakt", Toast.LENGTH_SHORT).show();
                            userID = rAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("Naam", fullname);
                            user.put("E-mail", email);
                            user.put("Telefoon", phone);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        rLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}