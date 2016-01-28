package laz.llunaplenafnsb.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiField;
import laz.llunaplenafnsb.items.Feed;

/**
 * Feed parser.
 */
public class FeedParser {

    public static Feed parse(JSONObject json) {

        Feed item = new Feed();

        try {

            parseTitle(json, item);
            parseSubtitle(json, item);
            //TODO: Parse links.
            if (json.has(ApiField.ENTRY)) {

                item.setEntries(EntryParser.parse(json.getJSONArray(ApiField.ENTRY)));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }


        return item;
    }


    private static void parseSubtitle(JSONObject json, Feed item) throws JSONException {

        JSONObject jsonSubtitle = json.getJSONObject(ApiField.SUBTITLE);
        item.setSubtitle(jsonSubtitle.getString(ApiField._T));
    }

    private static void parseTitle(JSONObject json, Feed item) throws JSONException {

        JSONObject jsonTitle = json.getJSONObject(ApiField.TITLE);
        item.setTitle(jsonTitle.getString(ApiField._T));
    }
}
