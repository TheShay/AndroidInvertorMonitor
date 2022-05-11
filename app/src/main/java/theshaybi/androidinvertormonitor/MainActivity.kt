package theshaybi.androidinvertormonitor

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import org.json.JSONException
import org.json.JSONObject
import theshaybi.androidinvertormonitor.BannerBluetooth.BluetoothConnectionCallback
import theshaybi.androidinvertormonitor.classes.BackSeatStatus
import theshaybi.androidinvertormonitor.classes.Constants
import theshaybi.androidinvertormonitor.classes.DownloadFile
import theshaybi.androidinvertormonitor.classes.ReceiverManager
import theshaybi.androidinvertormonitor.receivers.MyAppReceiver
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
        private const val CODE_WRITE_SETTING_APP_PERMISSION = 2085
        const val REQUEST_DATA_USAGE = 2086
        private const val REQUEST_CHECK_SETTINGS = 5
        var isAppalreadyDownloaded = false
        @JvmField var downloadFile: DownloadFile? = null
        private const val REQUEST_EXTERNAL_STORAGE = 2092
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val REQUEST_CODE_LOC = 2091
    val FIFTEEN_SECONDS: Long = 15000
    private val permissionrequired = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    private var statusTimer: Timer? = null
    private val statusTimerTask: TimerTask? = null
    private val backseatConnectTimer = Timer()
    private var bluetoothONTimerTask: TimerTask? = null
    private val bluetoothONTimer = Timer()
    private var backseatConnectTimerTask: TimerTask? = null
    private val appLink = ""
    var bannerBluetooth: BannerBluetooth? = null
    private lateinit var navController: NavController

    private val bluetoothStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val action = intent.action!!
                when (action) {
                    BluetoothAdapter.ACTION_STATE_CHANGED -> if (intent.extras!!.getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_ON) {
                        if (bluetoothONTimerTask != null) {
                            bluetoothONTimerTask!!.cancel()
                            bluetoothONTimer.purge()
                        }
                        bluetoothONTimerTask = object : TimerTask() {
                            override fun run() {
                                runOnUiThread { startBackseatListener() }
                            }
                        }
                        bluetoothONTimer.schedule(bluetoothONTimerTask, 2000)
                    } else if (intent.extras!!.getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_TURNING_OFF || intent.extras!!.getInt(BluetoothAdapter.EXTRA_STATE) == BluetoothAdapter.STATE_OFF) {
                        if (bannerBluetooth != null) {
                            bannerBluetooth!!.cancel()
                            bannerBluetooth = null
                        }
                        BackSeatStatus.setDefaultStatus()
                        Common.enableBluetoothState()
                    }
                    BluetoothDevice.ACTION_ACL_CONNECTED -> {}
                    BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        if (device!!.address == bannerBluetooth!!.address) {
                            Common.disableBluetoothState()
                            Common.enableBluetoothState()
                            startBackseatListener()
                            //                            BackSeatStatus.setDefaultStatus();
                            //                            Common.showCustomToast(0, "BackSeat Disconnected", true);
                        }
                    }
                }
            } catch (e: Exception) {
                val exception = """
                    [Exception in MainActivity:bluetoothDiscoveryReceiver] 
                    [${e.localizedMessage}]
                    """.trimIndent()
                Log.d("Banner", exception)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        ReceiverManager.init(this).registerReceiver(bluetoothStateReceiver, intentFilter)
        //        enableBluetoothState();
        startBackseatListener()

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment?.navController!!
        // Make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController)
    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun startServiceWithIcabbi(view: View?) {
        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this@MainActivity)) startBannerService()
        Timer().schedule(object : TimerTask() {
            override fun run() {
                //                if (BuildConfig.FLAVOR.equalsIgnoreCase("taxius"))
                //                    openDriverApplication(MainActivity.this, "com.iCabbi.DriverMC");
                onBackPressed()
            }
        }, 100)
    }

    @RequiresApi(api = Build.VERSION_CODES.M) override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {}
    private fun startBannerService() {
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
    private fun relaunchBannerApplication() {
        val am = this.getSystemService(ALARM_SERVICE) as AlarmManager
        val mIntent = Intent(this, MyAppReceiver::class.java)
        val sender = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        am[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 500] = sender
    }

    @RequiresApi(api = Build.VERSION_CODES.M) private fun accessLocationPermission() {
        val accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        val accessFineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val readPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
        val writeExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readExternalStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        val listRequestPermission: MutableList<String> = ArrayList()
        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listRequestPermission.isEmpty()) {
            val strRequestPermission = listRequestPermission.toTypedArray()
            requestPermissions(strRequestPermission, REQUEST_CODE_LOC)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M) override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOC -> if (grantResults.isNotEmpty()) for (gr in grantResults) {
                if (gr != PackageManager.PERMISSION_GRANTED) accessLocationPermission()
            }
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty())
                    //downloadNewVersionOfApp(appLink);
                    //break
                return
            }
            else -> return
        }
        //        if (!Settings.canDrawOverlays(this))
        //            getOverDrawPermission();
        //        if (!Settings.System.canWrite(this))
        //            getWriteSettingPermission();
    }

    private fun startBackseatListener() {
        try {
            if (bannerBluetooth == null || !bannerBluetooth!!.isConnectionAlive) {
                if (bannerBluetooth != null) bannerBluetooth!!.cancel()
                bannerBluetooth = BannerBluetooth(null)
                bannerBluetooth!!.setBluetoothConnectionCallback(object : BluetoothConnectionCallback {
                    override fun onConnectionStatusChange(isConnectionSuccessful: Boolean, mmDevice: BluetoothDevice) {
                        if (isConnectionSuccessful) {
                            //bannerBluetooth.start();
                        } else {
                            runOnUiThread { startBackseatListener() }
                            //disableBluetoothState();
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M) @Throws(JSONException::class) override fun onBackseatMessage(jsonObject: JSONObject) {
                        var startService = false
                        if (jsonObject.has("statusMsg")) {
                            if (!BackSeatStatus.statusMsg.equals(jsonObject.getString("statusMsg"), ignoreCase = true)) startService = true
                            BackSeatStatus.statusMsg = jsonObject.getString("statusMsg")
                        }
                        if (jsonObject.has("ingenicoConnectivityStatus")) {
                            if (!BackSeatStatus.ingenicoConnectivityStatus.equals(jsonObject.getString("ingenicoConnectivityStatus"), ignoreCase = true)) startService = true
                            BackSeatStatus.ingenicoConnectivityStatus = jsonObject.getString("ingenicoConnectivityStatus")
                        }
                        if (jsonObject.has("ingenicoBatteryCharging") && jsonObject.getString("ingenicoBatteryCharging").equals(Constants.GREEN, ignoreCase = true)) {
                            BackSeatStatus.ingenicoBatteryStatus = Constants.CHARGING
                            if (jsonObject.has("ingenicoBatteryLevel")) BackSeatStatus.ingenicoBatteryLevel = jsonObject.getString("ingenicoBatteryLevel")
                        } else if (jsonObject.has("ingenicoBatteryLevel")) {
                            BackSeatStatus.ingenicoBatteryLevel = jsonObject.getString("ingenicoBatteryLevel")
                            val value = BackSeatStatus.ingenicoBatteryLevel.toInt()
                            if (value > 20) BackSeatStatus.ingenicoBatteryStatus = Constants.GREEN else if (value > 0) BackSeatStatus.ingenicoBatteryStatus = Constants.CRITICAL else BackSeatStatus.ingenicoBatteryStatus = Constants.GREY
                        }
                        if (jsonObject.has("pimBatteryCharging") && jsonObject.getString("pimBatteryCharging").equals(Constants.GREEN, ignoreCase = true)) {
                            BackSeatStatus.pimBatteryStatus = Constants.CHARGING
                            if (jsonObject.has("pimBatteryLevel")) BackSeatStatus.pimBatteryLevel = jsonObject.getString("pimBatteryLevel")
                        } else if (jsonObject.has("pimBatteryLevel")) {
                            BackSeatStatus.pimBatteryLevel = jsonObject.getString("pimBatteryLevel")
                            val value = BackSeatStatus.pimBatteryLevel.toInt()
                            if (value > 20) BackSeatStatus.pimBatteryStatus = Constants.GREEN else if (value > 0) BackSeatStatus.pimBatteryStatus = Constants.CRITICAL else BackSeatStatus.pimBatteryStatus = Constants.GREY
                        }
                        if (jsonObject.has("bluetoothConnectivityStatus")) {
                            if (!BackSeatStatus.bluetoothConnectivityStatus.equals(jsonObject.getString("bluetoothConnectivityStatus"), ignoreCase = true)) startService = true
                            BackSeatStatus.bluetoothConnectivityStatus = jsonObject.getString("bluetoothConnectivityStatus")
                        }
                        if (jsonObject.has("serverIP")) {
                            BackSeatStatus.serverIP = jsonObject.getString("serverIP")
                            //Common.updateIpAddress(BackSeatStatus.serverIP)
                        }
                        if (jsonObject.has("usbMeterCommunication")) {
                            BackSeatStatus.usbMeterCommunication = jsonObject.getString("usbMeterCommunication")
                        }
                        if (jsonObject.has("isTunnelConnected")) {
                            BackSeatStatus.isTunnelConnected = jsonObject.getString("isTunnelConnected")
                        }
                        if (jsonObject.has("isIngenicoLoggedIn")) {
                            BackSeatStatus.isIngenicoLoggedIn = jsonObject.getString("isIngenicoLoggedIn")
                        }
                        if (jsonObject.has("pimInternetStatus")) {
                            if (!BackSeatStatus.pimInternetStatus.equals(jsonObject.getString("pimInternetStatus"), ignoreCase = true)) startService = true
                            if (jsonObject.getString("pimInternetStatus")
                                    .equals(Constants.GREEN, ignoreCase = true)) BackSeatStatus.pimInternetStatus = Constants.GREEN else BackSeatStatus.pimInternetStatus = Constants.CRITICAL
                        }
                        if (jsonObject.has("vehicleId")) BackSeatStatus.vehicleId = jsonObject.getString("vehicleId")
                        if (jsonObject.has("IMEI")) BackSeatStatus.IMEI = jsonObject.getString("IMEI")
                        BackSeatStatus.updatedTimeStamp = System.currentTimeMillis()
                        if (startService) {
                            if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this@MainActivity)) {
                                startBannerService()
                            }
                        }
                    }
                })
                if (backseatConnectTimerTask != null) {
                    backseatConnectTimerTask!!.cancel()
                    backseatConnectTimerTask = null
                    backseatConnectTimer.purge()
                }
                backseatConnectTimerTask = object : TimerTask() {
                    override fun run() {
                        try {
                            if (bannerBluetooth != null) bannerBluetooth!!.start()
                        } catch (exception: IllegalThreadStateException) {
                            exception.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                backseatConnectTimer.schedule(backseatConnectTimerTask, 1000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M) public override fun onResume() {
        super.onResume()
        Common.currentActivity = this
        Common.currentContext = this
        Common.isAppOnFront = true
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
        Common.enableBluetoothState()
    }

    public override fun onPause() {
        Common.isAppOnFront = false
        super.onPause()
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onDestroy() {
        if (ReceiverManager.isReceiverRegistered(bluetoothStateReceiver)) ReceiverManager.init(this).unregisterReceiver(bluetoothStateReceiver)
        //        Intent intent = new Intent(MainActivity.this, StatusHeadService.class);
//        stopService(intent);
        if (statusTimer != null) {
            statusTimer!!.cancel()
            statusTimer!!.purge()
            statusTimer = null
        }
        relaunchBannerApplication()
        super.onDestroy()
    }
}