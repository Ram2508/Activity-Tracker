package com.example.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.concurrent.TimeUnit;

public class TransitionRecognitionReceiver  extends BroadcastReceiver {

        Context mContext;
        DatabaseHelper myDb;
        static long startTime;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        myDb = DatabaseHelper.getDatabaseHelper(mContext);
        Log.d("TransitionReceiver", "onReceive");
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            if (result != null) {
                processTransitionResult(result);
            }else
            {
                Log.d("TransitionRecognitionRe" ,"No Activities are found");
            }
        }
    }


    private void processTransitionResult( ActivityTransitionResult result) {
        for (ActivityTransitionEvent event : result.getTransitionEvents()) {
            onDetectedTransitionEvent(event);
        }
    }

    static int flag =0;
    private void onDetectedTransitionEvent(ActivityTransitionEvent activity) {

        switch (activity.getActivityType()) {
            case DetectedActivity.RUNNING:
                case DetectedActivity.WALKING:
                case DetectedActivity.IN_VEHICLE:
                case DetectedActivity.STILL:
                {

                    Log.d("TransitionRecognition","Event occurred "+activity.getActivityType());
                    if(activity.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                    {
                        loadImage(activity);
                        startTime = activity.getElapsedRealTimeNanos();
                        if(DetectedActivity.IN_VEHICLE == activity.getActivityType() ||
                                DetectedActivity.WALKING == activity.getActivityType()) {
                            Intent i = new Intent("com.android.music.musicservicecommand");
                            i.putExtra("command","pause");
                            mContext.sendBroadcast(i);
                            flag =0;
                          MainActivity.startIntentService(String.valueOf(activity.getActivityType()));
                        }else{
                            if(flag == 0){
                             MapsActivity.startIntentService();

                           Intent h = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                            mContext.startActivity(h);

                            flag =1;}
                        }
                    }
                  else if(activity.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                  {
                      flag =0;
                      String time = TransitionRecognitionUtils.getLocalCurrentTime();
                      long endTime = activity.getElapsedRealTimeNanos();
                      long activityDurationTime;
                      activityDurationTime = endTime - startTime;

                      double seconds = TimeUnit.SECONDS.convert(activityDurationTime,TimeUnit.NANOSECONDS);
                      String toastMessage =  TransitionRecognitionUtils.toActivityString(activity.getActivityType())+" for "+(int)seconds+" seconds ";
                      myDb.insertData(toastMessage,time);
                      Toast.makeText(mContext, toastMessage,Toast.LENGTH_LONG).show();
                      if(DetectedActivity.IN_VEHICLE == activity.getActivityType() ||
                              DetectedActivity.RUNNING == activity.getActivityType()) {
                          MapsActivity.startIntentService();

                      }
                  }
                    break;
                }
                 default: {
                     Toast.makeText(mContext, activity.getActivityType() + " Activity not handled ",Toast.LENGTH_LONG).show();

                }
            }
        }


        private void loadImage(ActivityTransitionEvent activity)
        {

            switch (activity.getActivityType()) {
                case DetectedActivity.RUNNING: {
                    MainActivity.setImageToActivity(R.drawable.ic_directions_run_black_192dp);
                    MainActivity.setTextView("RUNNING");

                    break;
                }
                case DetectedActivity.WALKING: {
                    MainActivity.setImageToActivity(R.drawable.ic_directions_walk_black_192dp);
                    MainActivity.setTextView("WALKING");

                    break;
                }
                case DetectedActivity.IN_VEHICLE: {
                    MainActivity.setImageToActivity(R.drawable.ic_directions_car_black_192dp);
                    MainActivity.setTextView("IN VEHICLE");

                    break;
                }
                case DetectedActivity.STILL: {
                    MainActivity.setImageToActivity(R.drawable.ic_directions_still_black_192dp);
                    MainActivity.setTextView("STILL");
                    break;
                }
            }
        }

}