package com.example.picapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        Login_fragment login = new Login_fragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.login, login).commit();
        friends = new ArrayList();

    }


}