
package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopWatch extends AppCompatActivity implements myRecyclerViewInterface{
    Button btnStart, btnPause, btnReset, btnLap;
    Animation animBtn;
    Animation rotate;
    Animation down;
    ImageView clockHand;
    TextView timer;
    LinearLayout startStop;
    RecyclerView recyclerView;
    MyRecyclerAdapter recyclerAdapter;
    List<String> lapList;

    int hours;
    int minutes;
    int secs;
    Handler handler;

    TextView setLap, setSlno;

    String lap;
    LinearLayoutManager layoutManager;
    //Number of seconds displayed on the stopwatch.
    private int seconds = 0;
    //Is the stopwatch running?
    private boolean running;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        btnStart = findViewById(R.id.btn_start);
        btnPause = findViewById(R.id.btn_stop);
        btnReset = findViewById(R.id.btn_reset);
        timer = findViewById(R.id.timer);
        startStop = findViewById(R.id.start_stop_layout);
        btnLap = findViewById(R.id.btn_lap);
        recyclerView = findViewById(R.id.recycler_view);

        setLap = findViewById(R.id.lap);
        setSlno = findViewById(R.id.slno);

        // handler to deal with time
        handler = new Handler();

        lapList = new ArrayList<String>();
        recyclerAdapter = new MyRecyclerAdapter(lapList, this);
        recyclerView.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // Optionally customize the position you want to default scroll to
//        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        recyclerView.setLayoutManager(layoutManager);


        //getting orientation to change status bar color
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
//            getWindow().setStatusBarColor(ContextCompat.getColor(StopWatch.this,R.color.mycolor));
//            getWindow().setStatusBarColor(ContextCompat.getColor(StopWatch.this,R.color.mycolor));
//            getWindow().setStatusBarColor(ContextCompat.getDrawable(R.drawable.bg_card_clock2));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                Drawable background = getResources().getDrawable(R.drawable.bg_card_clock2);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
                window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
                window.setBackgroundDrawable(background);
            }

        } else {
            // In portrait
        }

        //optional animation
        startStop.setAlpha(0);


        animBtn = AnimationUtils.loadAnimation(this, R.anim.card);
        rotate = AnimationUtils.loadAnimation(this, R.anim.round);
        down = AnimationUtils.loadAnimation(this, R.anim.dowm);



        clockHand = findViewById(R.id.clock_hand);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            lapList = savedInstanceState.getStringArrayList("lapList");
        }



        if (running || lapList.size() != 0){
            // clock running

            clockHand.startAnimation(rotate);
            startStop.animate().alpha(1).translationY(-80).setDuration(500).start();
            btnStart.animate().alpha(0).setDuration(500).start();

            btnStart.setVisibility(View.INVISIBLE);
            startStop.setVisibility(View.VISIBLE);

            recyclerAdapter = new MyRecyclerAdapter(lapList, this);
            recyclerView.setAdapter(recyclerAdapter);
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            // Optionally customize the position you want to default scroll to
//        layoutManager.scrollToPosition(0);
            // Attach layout manager to the RecyclerView
            recyclerView.setLayoutManager(layoutManager);
        }

        runTimer();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;

                clockHand.startAnimation(rotate);
                startStop.animate().alpha(1).translationY(-80).setDuration(500).start();
                btnStart.animate().alpha(0).setDuration(500).start();

                btnStart.setVisibility(View.INVISIBLE);
                startStop.setVisibility(View.VISIBLE);

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setVisibility(View.INVISIBLE);
                startStop.setVisibility(View.VISIBLE);

                if (running){
                    btnPause.setText("Resume");
                    running = false;
                    clockHand.clearAnimation();
                }else{
                    running = true;
                    btnPause.setText("Pause");
                    clockHand.startAnimation(rotate);

                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;

                clockHand.clearAnimation();

                btnStart.setVisibility(View.VISIBLE);

                /// this will animate and setAlpha to 0(make invisible) by translating
                startStop.animate().alpha(0).translationY(80).setDuration(500).start();
                /// this will animate and setAlpha to 1(make visible) by translating
                btnStart.animate().alpha(1).translationY(-80).setDuration(500).start();


                timer.setText("00:00:00");
                lapList.clear();
                recyclerAdapter.notifyDataSetChanged();
            }
        });


        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lap = timer.getText().toString();
                lapList.add(lap);
                layoutManager.scrollToPosition(lapList.size());
                recyclerAdapter.notifyDataSetChanged();
            }
        });


    }

    //Sets the number of seconds on the timer.
    private void runTimer() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds / 3600;
                minutes = (seconds % 3600) / 60;
                secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timer.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putStringArrayList("lapList", (ArrayList<String>) lapList);

        super.onSaveInstanceState(savedInstanceState);

    }


    @Override
    public void onItemClick(int position) {
        lapList.remove(position);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
       if (!running){
           clockHand.clearAnimation();
            btnPause.setText("Resume");
       }

        recyclerAdapter = new MyRecyclerAdapter(lapList, this);
        recyclerView.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}