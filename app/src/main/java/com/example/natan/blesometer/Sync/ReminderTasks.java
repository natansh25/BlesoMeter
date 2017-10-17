package com.example.natan.blesometer.Sync;

import android.content.Context;

import com.example.natan.blesometer.NotificationUtils;
import com.example.natan.blesometer.PrefrenceUtilities;

/**
 * Created by natan on 10/16/2017.
 */

public class ReminderTasks  {

    public static final String ACTION_INCREMENT_BLESO_COUNT="increment-bleso-count";

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static final String ACTION_BLESSING_REMINDER="blessing_reminder";


    public static void executeTask(Context context,String action)
    {
        if (ACTION_INCREMENT_BLESO_COUNT.equals(action)) {
            incrementBlessCount(context);
        }
        else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        }
        else if (ACTION_BLESSING_REMINDER.equals(action)) {
            issueChargingReminder(context);
        }
    }


    private static void incrementBlessCount(Context context) {
        PrefrenceUtilities.incrementCount(context);
    }


    private static void issueChargingReminder(Context context) {
        PrefrenceUtilities.incrementChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);
    }




}
