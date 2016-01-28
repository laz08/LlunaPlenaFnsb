package laz.llunaplenafnsb.api.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import laz.llunaplenafnsb.api.ApiField;
import laz.llunaplenafnsb.items.Entry;

/**
 * Entry parser.
 */
public class EntryParser {

    public static List<Entry> parse(JSONArray jsonArray) {

        ArrayList<Entry> entries = new ArrayList<>();
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

    public static Entry parse(JSONObject json) {

        Entry entry = new Entry();
        try {

            entry.setTitle(NestedStringParser.parseT(json.getJSONObject(ApiField.TITLE)));
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return entry;
    }
}
