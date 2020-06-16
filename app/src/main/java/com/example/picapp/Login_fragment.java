package com.example.picapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class Login_fragment extends Fragment {

    private String email;
    public static String USERNAME;
    private String password;
    private FirebaseDatabase myAppDatabase;

    public Login_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myAppDatabase = FirebaseDatabase.getInstance();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getActivity().openFileInput("stayLogged.txt")));
            String bool = br.readLine();
            if (bool.equals("true")) {
                MainFragment mFrag = new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mFrag);
                transaction.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().findViewById(R.id.toolbar_main).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_main).setBackgroundResource(R.drawable.picapp_logo2);
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
        getActivity().findViewById(R.id.bottom_nav).setBackgroundColor(R.attr.backgroundColor);
        final TextView emailV = v.findViewById(R.id.login_email);
        final TextView passwordV = v.findViewById(R.id.login_password);
        final CheckBox cb = v.findViewById(R.id.checkBox);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        Button login = v.findViewById(R.id.login_btn);
        Button goToRegister = v.findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!passwordV.getText().toString().equals("") && !emailV.getText().toString().equals("")) {
                    email = emailV.getText().toString();
                    password = passwordV.getText().toString();

                } else {
                    Toast.makeText(getActivity(), "Alle Felder müssen befüllt sein", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cb.isChecked()) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput("stayLogged.txt", MODE_PRIVATE)));
                        bw.write("true");
                        bw.flush();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                DatabaseReference usersRef = myAppDatabase.getReference().child("users");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            HashMap user = (HashMap) ds.getValue();
                            if (user.get("username").equals(email) || user.get("email").equals(email)) {
                                System.out.println(user);
                                login(user);
                                return;
                            }
                        }
                        Toast.makeText(getActivity(), "Falscher Username/E-Mail", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Database Error", databaseError.getMessage());
                    }
                };
                usersRef.addValueEventListener(eventListener);

            }
        });
        goToRegister.setOnClickListener(v1 -> {

            Register_fragment reg = new Register_fragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, reg).addToBackStack(null);
            transaction.commit();
        });
        return v;
    }

    private void login(HashMap user) {

            assert user != null;
            String userName = (String) user.get("username");
            String emailU = (String) user.get("email");
            String passwordU = (String) user.get("password");

            if (email.equals(emailU) || email.equals(userName) && password.equals(passwordU)) {
                USERNAME = userName;
                MainFragment mFrag = new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mFrag);
                transaction.commit();
            }
    }

    public static String getUsername () {
        return USERNAME;
    }
}