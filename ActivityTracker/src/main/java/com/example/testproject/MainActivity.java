package com.example.testproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TransitionRecognition mTransitionRecognition;
    static TextView main_activity_tv;
    static ImageView imageView;
    private static Context context;

    DatabaseHelper myDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myDb = DatabaseHelper.getDatabaseHelper(this);
        context =this.getApplicationContext();
        imageView = findViewById(R.id.imageView);
        main_activity_tv =  findViewById(R.id.main_activity_tv);
        initTransitionRecognition();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    static void startIntentService(String activity)
    {
        Intent intent = new Intent( context, MapsActivity.class);
        intent.putExtra("Activity", activity);
        context.startActivity(intent);
    }
    static public void setImageToActivity(int resorceId)
    {
        imageView.setImageResource(resorceId);
    }
    static public void setTextView(String text)
    {
        main_activity_tv.setText(text);
    }
    @Override
    public void onPause() {
//        mTransitionRecognition.stopTracking();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTransitionRecognition() {
        mTransitionRecognition = new TransitionRecognition();
        mTransitionRecognition.startTracking(this);
    }
}
