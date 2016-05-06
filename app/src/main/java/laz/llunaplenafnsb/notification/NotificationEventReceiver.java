package laz.llunaplenafnsb.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import laz.llunaplenafnsb.preferences.PreferencesManager;

/**
 * Notification event receiver.
 */
public class NotificationEventReceiver extends WakefulBroadcastReceiver {

    public static final String TAG = "NotifEventReceiver";

    private static final String START_NOTIFICATION_SERVICE = "startNotificationService";
    public static final String ANDROID_INTENT_ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    /**
     * Sets alarm up.
     */
    public static void setUpAlarm(Context ctx) {

        Log.v(TAG, "Setting up alarm");

        int freq = PreferencesManager.getFrequencyCheckUpdates(ctx);
        Log.v(TAG, "Will trigger every " + freq + " hours");
//        long intervalInMillis = AlarmManager.INTERVAL_HOUR * freq;
        long intervalInMillis = 60 * 1000 * freq; //Uncheck this if you want to debug and check it every X minutes!

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(ctx);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + intervalInMillis,
                intervalInMillis,
                alarmIntent);
    }

    /**
     * Returns start pending intent.
     *
     * @param ctx Context.
     * @return Pending intent.
     */
    private static PendingIntent getStartPendingIntent(Context ctx) {

        Intent i = new Intent(ctx, NotificationEventReceiver.class);
        i.setAction(START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(ctx, NotificationCodes.REQUEST_CODE, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    /**
     * Cancels the alarm.
     *
     * @param ctx Context.
     */
    public static void cancelAlarm(Context ctx) {

        Log.v(TAG, "Canceling alarm");
        Intent i = new Intent(ctx, NotificationEventReceiver.class);
        PendingIntent pendInt = PendingIntent.getBroadcast(ctx, NotificationCodes.REQUEST_CODE, i, 0);
        ((AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE)).cancel(pendInt);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "onReceive");

        if (START_NOTIFICATION_SERVICE.equals(intent.getAction())) {

            Log.v(TAG, "onReceive triggered from alarm.");
            startWakefulService(context, NotificationIntentService.from(context));
        } else if (intent.getAction().equals(ANDROID_INTENT_ACTION_BOOT_COMPLETED)) {

            setUpAlarm(context);
        }
    }

}
