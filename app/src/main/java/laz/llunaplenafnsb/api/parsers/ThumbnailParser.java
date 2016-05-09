package laz.llunaplenafnsb.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.ThumbnailItem;

/**
 * Image parser.
 */
public class ThumbnailParser {

//    public static final String TAG = "ThumbnailParser";

    /**
     * Parses an image.
     *
     * @param jsonObject Json Image.
     * @return Image parsed.
     */
    public static ThumbnailItem parse(JSONObject jsonObject) {

//        Log.v(TAG, "Parsing image");
        ThumbnailItem image = new ThumbnailItem();
        try {

            image.setWidth(Integer.valueOf(jsonObject.optString(ApiConstant.WIDTH)));
            image.setHeight(Integer.valueOf(jsonObject.optString(ApiConstant.HEIGHT)));
            if (jsonObject.has(ApiConstant.SRC)) {

                image.setUrl(jsonObject.getString(ApiConstant.SRC));
            } else if (jsonObject.has(ApiConstant.URL)) {

                image.setUrl(jsonObject.getString(ApiConstant.URL));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return image;
    }
}
