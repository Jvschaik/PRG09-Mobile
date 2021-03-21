package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class mood_form extends AppCompatActivity {

    private static final String TAG = "mood_form";

    EditText reflect, reflectMood;
    FirebaseFirestore db;
    Button send;
    TextView viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reflect = findViewById(R.id.reflect);
        reflectMood = findViewById(R.id.reflectMood);
        send = findViewById(R.id.send);
        viewData = findViewById(R.id.loadData);
        db = FirebaseFirestore.getInstance();
    }

    public void send(View v) {
        String firstInput = reflect.getText().toString();
        String secondInput = reflectMood.getText().toString();

        Diary diary = new Diary( firstInput, secondInput);

        db.collection("Diary").document("My mood diary").set(diary).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mood_form.this, "Succesvol opgeslagen", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mood_form.this, "Er is iets fout gegaan!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void load(View v) {
        db.collection("Diary").document("My mood diary").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Diary diary = documentSnapshot.toObject(Diary.class);

                            String first = diary.getFirst();
                            String second = diary.getSecond();

                            viewData.setText("Wat ging er goed: " + first + "\n" + "Wat kan er morgen beter: " + second);
                        } else {
                            Toast.makeText(mood_form.this, "Sorry, dit bestaat niet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mood_form.this, "Er gaat iets mis!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }
}