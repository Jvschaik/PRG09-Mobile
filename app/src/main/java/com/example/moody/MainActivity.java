package com.example.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.moody.fragments.DiaryFragment;
import com.example.moody.fragments.HomeFragment;
import com.example.moody.fragments.LocationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView btnNav = findViewById(R.id.button_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        //setting home fragment as main fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_wrapper, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.ic_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.ic_location:
                            selectedFragment = new LocationFragment();
                            break;

                        case R.id.ic_diary:
                            selectedFragment = new DiaryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_wrapper
                            ,selectedFragment).commit();
                    return true;
                }
            };

}