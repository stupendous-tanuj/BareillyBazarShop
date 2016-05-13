package com.app.bareillybazarshop.notification;

import android.app.Application;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.app.bareillybazarshop.R;
import com.app.bareillybazarshop.activity.BirthdayWishActivity;
import com.app.bareillybazarshop.activity.UpdateOrderDetailActivity;
import com.app.bareillybazarshop.activity.WebViewActivity;
import com.app.bareillybazarshop.constant.AppConstant;
import com.app.bareillybazarshop.utils.Logger;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getCanonicalName();
    private NotificationCompat.Builder notificationBuilder = null;
    private boolean isChatMode = false;
    private String channelId;
    private static int i = 0;

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
        Logger.INFO("TAG : ", extras.toString());
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "", "", "" );
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(getResources().getString(R.string.app_name), extras.toString(), "", "", "");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                if (extras != null && extras.size() > 0) {
                    //  sendMessageBroadcast(extras.toString(), "");
                    try {
                        String eventType = extras.getString("eventType");
                        if(eventType.equals(AppConstant.NOTIFICATION.ORDER_STATUS)) {
                            String orderId = extras.getString("orderId");
                            String msg = extras.getString("message");
                            String orderStatus = extras.getString("orderStatus");
                            if (msg != null && !msg.equalsIgnoreCase("")) {
                                Logger.INFO("GCM RESPONSE :: ", msg);
                                sendNotification(getResources().getString(R.string.app_name), msg, "", orderId, orderStatus);
                                MediaPlayer mp = MediaPlayer.create(this, R.raw.soho);
                                mp.start();
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                v.vibrate(AppConstant.VIBRATE_TIME);
                                // sendMessageBroadcast(msg, type);
                            }
                        }
                        if(eventType.equals(AppConstant.NOTIFICATION.WEB_LINK)) {
                            String link = extras.getString("link");
                            String msg = extras.getString("message");
                            if (msg != null && !msg.equalsIgnoreCase("")) {
                                sendWebLinkNotification(getResources().getString(R.string.app_name), msg, link);
                            }
                        }
                        if(eventType.equals(AppConstant.NOTIFICATION.BIRTHDAY_WISH)) {
                            String msg = extras.getString("message");
                            if (msg != null && !msg.equalsIgnoreCase("")) {
                                sendBirthdayNotification(getResources().getString(R.string.app_name), msg);
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

    private void sendNotification(String title, String msg, String type, String orderId, String orderStatus) {
        // Build intent for notification content
        PendingIntent pendingIntent;
        Bundle eventBundle = new Bundle();
        eventBundle.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderId);
        eventBundle.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderStatus);
        Intent intent = new Intent(this, UpdateOrderDetailActivity.class);
        intent.putExtras(eventBundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, i++ , intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentIntent(pendingIntent);
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationBuilder.setAutoCancel(true);
        //mp.stop();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void sendBirthdayNotification(String title, String msg) {
        // Build intent for notification content
        PendingIntent pendingIntent;
        Bundle eventBundle = new Bundle();
        Intent intent = new Intent(this, BirthdayWishActivity.class);
        intent.putExtras(eventBundle);
        intent.setAction("ACTION");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentIntent(pendingIntent);
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationBuilder.setAutoCancel(true);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void sendWebLinkNotification(String title, String msg, String link) {

        PendingIntent pendingIntent;
        Bundle eventBundle = new Bundle();
        eventBundle.putString(AppConstant.BUNDLE_KEY.LINK, link);
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtras(eventBundle);
        intent.setAction("ACTION");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
        notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        notificationBuilder.setAutoCancel(true);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(1, notificationBuilder.build());
    }
}
