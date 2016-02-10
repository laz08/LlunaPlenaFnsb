package laz.llunaplenafnsb.api.parsers;

import android.text.Html;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;

/**
 * Parses nested strings.
 */
public class NestedStringParser {

    public static final String TAG = "NestedStringParser";

    /**
     * Parses $t field.
     *
     * @param object JSON object.
     * @return $t field.
     */
    public static String parseT(JSONObject object) {

//        Log.v(TAG, "Parsing T.");
        if (object.has(ApiConstant._T)) {

            try {

                String text = object.getString(ApiConstant._T);
                return Html.fromHtml(text).toString();

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Parses title object.
     *
     * @param object Json title.
     * @return Parsed title.
     */
    public static String parseTitle(JSONObject object) {

//        Log.v(TAG, "Parsing title.");
        return parseT(object);
    }

    /**
     * Parses subtitle object.
     *
     * @param object Json subtitle.
     * @return Parsed subtitle.
     */
    public static String parseSubtitle(JSONObject object) {

//        Log.v(TAG, "Parsing subtitle.");
        return parseT(object);
    }

    /**
     * Parses updated object.
     *
     * @param object Json updated.
     * @return Parsed updated.
     */
    public static String parseUpdated(JSONObject object) {

//        Log.v(TAG, "Parsing updated timestamp");
        return parseT(object);
    }

    /**
     * Parses summary object.
     *
     * @param object Summary json object.
     * @return Summary parsed.
     */
    public static String parseSummary(JSONObject object) {

//        Log.v(TAG, "Parsing summary");
        return parseT(object);
    }

    /**
     * Parses name object.
     *
     * @param object Name json object.
     * @return Name parsed.
     */
    public static String parseName(JSONObject object) {

//        Log.v(TAG, "Parsing name");
        return parseT(object);
    }

    /**
     * Parses email object.
     *
     * @param object Email json object.
     * @return Email parsed.
     */
    public static String parseEmail(JSONObject object) {

//        Log.v(TAG, "Parsing email");
        return parseT(object);
    }


}
