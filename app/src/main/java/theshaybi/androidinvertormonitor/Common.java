package theshaybi.androidinvertormonitor;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import theshaybi.androidinvertormonitor.classes.Constants;
import theshaybi.androidinvertormonitor.interfaces.CallbackResponseListener;

public class Common extends Application {
    public static SharedPreferences userInfoPrefs;
    public static String FCM_ID;
    public static String SENDER_ID = "";
    public static Context currentContext;
    public static Activity currentActivity;
    public static SharedPreferences pref;
    public static Map<String, IMessageListener> msg_listeners = new HashMap<>();
    public static String appVersion = "";
    public static int currentVersionCode = 0;
    public static int registeredVersionCode = 0;
    public static final String SERVER_IP = "";
    public static BluetoothAdapter mBluetoothAdapter = null;
    public static BluetoothDevice taxiMeter;
    public static BannerBluetooth bannerBluetooth = null;

    public static BluetoothDevice backseatDevice = null;
    public static CallbackResponseListener currentCallbackListener = null;
    public static boolean isAppOnFront;

    @Override
    public void onCreate() {
        currentContext = getApplicationContext();
        super.onCreate();
        pref = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        userInfoPrefs = getSharedPreferences("CUSTOMER_DATA_PREF", MODE_PRIVATE);
        appVersion = BuildConfig.VERSION_NAME;
        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        synchronized (userInfoPrefs) {
            FCM_ID = userInfoPrefs.getString("FCM_REG_ID", "");
            registeredVersionCode = userInfoPrefs.getInt("APP_VERSION_CODE", Integer.MIN_VALUE);

        }//synchronized
        //String regid = getRegistrationId(this);

//        if (regid.isEmpty())
//            registerFCMInBackground(getResources().getString(R.string.google_app_id));
    }

//    public static boolean isPackageInstalled() {
//
//        boolean found = false;
//
//        try {
//            PackageManager packageManager = currentContext.getPackageManager();
//            List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
//
//            for (ApplicationInfo packageInfo : packages) {
//                Log.d("Installed", " package :" + packageInfo.packageName);
//                if (packageInfo.packageName.startsWith("itcurves.") && !packageInfo.packageName.contains("mars") && !packageInfo.packageName.contains("banner")) {
//                    found = true;
//                }
//            }
//        } catch (Exception e) {
//            found = false;
//        }
//        return found;
//    }

//    public static void ChangeWifiState(boolean status) {
//        WifiManager wifiManager = (WifiManager) currentContext.getSystemService(Context.WIFI_SERVICE);
//        wifiManager.setWifiEnabled(true);
//    }

    public static void enableBluetoothState() {
        if (!mBluetoothAdapter.isEnabled())
            mBluetoothAdapter.enable();
    }

