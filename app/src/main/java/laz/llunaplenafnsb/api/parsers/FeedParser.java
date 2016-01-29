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

            item.setTitle(NestedStringParser.parseTitle(json.getJSONObject(ApiField.TITLE)));
            item.setSubtitle(NestedStringParser.parseSubtitle(json.getJSONObject(ApiField.SUBTITLE)));
            item.setUpdated(NestedStringParser.parseUpdated(json.getJSONObject(ApiField.UPDATED)));
            if (json.has(ApiField.ENTRY)) {

                item.setEntries(EntryParser.parse(json.getJSONArray(ApiField.ENTRY)));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return item;
    }


}
