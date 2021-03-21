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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        db.collection("Diary").add(diary).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(mood_form.this, "Succesvol opgeslagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load(View v) {
        db.collection("Diary").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Diary diary = documentSnapshot.toObject(Diary.class);

                    String first = diary.getFirst();
                    String second = diary.getSecond();

                    data += "Wat ging er goed: " + first + "\nWat kan er beter: " + second + "\n\n";
                }
                viewData.setText(data);
            }
        });
    }
}