package laz.llunaplenafnsb.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.api.ApiField;
import laz.llunaplenafnsb.items.ImageItem;

/**
 * Image parser.
 */
public class ImageParser {

    /**
     * Parses an image.
     *
     * @param jsonObject Json Image.
     * @return Image parsed.
     */
    public static ImageItem parse(JSONObject jsonObject) {

        ImageItem image = new ImageItem();
        try {

            image.setWidth(Integer.valueOf(jsonObject.getString(ApiField.WIDTH)));
            image.setHeight(Integer.valueOf(jsonObject.getString(ApiField.HEIGHT)));
            image.setSrc(jsonObject.getString(ApiField.SRC));
            return image;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
