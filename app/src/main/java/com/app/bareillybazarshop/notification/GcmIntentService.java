package com.app.bareillybazarshop.notification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.utils.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getCanonicalName();
    private NotificationCompat.Builder notificationBuilder = null;
    private boolean isChatMode = false;
    private String channelId;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                if (extras != null && extras.size() > 0) {
                    try {

                        String response = extras.getString("ShopCategoryResponse");
                        if (response != null && !response.equalsIgnoreCase("")) {
                            Logger.INFO("GCM RESPONSE :: ", response);
                            JSONObject res = new JSONObject(response);
                            if (res.has("notifyResponse")) {
                                JSONObject data = res.getJSONObject("notifyResponse");
                                String msg = (String) data.get("msg");
                                String type = data.get("type").toString();
                                sendNotification(getResources().getString(R.string.app_name), msg, type);
                                sendMessageBroadcast(msg, type);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        TestGcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendMessageBroadcast(String msg, String type) {
        //  BroadcastManager.getInstance().sendEventBroadcast(msg, type);
    }


    private void sendNotification(String title, String msg, String type) {

        // Build intent for notification content
      /*  PendingIntent pendingIntent;

            Bundle eventBundle = new Bundle();
            eventBundle.putString("eventType", type);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtras(eventBundle);
            intent.setAction(AppConstant.NOTIFATION_ACTION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setContentIntent(pendingIntent);
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationBuilder.setAutoCancel(true);


// Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
// Build the notification and issues it with notification manager.
        notificationManager.notify(AppConstant.NOTIFICATION_ID, notificationBuilder.build());
*/
    }
}
