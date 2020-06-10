package com.example.picapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Register_fragment extends Fragment {

    private static final Object URL = "https://picapp-f8f0d.firebaseio.com/users/";

    public Register_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        final TextView emailV = v.findViewById(R.id.register_email);
        final TextView passwordV = v.findViewById(R.id.register_password);
        Button register = v.findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean passwordCorrect = false;
                String passw = passwordV.getText().toString();
                String email = emailV.getText().toString();

                if (passw.length() < 7) {
                    Toast.makeText(getActivity(), "Password has to be at least 8 symbols long!", Toast.LENGTH_SHORT).show();
                } else if (!passwordV.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(getActivity(), "Password has to contain a number!", Toast.LENGTH_SHORT).show();
                } else {
                    passwordCorrect = true;
                }

                if (passwordCorrect) {
                    if (email.matches(".*@.*") && !email.isEmpty()) {
                        JSONObject postParams = new JSONObject();
                        JSONObject pass = new JSONObject();
                        try {
                            postParams.put(email, pass.put("password", passw));
                            ServerConnectionTaskPOST sctP = new ServerConnectionTaskPOST();
                            sctP.execute(postParams.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Write proper E-mail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }



    private class ServerConnectionTaskPOST extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            String post = strings[0];
            String sJson = "";
            //String post = "\""+email+"\": {\n\"password\": "+password+"\n}";
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(URL + ".json").openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setFixedLengthStreamingMode(post.getBytes().length);
                connection.getOutputStream().write(post.getBytes());
                int i = connection.getResponseCode();
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sJson;
        }
    }
}
