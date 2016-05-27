package laz.llunaplenafnsb.helper;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date format helper.
 */
public class DateFormatHelper {

    public static final String TAG = "DateFormatHelper";

    private static DateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Has been updated.
     *
     * @param newUpdated New updated date as string.
     * @param oldUpdated Old updated date as string.
     * @return True if new date is after old one. False otherwise.
     */
    public static boolean hasBeenUpdated(String newUpdated, String oldUpdated) {

        if (oldUpdated.equals("")) {

            return true;
        }
        try {

            Date newDate = sDateFormat.parse(newUpdated);
            Date oldDate = sDateFormat.parse(oldUpdated);

            return newDate.after(oldDate);
        } catch (ParseException e) {

            e.printStackTrace();
            Log.v(TAG, "Could not parse date");
        }

        return false;
    }
}
