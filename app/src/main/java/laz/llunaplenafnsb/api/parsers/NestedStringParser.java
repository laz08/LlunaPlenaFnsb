package laz.llunaplenafnsb.api.parsers;

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

        try {

            return object.getString(ApiField._T);
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return null;
    }

}
