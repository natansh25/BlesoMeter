package com.example.natan.blesometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by natan on 10/16/2017.
 */

public final class PrefrenceUtilities {

    public static final String KEY_BLESO_COUNT="bleso_count";
    public static final String KEY_REMINDER_COUNT="reminder_count";


    private static final int DEFAULT_COUNT = 0;


    synchronized private static void setCount(Context context, int blessingCount) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_BLESO_COUNT, blessingCount);
        editor.apply();
    }

    public static int getCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int totalblessings = prefs.getInt(KEY_BLESO_COUNT, DEFAULT_COUNT);
        return totalblessings;
    }

    synchronized public static void incrementCount(Context context) {
        int Count = PrefrenceUtilities.getCount(context);
        PrefrenceUtilities.setCount(context, ++Count);
    }


    synchronized public static void incrementChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_REMINDER_COUNT, DEFAULT_COUNT);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_REMINDER_COUNT, ++chargingReminders);
        editor.apply();
    }

    public static int getChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_REMINDER_COUNT, DEFAULT_COUNT);
        return chargingReminders;
    }
}




