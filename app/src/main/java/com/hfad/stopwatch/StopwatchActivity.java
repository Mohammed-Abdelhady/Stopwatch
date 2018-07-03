package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    //Record number of second // default false
    private int seconds = 0;

    //Check if stopWatch Running or not
    private boolean running;

    //variable to record whether the stopWatch was running before
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if(savedInstanceState != null){
            //retrieve the values of the seconds and running from Bundle
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        /*
        We’re using a separate method to update the stopwatch.
         We’re starting it when the activity is created.
         */
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save values of the seconds and running variables to the Bundle
        outState.putInt("seconds" , seconds);
        outState.putBoolean("running" , running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer(){
        final TextView timeView = findViewById(R.id.timer_view);
        //Create a new Handler
        final Handler handler = new Handler();
        /*
        Call the post() method, passing in a new Runnable. The post()
        method processes code without a delay, so the code in the
        Runnable will run almost immediately.
         */
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                /*
                The Runnable run()
                method contains the code
                you want to run—in our
                case, the code to update
                the text view
                 */
                String time = String.format(Locale.getDefault() , "%d:%02d:%02d" , hours , minutes , secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                /*
                Post the code in the Runnable to be run again
                after a delay of 1,000 milliseconds. As this line
                of code is included in the Runnable run() method,
                it will keep getting called.
                 */
                handler.postDelayed(this, 1000);
            }
        });

    }


}
