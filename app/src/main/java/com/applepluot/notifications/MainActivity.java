package com.applepluot.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * When you select the Alarm button, a Broadcast PendingIntent is passed to the AlarmManager.
 * After the specified time the Broadcast is performed and received by your custom WakefulBroadcastReceiver class.
 * The WakefulBroadcastReceiver keeps the device awake, and starts a custom IntentService in the background.
 * The custom IntentService is now free to perform AsyncOperations if it needs to. It creates a notification and
 * when it is done it completes the WakefulBroadcastReceiver.
 */
public class MainActivity extends Activity {

    private int notificationID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button notificationButton = (Button) findViewById(R.id.notify_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayNotification();
            }
        });
        findViewById(R.id.alarm_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setAlarm();
                    }
                });
        findViewById(R.id.cancel_alarm_button).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

    }

    protected void setAlarm() {
        Alarm alarm = new Alarm();
        alarm.setAlarm(this);
    }
    protected void cancelAlarm() {
        Alarm alarm = new Alarm();
        alarm.cancelAlarm(this);
    }
    /*
    protected void setAlarm() {
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                getMainActivityPendingIntent());
        Toast.makeText(this, "Hello alarm", Toast.LENGTH_LONG).show();
//        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + 10 * 1000,
//                10 * 1000,
//
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+5);
        long milliseconds = calendar.getTimeInMillis();
        alarmMgr.setInexactRepeating(AlarmManager.RTC,
                milliseconds,
                AlarmManager.INTERVAL_DAY,
                getMainActivityPendingIntent());
    }
    protected void cancelAlarm() {
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(getMainActivityPendingIntent());
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void displayNotification() {
        Notifier notifier = new Notifier();
        notifier.createNotification(this);
    }

    protected PendingIntent getMainActivityPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }
}
