package com.app.bareillybazarshop.network;

import android.content.Context;
import android.util.Log;

import com.app.bareillybazarshop.api.output.ErrorObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class LocationResponseListener<S> extends AppResponseListener<ArrayList> {
    public LocationResponseListener(Class<ArrayList> t, Context context) {
        super(t, context);
    }

    @Override
    public void onSuccess(ArrayList t) {

    }

    @Override
    public void onError(ErrorObject error) {

    }

    @Override
    public void onResponse(String response) {
        if (response != null) {
            ArrayList resultList = null;
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray predsJsonArray = jsonObject.getJSONArray("predictions");
                resultList = new ArrayList(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                }
            } catch (JSONException e) {
                Log.e("", "Cannot process JSON results", e);
            }
            onSuccess(resultList);
        }
    }
}