package com.app.bareillybazarshop.notification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Class that receives the GCM
 */

/*A WakefulBroadcastReceiver is a helper BroadcastReceiver that can receive a device
  wakeup event and then pass on the work to a Service ensuring that the device(CPUs)doesn’t
  go back to sleep while the Service is executing.It creates and manages a partial wake lock
  for your app.The service is started using startWakefulService() which is similar to startService()*/

public class TestGcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private String TAG = TestGcmBroadcastReceiver.class.getSimpleName();
    private final int WAKELOG_AQUIRE_TIME = 10000;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        // Change the current result code of this broadcast; only works with  broadcasts sent through
        PowerManager.WakeLock screenOn = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        screenOn.acquire(WAKELOG_AQUIRE_TIME);

        setResultCode(Activity.RESULT_OK);

    }
}
