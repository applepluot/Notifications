package com.applepluot.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotifyService extends IntentService {

    public NotifyService() {
        super("NotifyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notifier notifier = new Notifier();
        notifier.createNotification(this);
        //let the Android system know when your Broadcast Receiver's work is done.
        Log.i("NotifyService", "completeWakefulIntent");
        AlarmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
