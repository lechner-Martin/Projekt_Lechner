package com.example.picapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Login_fragment login = new Login_fragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, login).commit();
        friends = new ArrayList();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment chosenOne = null;
            switch (item.getItemId()) {
                case R.id.home_id:
                    chosenOne = new MainFragment();
                    break;
                case R.id.friends_id:
                    chosenOne = new Friends();
                    break;
                case R.id.settings:
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chosenOne).addToBackStack(null).commit();
            return true;
        }
    };
}