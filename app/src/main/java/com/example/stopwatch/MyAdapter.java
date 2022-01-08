package com.example.stopwatch;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends ArrayAdapter<String> {

    String mText;

    public MyAdapter(Context context, String mText) {
        super(context, R.layout.layout_list, R.id.lap);
        this.mText = mText;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.layout_list, parent,false);

        TextView setLapThisTime = row.findViewById(R.id.lap);

//        setLapThisTime.setText(StopWatch.);


        return row;
    }


}
