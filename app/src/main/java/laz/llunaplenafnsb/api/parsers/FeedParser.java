package laz.llunaplenafnsb.api.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.Feed;

/**
 * Feed parser.
 */
public class FeedParser {

    public static final String TAG = "FeedParser";

    public static Feed parse(JSONObject json) {

//        Log.v(TAG, "Parsing first author.");

        Feed item = new Feed();
        try {

            item.setTitle(NestedStringParser.parseTitle(json.getJSONObject(ApiConstant.TITLE)));
            item.setSubtitle(NestedStringParser.parseSubtitle(json.getJSONObject(ApiConstant.SUBTITLE)));
            item.setUpdated(NestedStringParser.parseUpdated(json.getJSONObject(ApiConstant.UPDATED)));
            if (json.has(ApiConstant.ENTRY)) {

                item.setEntries(EntryParser.parse(json.getJSONArray(ApiConstant.ENTRY)));
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return item;
    }


}
