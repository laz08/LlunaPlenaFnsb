package laz.llunaplenafnsb.api.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * EntryItem parser.
 */
public class EntryParser {

    public static final String TAG = "EntryParser";

    /**
     * Parses an array of entries.
     *
     * @param jsonArray JSON array of entries.
     * @return List of parsed entries.
     */
    public static List<EntryItem> parse(JSONArray jsonArray) {

        Log.v(TAG, "Parsing list of entries.");
        Log.v(TAG, "Entries size: " + jsonArray.length());

        ArrayList<EntryItem> entries = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            try {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                entries.add(parse(jsonObject));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entries;
    }

    /**
     * Parses an entry.
     *
     * @param json JSON Entry.
     * @return Parsed entry.
     */
    public static EntryItem parse(JSONObject json) {


//        Log.v(TAG, "Parsing entry");
        EntryItem entry = new EntryItem();
        try {

            entry.setTitle(NestedStringParser.parseTitle(json.getJSONObject(ApiConstant.TITLE)));
//            entry.setLinks(); //TODO: SET LINKS
            entry.setUpdated(NestedStringParser.parseUpdated(json.getJSONObject(ApiConstant.UPDATED)));
            if (json.has(ApiConstant.SUMMARY)) {

                entry.setSummary(NestedStringParser.parseSummary(json.getJSONObject(ApiConstant.SUMMARY)));
            }
            entry.setAuthor(AuthorParser.parseFirstAuthor(json.getJSONArray(ApiConstant.AUTHOR)));
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return entry;
    }
}
