package com.app.bareillybazarshop.notification;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.bareillybazarshop.activity.HomeActivity;
import com.app.bareillybazarshop.utils.PreferenceKeeper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GcmIdGenerator {

    private String TAG = HomeActivity.class.getSimpleName();
    private final Context context;
    private final PreferenceKeeper prefs;
    private GoogleCloudMessaging gcm;

    public GcmIdGenerator(Context context) {
        this.context = context;
        prefs = PreferenceKeeper.getInstance();
    }

    /**
     * generates a gcm registration id
     */
    public void getGCMRegId() {
        new GCMIdGeneratorTask().execute();
    }

    private class GCMIdGeneratorTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String regid = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register("GCM SENDER ID");

            } catch (IOException ex) {

            }
            Log.i(TAG, "REG ID : " + regid);

            return regid;
        }

        @Override
        protected void onPostExecute(String regid) {
            super.onPostExecute(regid);
          //  prefs.setGCMReg(regid);
        }
    }

}
