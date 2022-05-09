package theshaybi.androidinvertormonitor;

import static theshaybi.androidinvertormonitor.Common.disableBluetoothState;
import static theshaybi.androidinvertormonitor.Common.enableBluetoothState;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationSettingsRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import theshaybi.androidinvertormonitor.classes.BackSeatStatus;
import theshaybi.androidinvertormonitor.classes.DownloadFile;
import theshaybi.androidinvertormonitor.classes.Constants;
import theshaybi.androidinvertormonitor.classes.BackSeatStatus;
import theshaybi.androidinvertormonitor.classes.ReceiverManager;
import theshaybi.androidinvertormonitor.receivers.MyAppReceiver;

public class MainActivity extends AppCompatActivity {

    private static final int          CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private static final int          CODE_WRITE_SETTING_APP_PERMISSION   = 2085;
    public static final  int          REQUEST_DATA_USAGE                  = 2086;
    private static final int          REQUEST_CHECK_SETTINGS              = 5;
    private final        int          REQUEST_CODE_LOC                    = 2091;
    final                long         FIFTEEN_SECONDS                     = 15000;
    private final        boolean      permissionrequired                  = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    private              Timer        statusTimer;
    private              TimerTask    statusTimerTask;
    private              Timer        backseatConnectTimer                = new Timer();
    private              TimerTask    bluetoothONTimerTask;
    private              Timer        bluetoothONTimer                    = new Timer();
    private              TimerTask    backseatConnectTimerTask;
    private              String       appLink                             = "";
    public static        boolean      isAppalreadyDownloaded              = false;
    public static DownloadFile downloadFile;
    private static final int          REQUEST_EXTERNAL_STORAGE            = 2092;
    private static       String[]     PERMISSIONS_STORAGE                 = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    BannerBluetooth bannerBluetooth;
    private final BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                assert action != null;
                switch (action) {
                    case BluetoothAdapter.ACTION_STATE_CHANGED:
                        if (intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_ON) {
                            if (bluetoothONTimerTask != null) {
                                bluetoothONTimerTask.cancel();
                                bluetoothONTimer.purge();
                            }
                            bluetoothONTimerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            startBackseatListener();
                                        }
                                    });
                                }
                            };
                            bluetoothONTimer.schedule(bluetoothONTimerTask, 2000);
                        } else if (intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_TURNING_OFF || intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_OFF) {
                            if (bannerBluetooth != null) {
                                bannerBluetooth.cancel();
                                bannerBluetooth = null;
                            }
                            BackSeatStatus.setDefaultStatus();
                            enableBluetoothState();
                        }
                        break;
                    case BluetoothDevice.ACTION_ACL_CONNECTED:
                        //                        BluetoothDevice dvice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        //                        if (dvice.getAddress().equals(bannerBluetooth.getAddress()))
                        //                            Common.showCustomToast(0, "BackSeat Connected", false);
                        break;
                    case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        if (device.getAddress().equals(bannerBluetooth.getAddress())) {
                                                        disableBluetoothState();
                                                        enableBluetoothState();
                                                        startBackseatListener();
//                            BackSeatStatus.setDefaultStatus();
                            //                            Common.showCustomToast(0, "BackSeat Disconnected", true);
                        }
                        break;
                }
            } catch (Exception e) {
                String exception = "[Exception in MainActivity:bluetoothDiscoveryReceiver] \n[" + e.getLocalizedMessage() + "]";
                Log.d("Banner", exception);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        ReceiverManager.init(this).registerReceiver(bluetoothStateReceiver, intentFilter);
//        enableBluetoothState();
        startBackseatListener();
    }

    public void showScreenOne(View view) {
        startActivity(new Intent(this,ScreenOneActivity.class));
    }
    public void showScreenTwo(View view) {
        startActivity(new Intent(this,ScreenTwoActivity.class));
    }

    public void startServiceWithIcabbi(View view) {
        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(MainActivity.this))
            startBannerService();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //                if (BuildConfig.FLAVOR.equalsIgnoreCase("taxius"))
                //                    openDriverApplication(MainActivity.this, "com.iCabbi.DriverMC");
                onBackPressed();
            }
        }, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (permissionrequired) {
            if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
//                if (!Settings.canDrawOverlays(this))
//                    getOverDrawPermission();
//                else if (Settings.System.canWrite(this))
//                    startServiceWithIcabbi(null);
            } else if (requestCode == CODE_WRITE_SETTING_APP_PERMISSION) {
//                if (!Settings.System.canWrite(this))
//                    getWriteSettingPermission();
//                else {
//                    startHotSpot(null);
//                    if (Settings.canDrawOverlays(this))
//                        queryUsageStats("");
//                    startServiceWithIcabbi(null);
//                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
    }


    private void startBannerService() {
//        if (Common.isMyServiceRunning(StatusHeadService.class)) {
//            startService(new Intent(MainActivity.this, StatusHeadService.class));
//        }
    }
