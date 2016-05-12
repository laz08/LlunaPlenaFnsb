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

            entry.setTitle(json.getString(ApiConstant.TITLE));
            entry.setUpdated(json.getString(ApiConstant.UPDATED));
            entry.setAuthor(AuthorParser.parse(json.getJSONObject(ApiConstant.AUTHOR)));
            entry.setContent(json.getString(ApiConstant.CONTENT));
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return entry;
    }
}
