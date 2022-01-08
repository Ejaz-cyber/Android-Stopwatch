package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    int vibrateMili = 200;
    Animation atg, card;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = findViewById(R.id.cardView);
        // load the animation
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        card = AnimationUtils.loadAnimation(this,R.anim.card);
        cardView.setAnimation(card);
        //setting the animation
//        cardView.setAnimation(atg);

        

    }

    public void getStarted(View view) {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(vibrateMili, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(vibrateMili);
        }
        Intent intent = new Intent(this, StopWatch.class);
        startActivity(intent);
    }
}




