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

            Log.v(TAG, "Name: " + json.getString(ApiConstant.NAME));
            item.setName(json.getString(ApiConstant.NAME));
            item.setDescription(json.getString(ApiConstant.DESCRIPTION));
            item.setUpdated(json.getString(ApiConstant.UPDATED));
            item.setUrl(json.getString(ApiConstant.URL));

            JSONObject jsonPosts = json.getJSONObject(ApiConstant.POSTS);
            item.setPostsUrl(jsonPosts.getString(ApiConstant.SELF_LINK));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return item;
    }


}
