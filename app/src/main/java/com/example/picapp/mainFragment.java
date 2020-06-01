package com.example.picapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class mainFragment extends Fragment {

    public ListView listView;
    public List list = new ArrayList();

    public mainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super. onStart() ;
        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = view.findViewById(R.id.list_main);
        return view;
    }
}
