package com.example.picapp;

import android.content.Context;
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
    public List<String> status = new ArrayList();
    public List<String> list = new ArrayList();
    private OnSelectionChangedListener listener;

    public mainFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) context;
        } else {

        }
    }

    @Override
    public void onStart() {
        super. onStart() ;

        final AdapterMain adapter = new AdapterMain(getContext(), R.layout.main_names_layout, list, status);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = view.findViewById(R.id.list_main);
        return view;
    }
}
