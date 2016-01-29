package laz.llunaplenafnsb.api.parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.items.ImageItem;

/**
 * Image parser.
 */
public class ImageParser {

    public static final String TAG = "ImageParser";

    /**
     * Parses an image.
     *
     * @param jsonObject Json Image.
     * @return Image parsed.
     */
    public static ImageItem parse(JSONObject jsonObject) {

        Log.v(TAG, "Parsing image");
        ImageItem image = new ImageItem();
        try {

            image.setWidth(Integer.valueOf(jsonObject.getString(ApiConstant.WIDTH)));
            image.setHeight(Integer.valueOf(jsonObject.getString(ApiConstant.HEIGHT)));
            image.setSrc(jsonObject.getString(ApiConstant.SRC));
            return image;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
