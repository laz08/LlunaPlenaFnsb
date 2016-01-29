package laz.llunaplenafnsb.api.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiField;
import laz.llunaplenafnsb.items.AuthorItem;

/**
 * Author parser.
 */
public class AuthorParser {

    /**
     * Parses first author from an array of authors.
     *
     * @param jsonArray JSON array of authors.
     * @return First author parsed from array.
     */
    public static AuthorItem parseFirstAuthor(JSONArray jsonArray) {

        AuthorItem author = new AuthorItem();
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

        AuthorItem author = new AuthorItem();
        try {

            author.setName(NestedStringParser.parseName(json.getJSONObject(ApiField.NAME)));
            author.setEmail(NestedStringParser.parseEmail(json.getJSONObject(ApiField.EMAIL)));
            author.setThumbnail(ImageParser.parse(json.getJSONObject(ApiField.IMAGE)));
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return author;
    }
}
