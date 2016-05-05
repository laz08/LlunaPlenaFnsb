package laz.llunaplenafnsb.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Notification event receiver.
 */
public class NotificationEventReceiver extends WakefulBroadcastReceiver {

//    http://stackoverflow.com/questions/20501225/using-service-to-run-background-and-create-notification

    public static final String TAG = "NotifEventReceiver";

    private static final String START_NOTIFICATION_SERVICE = "startNotificationService";

    /**
     * Sets alarm up.
     */
    public static void setUpAlarm(Context ctx) {

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(ctx);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getRemainingTimeUntilAlarm(),
                AlarmManager.INTERVAL_DAY,
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
     * Returns time resting until specified Alarm Time.
     *
     * @return Time in millis until alarm time.
     */
    public static long getRemainingTimeUntilAlarm() {

        Calendar currentCalendar = Calendar.getInstance();
        Calendar alarmCalendar = Calendar.getInstance();

        alarmCalendar.set(Calendar.AM_PM, Calendar.AM);

        alarmCalendar.set(Calendar.HOUR, ALARM_HOUR);
        if (ALARM_HOUR >= 12) {

            alarmCalendar.set(Calendar.AM_PM, Calendar.PM);
            alarmCalendar.set(Calendar.HOUR, ALARM_HOUR - 12);
        }
        alarmCalendar.set(Calendar.MINUTE, ALARM_MIN);
        alarmCalendar.set(Calendar.SECOND, ALARM_SEC);
        alarmCalendar.set(Calendar.MILLISECOND, 0);

        long currentTime = currentCalendar.getTimeInMillis();
        long alarmTime = alarmCalendar.getTimeInMillis();

        //Next day.
        if (currentTime > alarmTime) {

            Log.v(TAG, "Will be triggered next day.");
            alarmCalendar.add(Calendar.DAY_OF_MONTH, 1);
            alarmTime = alarmCalendar.getTimeInMillis();

        } else {

            Log.v(TAG, "Will be triggered today.");
        }

        Log.v(TAG, "Remaining millis: " + alarmTime);
        return alarmTime;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "onReceive");

        if (START_NOTIFICATION_SERVICE.equals(intent.getAction())) {

            Log.v(TAG, "onReceive triggered from alarm.");
            startWakefulService(context, NotificationIntentService.from(context));
        }
    }

    private static final int ALARM_HOUR = 13;
    private static final int ALARM_MIN = 0;
    private static final int ALARM_SEC = 0;

}
