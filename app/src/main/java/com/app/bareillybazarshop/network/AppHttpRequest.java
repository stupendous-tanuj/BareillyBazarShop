package com.app.bareillybazarshop.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/*
 * AppHttpRequest : get response of json string request.
 */
public class AppHttpRequest extends StringRequest {

    private final AppResponseListener listener;
    private Map<String, String> params;
    private Map<String, File> fileParams;
    private final String url;

    public AppResponseListener getListener() {
        return listener;
    }

    /*
       add key value map of params
     */
    public AppHttpRequest addParam(String key, Object val) {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        if (val != null)
            params.put(key, val.toString());
        return this;
    }


    public AppHttpRequest addFile(String key, File file) {
        if (fileParams == null) {
            fileParams = new HashMap<String, File>();
        }
        fileParams.put(key, file);
        return this;
    }

    @Override
    public String getUrl() {
        if (getMethod() == Method.GET && params != null) {
            String query = getQuery();
            return this.url + query;
        }
        return super.getUrl();
    }

    private String getQuery() {
        if (params != null) {
            StringBuilder b = new StringBuilder("?");
            for (String key : params.keySet()) {
                b.append(key).append("=").append(params.get(key)).append("&");
            }
            return b.toString();
        }
        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    private AppHttpRequest(int method, String url, AppResponseListener listener) {
        super(method, url, listener, listener);
        super.setRetryPolicy(new DefaultRetryPolicy(50000, 1, 1.0f));
        this.listener = listener;
        this.url = url;
    }

    private AppHttpRequest(int method, String url, AppResponseListener listener, int timeout) {
        super(method, url, listener, listener);
        super.setRetryPolicy(new DefaultRetryPolicy(timeout, 1, 1.0f));
        this.listener = listener;
        this.url = url;
    }

    public static AppHttpRequest getGetRequest(String url, AppResponseListener listener) {
        return new AppHttpRequest(Method.GET, url, listener);
    }

    public static AppHttpRequest getGetRequest(String url, AppResponseListener listener, int timeout) {
        return new AppHttpRequest(Method.GET, url, listener, timeout);
    }

    public static AppHttpRequest getPostRequest(String url, AppResponseListener listener) {
        return new AppHttpRequest(Method.POST, url, listener);
    }

    public static AppHttpRequest getPostRequest(String url, AppResponseListener listener, int timeout) {
        return new AppHttpRequest(Method.POST, url, listener, timeout);
    }

    public static AppHttpRequest getPutRequest(String url, AppResponseListener listener) {
        return new AppHttpRequest(Method.PUT, url, listener);
    }

    public static AppHttpRequest getDeleteRequest(String url, AppResponseListener listener) {
        return new AppHttpRequest(Method.DELETE, url, listener);
    }

}
