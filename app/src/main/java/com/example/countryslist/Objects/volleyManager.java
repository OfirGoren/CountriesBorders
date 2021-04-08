package com.example.countryslist.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.countryslist.CallBacks.CallBackCountryList;
import com.example.countryslist.Utils.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class volleyManager {

    private final Context context;
    private final ArrayList<CountryDetails> arrayCountryDetails;
    private CallBackCountryList callBackCountryList;

    public volleyManager(Context context) {
        this.context = context;
        this.arrayCountryDetails = new ArrayList<>();
    }

    public void initCallBack(CallBackCountryList callBackCountryList) {
        this.callBackCountryList = callBackCountryList;
    }

    public void jsonParse() {
        String url = "https://restcountries.eu/rest/v2/all";
        RequestQueue mQueue = Volley.newRequestQueue(this.context);
        JsonArrayRequest jsonObRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    addCountriesDetailsToArray(response);
                    activateCallBack();

                }, error -> {

        });

        mQueue.add(jsonObRequest);

    }

    // pass the countries list to countryFragment
    private void activateCallBack() {
        if (this.callBackCountryList != null) {
            this.callBackCountryList.callBackCountyList(arrayCountryDetails);
        }
    }

    private void addCountriesDetailsToArray(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            try {

                JSONObject jsonObject = response.getJSONObject(i);
                CountryDetails countryDetails = new CountryDetails()
                        .setCountryName(jsonObject.getString("name"))
                        .setNativeName(jsonObject.getString("nativeName"))
                        .setBorders(ArrayUtils.convertJsonArrayToArrayListStr(jsonObject, "borders"))
                        .setAlphaThreeCode(jsonObject.getString("alpha3Code"))
                        .setFlag(jsonObject.getString("flag"));
                arrayCountryDetails.add(countryDetails);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}