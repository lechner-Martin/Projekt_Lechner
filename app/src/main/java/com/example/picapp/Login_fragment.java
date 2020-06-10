package com.example.picapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Login_fragment extends Fragment {

    String email;
    String password;
    private static final Object URL = "https://picapp-f8f0d.firebaseio.com/users";

    public Login_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView emailV = v.findViewById(R.id.login_email);
        final TextView passwordV = v.findViewById(R.id.login_password);
        CheckBox cb = v.findViewById(R.id.checkBox);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        Button login = v.findViewById(R.id.login_btn);
        Button goToRegister = v.findViewById(R.id.registerButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailV.getText().toString();
                password = passwordV.getText().toString();

                ServerConnectionTaskGET sct = new ServerConnectionTaskGET();
                //String returned =
                sct.execute(email, password);

                /*if (!returned.equals("Error") && !returned.equals("null")) {
                    try {
                        JSONObject jsonObject = new JSONObject(returned);
                        if (jsonObject.getString("password").equals(password)) {
                            mainFragment main = new mainFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, main);
                            transaction.commit();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "You need to register first!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register_fragment reg = new Register_fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, reg).addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }

    private class ServerConnectionTaskGET extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("Error") && !s.equals("null")) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("password").equals(password)) {
                        mainFragment main = new mainFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, main);
                        transaction.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "You need to register first!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader;
            String email = strings[0];
            String sJson = "";
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(URL + email + ".json").openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);
                connection.setRequestProperty("Content-Type", "application/json");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
                    sJson = readResponseStream(reader);
                    System.out.println(sJson);

                }else {
                    reader = new BufferedReader( new InputStreamReader(connection.getErrorStream()));
                    sJson = "Error";

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