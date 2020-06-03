package com.example.picapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class AdapterMain extends BaseAdapter {

    private List<String> pics;
    private List<String> status;
    private int layoutId;
    private LayoutInflater inflater;

    public AdapterMain(Context context, int layoutId, List<String> pics, List<String> status) {

        this.pics = pics;
        this.status = status;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return pics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = pics.get(position);
        String state = status.get(position);
        View listItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        TextView nameTextView = listItem.findViewById(R.id.name);
        TextView statusTextView = listItem.findViewById(R.id.status);
        nameTextView.setText(name);
        statusTextView.setText(state);
        return listItem;
    }
}
