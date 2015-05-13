package com.applepluot.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by achow on 5/10/15.
 */
public class Alarm {
    public void setAlarm(Context context) {
        //get reference to AlarmManager
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

//        //1st approach
//        //RTC alarm repeating
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 47);
//        long milliseconds = calendar.getTimeInMillis();
//
//        alarmMgr.setInexactRepeating(AlarmManager.RTC, milliseconds,
//                AlarmManager.INTERVAL_DAY, getMainActivityPendingIntent(context));
//        ComponentName bootReceiver = new ComponentName(context, BootReceiver.class);
//        PackageManager packageManager = context.getPackageManager();
//        packageManager.setComponentEnabledSetting(bootReceiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

        //Elapse real time repeating, 2nd approach
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                10 * 1000, getBroadcastActivityPendingIntent(context));
        //enable BootReceiver
        ComponentName bootReceiver = new ComponentName(context, BootReceiver.class);
        PackageManager packageManager = context.getPackageManager();

        packageManager.setComponentEnabledSetting(bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Log.i("alarm", "Set alarm");
    }
    protected void cancelAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //1st approach
        //alarmMgr.cancel(getMainActivityPendingIntent(context));
        Log.i("cancelAlarm", "Cancel alarm");
        alarmMgr.cancel(getBroadcastActivityPendingIntent(context));
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    // 2nd approach
    protected PendingIntent getBroadcastActivityPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return(pendingIntent);
    }

    // first approach
    protected PendingIntent getMainActivityPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return(pendingIntent);
    }
}
