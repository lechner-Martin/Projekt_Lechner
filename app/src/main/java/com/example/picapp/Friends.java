package com.example.picapp;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class Friends extends Fragment {

    public ListAdapter friendsAdapter;
    private ListView list;
    private List friends;
    public Friends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater , final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_friends , container , false );
        initializeViews (view);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this::checkAndAdd);
        return view;
    }

    public void checkAndAdd(View v) {

        AlertDialog.Builder addFriendDialog = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.add_friend_dialog,null);
        EditText username_friend = dialogView.findViewById(R.id.username_friend);
        addFriendDialog.setView(dialogView);
        addFriendDialog.setNegativeButton("Abbrechen", null);
        addFriendDialog.setPositiveButton("Hinzufügen", (dialog, which) -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference users = database.getReference().child("users");
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap user = (HashMap) ds.getValue();


                        String us = user.get("username").toString();
                        if (us.equals(username_friend.getText().toString())) {

                            String curUser = Login_fragment.USERNAME;

                            HashMap currentUser = (HashMap) dataSnapshot.child(curUser).getValue();
                            friends = (List) currentUser.get("friends");
                            List a = (List) user.get("friends");
                            boolean hasFriends = true;
                            boolean friendHasFriends = true;
                            try {
                                if ((Long) friends.get(0) == 1L) {
                                    hasFriends = false;
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if ((Long) a.get(0) == 1L) {
                                    friendHasFriends = false;
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (!friendHasFriends) {
                                a.clear();
                                user.remove("friends");
                            }

                            if (!hasFriends) {
                                friends.clear();
                                currentUser.remove("friends");
                            }

                            if (friends.contains(us)) {
                                Toast.makeText(getActivity(), "Dieser User ist bereits Ihr Freund", Toast.LENGTH_LONG).show();
                                break;
                            }

                            friends.add(us);
                            currentUser.put("friends",friends);
                            users.child(curUser).setValue(currentUser);

                            a.add(curUser);
                            user.put("friends",a);
                            users.child(us).setValue(user);
                        }
                        break;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Database Error", databaseError.getMessage());
                }
            };
            users.addValueEventListener(eventListener);
        });
        addFriendDialog.show();
    }

    private void initializeViews (View view) {

        list = view.findViewById(R.id.friends_list);
        friendsAdapter = new FriendsAdapter(getActivity(), R.layout.friend_list_layout, friends);
        list.setAdapter(friendsAdapter);
    }

    public List<String> getFriends() {
        return friends;
    }
}
