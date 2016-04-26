package com.app.bareillybazarshop.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/*
 * AppNetwork : generate http response of api calls
 */

public class AppNetwork extends HurlStack {

    private static final String TAG = AppNetwork.class.getCanonicalName();

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {

        if(request instanceof JsonObjectRequest){
            additionalHeaders.put("Content-Type", "application/json; charset=utf-8");
        }
       /* if(PreferenceKeeper.getInstance().getAccessToken() != null && !PreferenceKeeper.getInstance().getAccessToken().equalsIgnoreCase(""))
        {
            additionalHeaders.put("authorization",  "bearer "+PreferenceKeeper.getInstance().getAccessToken());
            Logger.INFO(TAG, "authorization,  bearer "+PreferenceKeeper.getInstance().getAccessToken());
        }*/
        return super.performRequest(request, additionalHeaders);
    }
}
