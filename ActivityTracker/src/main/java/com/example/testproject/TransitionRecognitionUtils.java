package com.example.testproject;

import com.google.android.gms.location.DetectedActivity;
import java.text.SimpleDateFormat;
import java.util.*;

 class TransitionRecognitionUtils {


      static String getLocalCurrentTime() {

         return ( new SimpleDateFormat("HH:mm:ss", Locale.UK)
                 .format(new Date()));
     }

     static String getCurrentDate(){
         return ( new SimpleDateFormat("dd-MMM-yyyy", Locale.UK)
                 .format(new Date()));
     }

     static String toActivityString( int activity) {
        switch (activity) {
            case DetectedActivity.STILL:
                            return "STILL";
            case DetectedActivity.WALKING:
                            return "WALKING";
            case DetectedActivity.IN_VEHICLE:
                            return "IN_VEHICLE";
            case DetectedActivity.RUNNING:
                        return "RUNNING";
            default: return "UNKNOWN";
        }
    }

}