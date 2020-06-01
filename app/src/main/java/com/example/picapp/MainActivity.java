package com.example.picapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            BufferedReader br = new BufferedReader(new FileReader("account.txt"));
            String line = br.readLine();
            if (line.isEmpty()) {
                setContentView(R.layout.login_layout);
            }else {
                String[] split = line.split(";");
                setContentView(R.layout.activity_main);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        friends = new ArrayList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.friends_id:
                    Intent friendView = new Intent(this,SwitchToFriends.class);
                    startActivityForResult(friendView, 1);
                break;

            case R.id.settings:

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}