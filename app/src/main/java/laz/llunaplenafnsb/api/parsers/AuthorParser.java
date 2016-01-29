package laz.llunaplenafnsb.api.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.AuthorItem;

/**
 * Author parser.
 */
public class AuthorParser {

    public static final String TAG = "AuthorParser";

    /**
     * Parses first author from an array of authors.
     *
     * @param jsonArray JSON array of authors.
     * @return First author parsed from array.
     */
    public static AuthorItem parseFirstAuthor(JSONArray jsonArray) {

        Log.v(TAG, "Parsing first author.");
        if (jsonArray != null && jsonArray.length() > 0) {

            try {

                return parse(jsonArray.getJSONObject(0));
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Parses an author.
     *
     * @param json Json object.
     * @return Author parsed.
     */
    public static AuthorItem parse(JSONObject json) {

        Log.v(TAG, "Parsing author.");
        AuthorItem author = new AuthorItem();
        try {

            author.setName(NestedStringParser.parseName(json.getJSONObject(ApiConstant.NAME)));
            author.setEmail(NestedStringParser.parseEmail(json.getJSONObject(ApiConstant.EMAIL)));
            author.setThumbnail(ImageParser.parse(json.getJSONObject(ApiConstant.IMAGE)));
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return author;
    }
}
