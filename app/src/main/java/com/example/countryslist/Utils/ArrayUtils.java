package com.example.countryslist.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArrayUtils {

    // the method convert from jsonObjectArray to ArrayList
    public static ArrayList<String> convertJsonArrayToArrayListStr(JSONObject jsonObject, String name) throws JSONException {
        ArrayList<String> listData = new ArrayList<>();
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray(name);
            for (int i = 0; i < jsonArray.length(); i++) {
                listData.add(jsonArray.getString(i));
            }
        }
        return listData;

    }

}