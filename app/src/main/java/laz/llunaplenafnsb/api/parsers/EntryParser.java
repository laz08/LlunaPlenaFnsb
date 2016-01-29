package laz.llunaplenafnsb.api.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laz.llunaplenafnsb.api.ApiField;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * EntryItem parser.
 */
public class EntryParser {

    /**
     * Parses an array of entries.
     *
     * @param jsonArray JSON array of entries.
     * @return List of parsed entries.
     */
    public static List<EntryItem> parse(JSONArray jsonArray) {

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

        EntryItem entry = new EntryItem();
        try {

            entry.setTitle(NestedStringParser.parseTitle(json.getJSONObject(ApiField.TITLE)));
//            entry.setLinks(); //TODO: SET LINKS
            entry.setUpdated(NestedStringParser.parseUpdated(json.getJSONObject(ApiField.UPDATED)));
            if (json.has(ApiField.SUMMARY)) {

                entry.setSummary(NestedStringParser.parseSummary(json.getJSONObject(ApiField.SUMMARY)));
            }
            entry.setAuthor(AuthorParser.parseFirstAuthor(json.getJSONArray(ApiField.AUTHOR)));
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return entry;
    }
}
