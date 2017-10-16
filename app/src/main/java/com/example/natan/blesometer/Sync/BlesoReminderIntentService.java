package com.example.natan.blesometer.Sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.Nullable;

/**
 * Created by natan on 10/16/2017.
 */

public class BlesoReminderIntentService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BlesoReminderIntentService() {
        super("BlesoReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String action =intent.getAction();

        ReminderTasks.executeTask(this,action);

    }
}
