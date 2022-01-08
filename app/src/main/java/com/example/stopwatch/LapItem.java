package com.example.stopwatch;

import android.widget.ArrayAdapter;

public class LapItem {
// we have created a cuctome Objext(layout_list.xml) so we need a java class for
// that which then contains the textView ImageView ... that are preset in that single object

//    private int index;
    private String lap;

    public LapItem(String lap) {
        this.lap = lap;
    }

    public String getLap() {
        return lap;
    }

    public void setLap(String lap) {
        this.lap = lap;
    }
//
//    now our custome object is ready
//            and we will use this to fill our list view later
}