    public static void disableBluetoothState() {
        if (mBluetoothAdapter.isEnabled())
            mBluetoothAdapter.disable();
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) currentContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
//
//    public static void openDriverApplication(Context context, String packageName) {
//        try {
//            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
//            if (intent != null) {
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getWifiApIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                if (intf.getName().startsWith("wlan") || intf.getName().startsWith("ap") || intf.getName().startsWith("eth")) {
//                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                        InetAddress inetAddress = enumIpAddr.nextElement();
//                        if (!inetAddress.isLoopbackAddress()
//                            && (inetAddress.getAddress().length == 4)) {
//                            return inetAddress.getHostAddress();
//                        }
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//            Toast.makeText(currentContext, "Exception in Getting Ip Address :" + ex.toString(), Toast.LENGTH_LONG).show();
//        }
//        return null;
//    }

    public static void showCustomToast(final int resId, final String toastMsg, final Boolean isError) {
        try {
            currentActivity.runOnUiThread(() -> {
                LayoutInflater inflater = currentActivity.getLayoutInflater();
                View layout = inflater.inflate(R.layout.waitlayout, currentActivity.findViewById(R.id.toast_layout_root));

                if (isError)
                    layout.setBackgroundResource(R.drawable.mars_text_view_pale_background);

                layout.findViewById(R.id.customprogress_progress).setVisibility(View.GONE);

                TextView message = layout.findViewById(R.id.customprogress_text);
                if (toastMsg != null && toastMsg.length() > 0) {
                    message.setText(toastMsg);
                } else if (resId > 0)
                    message.setText(resId);
                else
                    message.setText("");

                Toast toast = new Toast(currentContext);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                if (message.length() > 0)
                    toast.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    /*------------------------------------------------------performBannerStatusUpdate-----------------------------------------------------------------------------*/
//    public static void performBannerStatusUpdate(CallbackResponseListener callbackResponseListener) {
//        currentCallbackListener = callbackResponseListener;
//        HttpVolleyRequests mhttpRequest = new HttpVolleyRequests(currentContext, callbackResponseListener);
//        Map<String, String> data = new HashMap<>();
//        try {
//            data.put("MsgTag", String.valueOf(System.currentTimeMillis()));
//            data.put("Status", BackSeatStatus.statusMsg);
//            data.put("IsMeterConnected", String.valueOf(BackSeatStatus.usbMeterCommunication.equals(Constants.GREEN)));
//            data.put("IsIngenicoConnected", String.valueOf(BackSeatStatus.ingenicoConnectivityStatus.equals(Constants.GREEN)));
//            data.put("IsIngenicoLogined", String.valueOf(BackSeatStatus.isIngenicoLoggedIn.equals(Constants.GREEN)));
//            data.put("IsPIMInternetConnected", String.valueOf(BackSeatStatus.pimInternetStatus.equals(Constants.GREEN)));
//            data.put("IsPIMBluetoothConnected", String.valueOf(BackSeatStatus.isTunnelConnected.equals(Constants.GREEN)));
//            data.put("IsDIMConnected", String.valueOf(BackSeatStatus.bluetoothConnectivityStatus.equals(Constants.GREEN)));
//            data.put("IngenicoBatteryLevel", BackSeatStatus.ingenicoBatteryLevel);
//            data.put("PIMBatteryLevel", BackSeatStatus.pimBatteryLevel);
//            data.put("AppVersion", appVersion);
//            data.put("PushNRegistrationID", FCM_ID);
//            data.put("AppType", "Banner");
//            data.put("DeviceNum", BackSeatStatus.IMEI);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mhttpRequest.postHttp(Constants.APIs.BANNER_STATUS_UPDATE, currentActivity, data, false, 3);
//    }


//    private static int getAppVersionCode(Context context) {
//        try {
//            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            return packageInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            // should never happen
//            throw new RuntimeException("Could not get package name: " + e);
//        }
//    }

//
//    public static String getRegistrationId(Context context) {
//        // Check if app was updated; if so, it must clear the registration ID
//        // since the existing FCM_ID is not guaranteed to work with the new
//        // app version.
//
//        currentVersionCode = getAppVersionCode(context);
//        if (registeredVersionCode != currentVersionCode) {
//            Log.i("FCM", "App version changed.");
//            return "";
//        }
//        return FCM_ID;
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    public static void registerFCMInBackground(String project_id) {
//        SENDER_ID = project_id;
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
////                    FCM_ID = FirebaseInstanceId.getInstance().getToken();
//                    if (FCM_ID != null) {
//                        msg = "Device registered\nID = " + FCM_ID;
//
//                        // Persist the FCM_ID - no need to register again.
//                        userInfoPrefs.edit().putString("FCM_REG_ID", FCM_ID).putInt("APP_VERSION_CODE", currentVersionCode).apply();
//                    } else {
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                registerFCMInBackground(SENDER_ID);
//                            }
//                        }, 15000);
//                    }
//                } catch (Exception ex) {
//                    msg = "Error :" + ex.getMessage();
//                }
//                return msg;
//            }
//        }.execute(null, null, null);
//    }
//
//    public static void updateIpAddress(String ipAddress) {
//        pref.edit().putString("sdhsUrl", ipAddress).apply();
//    }

    public static String reteriveIpAddress() {
        return pref.getString("sdhsUrl", "");
    }


}
