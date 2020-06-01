package com.example.picapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Friends extends Fragment {

    public ListAdapter friendsAdapter;
    private ListView list;
    private List<String> friends = new ArrayList<>();
    public Friends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_friends , container , false );
        initializeViews (view);
        return view;
    }
    private void initializeViews (View view) {

        list = view.findViewById(R.id.friends_list);
    }

    public List<String> getFriends() {
        return friends;
    }

    @Override
    public void onStart() {
        super. onStart() ;
        friendsAdapter = new FriendsAdapter(getActivity(), R.layout.friend_list_layout, friends);
        list.setAdapter(friendsAdapter);
    }
}
