package com.example.natan.blesometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.blesometer.Sync.BlesoReminderIntentService;
import com.example.natan.blesometer.Sync.ReminderTasks;
import com.example.natan.blesometer.Sync.ReminderUtilities;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public TextView BlesoCount;
    public ImageButton Shiva;
    public ConstraintLayout conn;
    ChargingBroadcastReceiver mChargingReceiver;

    IntentFilter mChargingIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlesoCount= (TextView) findViewById(R.id.count);
        Shiva=(ImageButton)findViewById(R.id.imageButton);
        conn= (ConstraintLayout) findViewById(R.id.con);
        updateCount();

        /** Setup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        ReminderUtilities.scheduleChargingReminder(this);

        mChargingIntentFilter = new IntentFilter();
        mChargingReceiver = new ChargingBroadcastReceiver();
        // COMPLETED (6) Call the addAction method on your intent filter and add Intent.ACTION_POWER_CONNECTED
        // and Intent.ACTION_POWER_DISCONNECTED. This sets up an intent filter which will trigger
        // when the charging state changes.
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
    }



    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mChargingReceiver, mChargingIntentFilter);
    }


    // COMPLETED (8) Override onPause and unregister your receiver using the unregisterReceiver method
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mChargingReceiver);
    }


    /**
     * Updates the TextView to display the new water count from SharedPreferences
     */
    private void updateCount() {
        int Count = PrefrenceUtilities.getCount(this);
        BlesoCount.setText(Count+"");
        Log.i("count", String.valueOf(Count));
    }

    public void incrementWater(View view) {


        Intent incrementWaterCountIntent = new Intent(this, BlesoReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_BLESO_COUNT);
        startService(incrementWaterCountIntent);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** Cleanup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }



    // implemented method on implementing onshared lostner
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (PrefrenceUtilities.KEY_BLESO_COUNT.equals(key)) {
            updateCount();
        }

    }



    private void showCharging(boolean isCharging){
        if (isCharging) {
            conn.setBackgroundColor(Color.parseColor("#F44336"));

        } else {
            conn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }


    // COMPLETED (2) Create an inner class called ChargingBroadcastReceiver that extends BroadcastReceiver
    private class ChargingBroadcastReceiver extends BroadcastReceiver {
        // COMPLETED (3) Override onReceive to get the action from the intent and see if it matches the
        // Intent.ACTION_POWER_CONNECTED. If it matches, it's charging. If it doesn't match it's not
        // charging.
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));

            // COMPLETED (4) Update the UI using the showCharging method you wrote
            showCharging(isCharging);
        }
    }
}
