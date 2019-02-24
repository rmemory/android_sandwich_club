package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
//        Log.v("MyTag", "JSON: " + json);
        String mainName = null;
        ArrayList<String> alsoKnownAsList = new ArrayList<String>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        ArrayList<String> ingredientsList = new ArrayList<String>();

        JSONObject sandwichJson = new JSONObject(json);

        Object obj = sandwichJson.getJSONObject("name").get("mainName");
        if (obj instanceof String) {
            mainName = (String)obj;
        }
        JSONArray alsoKnowAsJSONArray =
                sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
        for (int i = 0; i < alsoKnowAsJSONArray.length(); i++) {
            obj = alsoKnowAsJSONArray.get(i);
            if (obj instanceof String)
                alsoKnownAsList.add((String)obj);
        }

        obj = sandwichJson.get("placeOfOrigin");
        if (obj instanceof String) {
            placeOfOrigin = (String)obj;
        }
        obj = sandwichJson.get("description");
        if (obj instanceof String) {
            description = (String)obj;
        }
        obj = sandwichJson.get("image");
        if (obj instanceof String) {
            image = (String)obj;
        }
        JSONArray ingredientsJSONArray =
                sandwichJson.getJSONArray("ingredients");
        for (int i = 0; i < ingredientsJSONArray.length(); i++) {
            obj = ingredientsJSONArray.get(i);
            if (obj instanceof String)
                ingredientsList.add((String)obj);
        }

        return new Sandwich(
                mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                image,
                ingredientsList);
    }
}
