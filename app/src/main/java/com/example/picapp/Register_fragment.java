package com.example.picapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        final TextView usernameV = v.findViewById(R.id.register_username);
        final TextView emailV = v.findViewById(R.id.register_email);
        final TextView passwordV = v.findViewById(R.id.register_password);
        Button register = v.findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean passwordCorrect = false;
                String userNameE = "";
                String passw = "";
                String emailL = "";

                if (!usernameV.getText().toString().equals("") && !passwordV.getText().toString().equals("") && !emailV.getText().toString().equals("")) {
                    userNameE = usernameV.getText().toString();
                    passw = passwordV.getText().toString();
                    emailL = emailV.getText().toString();

                } else {
                    Toast.makeText(getActivity(), "Alle Felder müssen befüllt sein", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String userName = userNameE;
                final String email = emailL;

                if (passw.length() < 7) {
                    Toast.makeText(getActivity(), "Passwort muss min. 8 Zeichen lang sein!", Toast.LENGTH_SHORT).show();
                } else if (!passwordV.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(getActivity(), "Passwort muss eine Nummer beinhalten!", Toast.LENGTH_SHORT).show();
                } else {
                    passwordCorrect = true;
                }

                if (passwordCorrect) {
                    if (email.matches(".*@.*") && !email.isEmpty()) {
                        JSONObject postParams = new JSONObject();
                        JSONObject pass = new JSONObject();
                        try {
                            pass.put("password", passw);
                            pass.put("email", email);
                            pass.put("userName", userName);
                            postParams.put("user", pass);
                            FirebaseDatabase checkRegisteredUsers = FirebaseDatabase.getInstance();
                            DatabaseReference getUsers = checkRegisteredUsers.getReference().child("users");
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        JSONObject user = ds.child("user").getValue(JSONObject.class);
                                        String result = checkUsers(user, userName, email);
                                        if (result.equals("")) {

                                        }else {
                                            return;
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("Database Error", databaseError.getMessage());
                                }
                            };
                            getUsers.addValueEventListener(eventListener);
                            ServerConnectionTaskPOST sctP = new ServerConnectionTaskPOST();
                            sctP.execute(postParams.toString());
                            Toast.makeText(getActivity(), "Account erstellt", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getActivity(), "E-mail ist falsch", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    private String checkUsers(JSONObject user, String userN, String email) {

        try {
            assert user != null;
            String userName = (String) user.get("userName");
            String emailU = (String) user.get("email");

            if (email.equals(emailU)) {
                Toast.makeText(getActivity(), "E-mail bereits registriert", Toast.LENGTH_SHORT).show();
                return "email";

            }else if (userN.equals(userName)) {
                Toast.makeText(getActivity(), "Username bereits vergeben", Toast.LENGTH_SHORT).show();
                return "username";

            }else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Do is wos kaputt";
    }

    private class ServerConnectionTaskPOST extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            BufferedReader reader;
            String post = strings[0];
            String sJson = "";
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