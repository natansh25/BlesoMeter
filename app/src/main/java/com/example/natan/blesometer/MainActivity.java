package com.example.natan.blesometer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.blesometer.Sync.BlesoReminderIntentService;
import com.example.natan.blesometer.Sync.ReminderTasks;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public TextView BlesoCount;
    public ImageButton Shiva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlesoCount= (TextView) findViewById(R.id.count);
        Shiva=(ImageButton)findViewById(R.id.imageButton);
        updateCount();

        /** Setup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
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

    public void Notify(View view) {
        NotificationUtils.remindUserBecauseCharging(this);
    }
}
