package laz.llunaplenafnsb.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import laz.llunaplenafnsb.notification.NotificationEventReceiver;

public class NotificationHelper {

    /**
     * Enables auto check updates.
     *
     * @param ctx Context.
     */
    public static void enableAutoCheck(Context ctx) {

        ComponentName receiver = new ComponentName(ctx, NotificationEventReceiver.class);
        PackageManager pm = ctx.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * Disables auto check updates.
     *
     * @param ctx Context.
     */
    public static void disableAutoCheckUpdates(Context ctx) {

        ComponentName receiver = new ComponentName(ctx, NotificationEventReceiver.class);
        PackageManager pm = ctx.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
