package laz.llunaplenafnsb.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.activities.MainActivity;
import laz.llunaplenafnsb.api.loader.FeedLoaderManager;
import laz.llunaplenafnsb.helper.DateFormatHelper;
import laz.llunaplenafnsb.items.Feed;
import laz.llunaplenafnsb.preferences.PreferencesManager;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Notification intent service.
 */
public class NotificationIntentService extends IntentService {

    private static final String TAG = "NotifIntentServ";

    /**
     * Constructor.
     */
    public NotificationIntentService() {

        super(TAG);
    }

    /**
     * Creates intent notification service.
     *
     * @param ctx Context.
     * @return NotificationService.
     */
    public static Intent from(Context ctx) {

        Intent intent = new Intent(ctx, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.v(TAG, "OnHandleIntent");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {

                Log.v(TAG, "Is start action");
                checkIfNewUpdate();
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    /**
     * Checks if there's a new update.
     */
    private void checkIfNewUpdate() {

        final Context ctx = getApplicationContext();
        FeedLoaderManager manager = FeedLoaderManager.getInstance(ctx);
        retrofit2.Call<ResponseBody> responseBodyCall = manager.loadFeed(ctx);
        responseBodyCall.enqueue(new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                manageResponse(response, ctx);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                Log.v(TAG, "On failure");
            }
        });
    }

    /**
     * Manages feed response.
     *
     * @param response Response
     * @param ctx      Context.
     */
    private void manageResponse(Response<ResponseBody> response, Context ctx) {

        try {

            final FeedLoaderManager feedLoader = FeedLoaderManager.getInstance(ctx);
            Feed feed = feedLoader.parseFeed(response.body().string());

            if (DateFormatHelper.hasBeenUpdated(feed.getUpdated(), PreferencesManager.getLastUpdated(ctx))) {

                Log.v(TAG, "Updated!");
                PreferencesManager.setLastUpdated(ctx, feed.getUpdated());
                processStartNotification();
            } else {

                Log.v(TAG, "Not Updated.");

            }
        } catch (IOException e) {

            Log.v(TAG, "IO exception on checking new updates");
        }
    }

    /**
     * Processes notification start.
     */
    private void processStartNotification() {

        Log.v(TAG, "Processing start notification.");

        Context ctx = getBaseContext();

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(ctx);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            notifBuilder.setSmallIcon(R.drawable.full_moon_icon);
            notifBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {

            notifBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        Notification notification = notifBuilder
                .setContentTitle("Hi ha noves entrades")
                .setContentText("Hi ha noves notícies al blog de Lluna Plena!")
                .setContentIntent(getOnNotificationClickIntent())
                .build();

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NotificationCodes.NOTIF_ID, notification);
    }

    /**
     * Returns intent to open when on notification click.
     *
     * @return Pending intent.
     */
    private PendingIntent getOnNotificationClickIntent() {

        return PendingIntent.getActivity(this,
                NotificationCodes.NOTIF_ID,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static final String ACTION_START = "actionStart";
}
