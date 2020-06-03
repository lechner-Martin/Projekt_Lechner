package com.example.picapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Login_fragment extends Fragment {

    String email;
    String password;

    public Login_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView emailV = v.findViewById(R.id.email);
        final TextView passwordV = v.findViewById(R.id.password);
        Button login = v.findViewById(R.id.login_btn);
        Button goToRegister = v.findViewById(R.id.registerButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailV.getText().toString();
                password = passwordV.getText().toString();
                Toast.makeText(getContext(),"Got it",Toast.LENGTH_SHORT);
            }
        });
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register_fragment reg = new Register_fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout, reg);
                transaction.commit();
            }
        });
        return v;
    }



}
