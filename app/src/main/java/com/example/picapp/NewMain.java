package com.example.picapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewMain extends AppCompatActivity {

    private static final Object URL = "https://picapp-f8f0d.firebaseio.com/users";
    List friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login_fragment login = new Login_fragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.mainLayout, login).commit();
        friends = new ArrayList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.friends_id:
                Intent friendView = new Intent(this, SwitchToFriends.class);
                startActivity(friendView);
                break;

            case R.id.settings:

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class ServerConnectionTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {

            String username = strings[0];
            String sJson = "";
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(URL + username + "/").openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
                    sJson = readResponseStream(reader);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sJson;
        }

        private String readResponseStream(BufferedReader reader) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
    }
}
