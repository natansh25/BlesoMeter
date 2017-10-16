package com.example.natan.blesometer.Sync;

import android.content.Context;

import com.example.natan.blesometer.PrefrenceUtilities;

/**
 * Created by natan on 10/16/2017.
 */

public class ReminderTasks  {

    public static final String ACTION_INCREMENT_BLESO_COUNT="increment-bleso-count";


    public static void executeTask(Context context,String action)
    {
        if (ACTION_INCREMENT_BLESO_COUNT.equals(action)) {
            incrementBlessCount(context);
        }
    }


    private static void incrementBlessCount(Context context) {
        PrefrenceUtilities.incrementCount(context);
    }




}