//    private void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i("", "All location settings are satisfied.");
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i("", "PendingIntent unable to execute request.");
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
//                        break;
//                }
//            }
//        });
//    }

    private void RelaunchBannerApplication() {
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(this, MyAppReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 500, sender);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessLocationPermission() {
        int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        int readPhoneState = checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
        int writeExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listRequestPermission = new ArrayList<String>();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listRequestPermission.isEmpty()) {
            String[] strRequestPermission = listRequestPermission.toArray(new String[listRequestPermission.size()]);
            requestPermissions(strRequestPermission, REQUEST_CODE_LOC);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOC:
                if (grantResults.length > 0)
                    for (int gr : grantResults) {
                        if (gr != PackageManager.PERMISSION_GRANTED)
                            accessLocationPermission();
                    }
                break;
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0)
//                    downloadNewVersionOfApp(appLink);
                break;
            default:
                return;
        }
        //        if (!Settings.canDrawOverlays(this))
        //            getOverDrawPermission();
        //        if (!Settings.System.canWrite(this))
        //            getWriteSettingPermission();
    }
    private void startBackseatListener() {
        try {
            if (bannerBluetooth == null || !bannerBluetooth.isConnectionAlive) {
                if (bannerBluetooth != null)
                    bannerBluetooth.cancel();

                bannerBluetooth = new BannerBluetooth(null);
                bannerBluetooth.setBluetoothConnectionCallback(new BannerBluetooth.BluetoothConnectionCallback() {
                    @Override
                    public void onConnectionStatusChange(boolean isConnectionSuccessful, BluetoothDevice mmDevice) {
                        if (isConnectionSuccessful) {
                            //bannerBluetooth.start();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startBackseatListener();
                                }
                            });
                            //disableBluetoothState();
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onBackseatMessage(JSONObject jsonObject) throws JSONException {
                        boolean startService = false;
                        if (jsonObject.has("statusMsg")) {
                            if (!BackSeatStatus.statusMsg.equalsIgnoreCase(jsonObject.getString("statusMsg")))
                                startService = true;
                            BackSeatStatus.statusMsg = jsonObject.getString("statusMsg");
                        }

                        if (jsonObject.has("ingenicoConnectivityStatus")) {
                            if (!BackSeatStatus.ingenicoConnectivityStatus.equalsIgnoreCase(jsonObject.getString("ingenicoConnectivityStatus")))
                                startService = true;
                            BackSeatStatus.ingenicoConnectivityStatus = jsonObject.getString("ingenicoConnectivityStatus");
                        }

                        if (jsonObject.has("ingenicoBatteryCharging") && jsonObject.getString("ingenicoBatteryCharging").equalsIgnoreCase(Constants.GREEN)) {
                            BackSeatStatus.ingenicoBatteryStatus = Constants.CHARGING;
                            if (jsonObject.has("ingenicoBatteryLevel"))
                                BackSeatStatus.ingenicoBatteryLevel = jsonObject.getString("ingenicoBatteryLevel");
                        } else if (jsonObject.has("ingenicoBatteryLevel")) {
                            BackSeatStatus.ingenicoBatteryLevel = jsonObject.getString("ingenicoBatteryLevel");
                            int value = Integer.parseInt(BackSeatStatus.ingenicoBatteryLevel);
                            if (value > 20)
                                BackSeatStatus.ingenicoBatteryStatus = Constants.GREEN;
                            else if (value > 0)
                                BackSeatStatus.ingenicoBatteryStatus = Constants.CRITICAL;
                            else
                                BackSeatStatus.ingenicoBatteryStatus = Constants.GREY;
                        }

                        if (jsonObject.has("pimBatteryCharging") && jsonObject.getString("pimBatteryCharging").equalsIgnoreCase(Constants.GREEN)) {
                            BackSeatStatus.pimBatteryStatus = Constants.CHARGING;
                            if (jsonObject.has("pimBatteryLevel"))
                                BackSeatStatus.pimBatteryLevel = jsonObject.getString("pimBatteryLevel");
                        } else if (jsonObject.has("pimBatteryLevel")) {
                            BackSeatStatus.pimBatteryLevel = jsonObject.getString("pimBatteryLevel");
                            int value = Integer.parseInt(BackSeatStatus.pimBatteryLevel);
                            if (value > 20)
                                BackSeatStatus.pimBatteryStatus = Constants.GREEN;
                            else if (value > 0)
                                BackSeatStatus.pimBatteryStatus = Constants.CRITICAL;
                            else
                                BackSeatStatus.pimBatteryStatus = Constants.GREY;
                        }

                        if (jsonObject.has("bluetoothConnectivityStatus")) {
                            if (!BackSeatStatus.bluetoothConnectivityStatus.equalsIgnoreCase(jsonObject.getString("bluetoothConnectivityStatus")))
                                startService = true;
                            BackSeatStatus.bluetoothConnectivityStatus = jsonObject.getString("bluetoothConnectivityStatus");
                        }

                        if (jsonObject.has("serverIP")) {
                            BackSeatStatus.serverIP = jsonObject.getString("serverIP");
                            Common.updateIpAddress(BackSeatStatus.serverIP);
                        }

                        if (jsonObject.has("usbMeterCommunication")) {
                            BackSeatStatus.usbMeterCommunication = jsonObject.getString("usbMeterCommunication");
                        }

                        if (jsonObject.has("isTunnelConnected")) {
                            BackSeatStatus.isTunnelConnected = jsonObject.getString("isTunnelConnected");
                        }

                        if (jsonObject.has("isIngenicoLoggedIn")) {
                            BackSeatStatus.isIngenicoLoggedIn = jsonObject.getString("isIngenicoLoggedIn");
                        }

                        if (jsonObject.has("pimInternetStatus")) {
                            if (!BackSeatStatus.pimInternetStatus.equalsIgnoreCase(jsonObject.getString("pimInternetStatus")))
                                startService = true;
                            if (jsonObject.getString("pimInternetStatus").equalsIgnoreCase(Constants.GREEN))
                                BackSeatStatus.pimInternetStatus = Constants.GREEN;
                            else
                                BackSeatStatus.pimInternetStatus = Constants.CRITICAL;
                        }

                        if (jsonObject.has("vehicleId"))
                            BackSeatStatus.vehicleId = jsonObject.getString("vehicleId");

                        if (jsonObject.has("IMEI"))
                            BackSeatStatus.IMEI = jsonObject.getString("IMEI");

                        BackSeatStatus.updatedTimeStamp = System.currentTimeMillis();

                        if (startService) {
                            if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(MainActivity.this)) {
                                startBannerService();
                            }
                        }
                    }
                });

                if (backseatConnectTimerTask != null) {
                    backseatConnectTimerTask.cancel();
                    backseatConnectTimerTask = null;
                    backseatConnectTimer.purge();
                }
                backseatConnectTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (bannerBluetooth != null)
                                bannerBluetooth.start();
                        } catch (IllegalThreadStateException exception) {
                            exception.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                backseatConnectTimer.schedule(backseatConnectTimerTask, 1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        Common.currentActivity = this;
        Common.currentContext = this;
        Common.isAppOnFront = true;
//        currentCallbackListener = this;
//        int writeStoragePermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int locationPermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int phoneStatePermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        boolean runtimePermisionGiven = (phoneStatePermission != PackageManager.PERMISSION_GRANTED || locationPermission != PackageManager.PERMISSION_GRANTED || writeStoragePermission != PackageManager.PERMISSION_GRANTED);
//        if (permissionrequired && runtimePermisionGiven)
//            accessLocationPermission();
//        else if (permissionrequired && !Settings.canDrawOverlays(this)) {
//            getOverDrawPermission();
//        } else if (permissionrequired && !Settings.System.canWrite(this)) {
//            getWriteSettingPermission();
//        } else {
//            queryUsageStats("");
//            startServiceWithIcabbi(null);
//        }
        enableBluetoothState();
    }

    @Override
    public void onPause() {
        Common.isAppOnFront = false;
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (ReceiverManager.isReceiverRegistered(bluetoothStateReceiver))
            ReceiverManager.init(this).unregisterReceiver(bluetoothStateReceiver);
//        Intent intent = new Intent(MainActivity.this, StatusHeadService.class);
//        stopService(intent);
        if (statusTimer != null) {
            statusTimer.cancel();
            statusTimer.purge();
            statusTimer = null;
        }
        RelaunchBannerApplication();
        super.onDestroy();
    }
} 