package com.example.picapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    private List<String> pics;
    private int layoutId;
    private LayoutInflater inflater;

    public FriendsAdapter(Context context, int layoutId, List<String> pics) {
        this.pics = pics;
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
        View listItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;
        TextView nameTextView = listItem.findViewById(R.id.friendName);
        nameTextView.setText(name);
        return listItem;
    }
}
