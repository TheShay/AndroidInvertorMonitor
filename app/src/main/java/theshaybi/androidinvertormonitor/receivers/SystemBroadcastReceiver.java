package theshaybi.androidinvertormonitor.receivers;

import theshaybi.androidinvertormonitor.Common;
import theshaybi.androidinvertormonitor.BuildConfig;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class SystemBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case "android.intent.action.ACTION_POWER_CONNECTED":
                if (Common.currentActivity == null)
                    startApplication(context);
                break;

            case "android.intent.action.BOOT_COMPLETED":
                Log.d(getClass().toString(), "BOOT COMPLETE RECEIVED IN PIM STATUS APP");

//                if (Common.currentActivity == null && BuildConfig.FLAVOR.equalsIgnoreCase("taxius"))
                    startApplication(context);
                break;

        }
    }// onReceive

    private void startApplication(Context context) {
//        Intent pushIntent = new Intent(context, MainActivity.class);
//        pushIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(pushIntent);
    }
}