package laz.llunaplenafnsb.api.parsers;

import android.text.Html;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiField;

/**
 * Parses nested strings.
 */
public class NestedStringParser {

    /**
     * Parses $t field.
     *
     * @param object JSON object.
     * @return $t field.
     */
    public static String parseT(JSONObject object) {

        if (object.has(ApiField._T)) {

            try {

                String text = object.getString(ApiField._T);
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

        return parseT(object);
    }

    /**
     * Parses subtitle object.
     *
     * @param object Json subtitle.
     * @return Parsed subtitle.
     */
    public static String parseSubtitle(JSONObject object) {

        return parseT(object);
    }

    /**
     * Parses updated object.
     *
     * @param object Json updated.
     * @return Parsed updated.
     */
    public static String parseUpdated(JSONObject object) {

        return parseT(object);
    }

    /**
     * Parses summary object.
     *
     * @param object Summary json object.
     * @return Summary parsed.
     */
    public static String parseSummary(JSONObject object) {

        return parseT(object);
    }

    /**
     * Parses name object.
     *
     * @param object Name json object.
     * @return Name parsed.
     */
    public static String parseName(JSONObject object) {

        return parseT(object);
    }

    /**
     * Parses email object.
     *
     * @param object Email json object.
     * @return Email parsed.
     */
    public static String parseEmail(JSONObject object) {

        return parseT(object);
    }


}
