package com.example.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;


public class WelcomePageHandler extends Activity {
    Thread t2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage_layout);

        textView = findViewById(R.id.dateAndTime);
        String date = TransitionRecognitionUtils.getCurrentDate();
        String time = TransitionRecognitionUtils.getLocalCurrentTime();
        String dateTime = date +" "+time;
        textView.setText(dateTime);

        t2=new Thread(){
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                }catch(InterruptedException e)
                {
                    Log.d("WelcomePage","exception");
                }finally
                {
                    Intent abc=new Intent(WelcomePageHandler.this, MainActivity.class);
                    startActivity(abc);
                }
            }
        };
        t2.start();

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}



