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

            item.setTitle(NestedStringParser.parseT(json.getJSONObject(ApiField.TITLE)));
            item.setSubtitle(NestedStringParser.parseT(json.getJSONObject(ApiField.SUBTITLE)));

            if (json.has(ApiField.ENTRY)) {

                item.setEntries(EntryParser.parse(json.getJSONArray(ApiField.ENTRY)));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return item;
    }


}
