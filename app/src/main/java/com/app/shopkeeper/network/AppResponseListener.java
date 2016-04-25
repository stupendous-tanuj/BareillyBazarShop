package com.app.shopkeeper.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.shopkeeper.api.output.ErrorObject;
import com.app.shopkeeper.utils.AppUtil;
import com.app.shopkeeper.utils.DialogUtils;
import com.app.shopkeeper.utils.Logger;
import com.google.gson.JsonObject;

/*
 * AppResponseListener : listener for retrive json string of particular class object like T
 */

public abstract class AppResponseListener<T> implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = AppResponseListener.class.getSimpleName();
    private final Context context;
    private Class<T> classType;

    @Override
    public void onErrorResponse(VolleyError error) {
        if (context != null) {
            ErrorObject errorObject = new ErrorObject();
            errorObject.setErrorMessage(error.getMessage());
            _onError(errorObject);
            Logger.INFO("ERROR Volley: ", error.getMessage());
        }
    }

    protected AppResponseListener(Class<T> t, Context context) {
        this.context = context;
        this.classType = t;
    }

    @Override
    public void onResponse(String response) {
        Logger.INFO(TAG, "api root : " + response);
        try {
            JsonObject obj = AppUtil.parseJson(response);
            if (obj.get("status").getAsString().equals("1")) {
                onSuccess(AppUtil.parseJson(obj.getAsJsonObject("response"), classType));
            } else {
                Logger.INFO("ERROR Error: ", obj.get("response").toString());
                _onError(AppUtil.parseJson(obj.get("response"), ErrorObject.class));

            }
        } catch (Exception e) {
            ErrorObject errorObject = new ErrorObject();
            errorObject.setErrorCode(0001);
            errorObject.setErrorMessage(response);
            _onError(errorObject);
            Logger.INFO("ERROR Exception: ", e.getMessage());
        }
    }

    private void _onError(ErrorObject errors) {
        onError(errors);
        DialogUtils.showDialogError(context, errors);
    }


    public abstract void onSuccess(T t);

    public abstract void onError(ErrorObject error);

}
