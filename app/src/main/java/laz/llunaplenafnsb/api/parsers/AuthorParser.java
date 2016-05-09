package laz.llunaplenafnsb.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.AuthorItem;

/**
 * Author parser.
 */
public class AuthorParser {

//    public static final String TAG = "AuthorParser";

    /**
     * Parses an author.
     *
     * @param json Json object.
     * @return Author parsed.
     */
    public static AuthorItem parse(JSONObject json) {

//        Log.v(TAG, "Parsing author.");
        AuthorItem author = new AuthorItem();
        try {

            author.setName(json.getString(ApiConstant.DISPLAY_NAME));
            author.setUrl(json.getString(ApiConstant.URL));
            author.setThumbnail(ThumbnailParser.parse(json.getJSONObject(ApiConstant.IMAGE)));
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return author;
    }
}
