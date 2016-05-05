package laz.llunaplenafnsb.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preferences manager.
 */
public class PreferencesManager {

    /**
     * Stores the frequency in which the user wants to check for updates.
     *
     * @param ctx  Context.
     * @param freq Frequency in hours.
     */
    public static void storeFrequencyCheckUpdates(Context ctx, int freq) {

        SharedPreferences.Editor editor = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0).edit();
        editor.putInt(PreferencesValues.FREQUENCY_CHECK, freq);
        editor.apply();
    }

    /**
     * Gets the frequency in which the app checks for new entries.
     *
     * @param ctx Context.
     * @return Frequency in hours.
     */
    public static int getFrequencyCheckUpdates(Context ctx) {

        SharedPreferences prefs = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0);
        return prefs.getInt(PreferencesValues.FREQUENCY_CHECK, PreferencesValues.DEFAULT_HOURS);
    }

    /**
     * Sets if user wants to check for updates
     *
     * @param ctx   Context
     * @param value Value.
     */
    public static void setCheckForUpdates(Context ctx, boolean value) {

        SharedPreferences.Editor editor = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0).edit();
        editor.putBoolean(PreferencesValues.CHECK_FOR_UPDATES, value);
        editor.apply();

    }

    /**
     * Gets if user wants to check for updates automatically.
     *
     * @param ctx Context.
     * @return True if wants to check for updates automatically. False otherwise.
     */
    public static boolean getCheckForUpdates(Context ctx) {

        SharedPreferences prefs = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0);
        return prefs.getBoolean(PreferencesValues.CHECK_FOR_UPDATES, false);
    }

    /**
     * Returns when the feed was last updated in string format.
     *
     * @param ctx Context.
     * @return Last updated in string.
     */
    public static String getLastUpdated(Context ctx) {

        SharedPreferences prefs = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0);
        return prefs.getString(PreferencesValues.LAST_UPDATED, "");
    }

    /**
     * Sets feed last updated date.
     *
     * @param ctx         Context.
     * @param lastUpdated Last updated date as string.
     */
    public static void setLastUpdated(Context ctx, String lastUpdated) {

        SharedPreferences.Editor editor = ctx.getSharedPreferences(PreferencesValues.PREFS_NAME, 0).edit();
        editor.putString(PreferencesValues.LAST_UPDATED, lastUpdated);
        editor.apply();
    }

}
