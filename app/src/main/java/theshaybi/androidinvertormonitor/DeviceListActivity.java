/*
* Copyright (C) 2009 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package theshaybi.androidinvertormonitor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class DeviceListActivity extends Activity {

    String title = "Select a device to connect";

    // Debugging
    private static final String  TAG = "DeviceListActivity";
    private static final boolean D   = true;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String EXTRA_DEVICE_TYPE    = "device_type";

    // Member fields
    private BluetoothAdapter     mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        title = this.getIntent().getStringExtra("Title");

        setContentView(R.layout.activity_bluetooth_list);
        TextView tv_title = (TextView) findViewById(R.id.title_paired_devices);
        tv_title.setText(title);
// Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

// Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

// Initialize array adapters. One for already paired devices and
// one for newly discovered devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

// Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

// Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

// Register for broadcasts when a device is discovered
        IntentFilter bluetoothDiscoveryIntentFilter = new IntentFilter();
        bluetoothDiscoveryIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        bluetoothDiscoveryIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, bluetoothDiscoveryIntentFilter);

// Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

// Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

            // If there are paired devices, add each one to the ArrayAdapter
            if (pairedDevices.size() > 0) {
                scanButton.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                for (BluetoothDevice device : pairedDevices) {
                    mPairedDevicesArrayAdapter.add(getDeviceName(getDeviceName(device.getName())) + "\n" + device.getAddress());
                }
            } else {
                scanButton.setVisibility(View.VISIBLE);
                String noDevices = "No devices have been paired";
                mPairedDevicesArrayAdapter.add(noDevices);
            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        if (D)
            Log.d(TAG, "doDiscovery()");

// Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle("scanning for devices...");

// Turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

//        if (AVL_Service.prefs.getBoolean("VeriFoneDevice", AVL_Service.SDVeriFoneDeviceAvailable) && TaxiPlexer.VMeterAddress != null) {
//            findViewById(R.id.title_new_devices).setVisibility(View.GONE);
//            findViewById(R.id.new_devices).setVisibility(View.GONE);
//            setTitle("Searching...");
//        }

// If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

// Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    // The on-click listener for all devices in the ListViews
    private final OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String name = info.substring(0, info.length() - 17);
            String address = info.substring(info.length() - 17);

            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
            intent.putExtra(EXTRA_DEVICE_TYPE, title.substring(title.lastIndexOf(' ')));

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    // The BroadcastReceiver that listens for discovered devices and
// changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mNewDevicesArrayAdapter.clear();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mBtAdapter.cancelDiscovery();
                setProgressBarIndeterminateVisibility(false);
                setTitle(title);
//                if (TaxiPlexer.VMeterAddress != null && mNewDevicesArrayAdapter.getPosition(TaxiPlexer.VMeterAddress) <= 0) {
//                    if (mNewDevicesArrayAdapter.getCount() == 0) {
//                        DeviceListActivity.this.finish();
//                    }
//                } else if (AVL_Service.prefs.getBoolean("VeriFoneDevice", AVL_Service.SDVeriFoneDeviceAvailable) && TaxiPlexer.VMeterAddress == null) {
//                    findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
//                    findViewById(R.id.paired_devices).setVisibility(View.VISIBLE);
//                    findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
//                    findViewById(R.id.new_devices).setVisibility(View.VISIBLE);
//                } else {
                    if (mNewDevicesArrayAdapter.getCount() == 0) {
                        String noDevices = "No devices found";
                        mNewDevicesArrayAdapter.add(noDevices);
                        counter = 0;
                    }
//                }

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // If it's already paired, skip it, because it's been listed already
//                if (TaxiPlexer.VMeterAddress == null || TaxiPlexer.VMeterAddress.equalsIgnoreCase("")) {
//                    if (device.getBondState() != BluetoothDevice.BOND_BONDED && mNewDevicesArrayAdapter.getPosition(getDeviceName(device.getName()) + "\n" + device.getAddress()) == -1) {
//                        mNewDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
//                    }
//                } else {
//                    if (device.getAddress().equalsIgnoreCase(TaxiPlexer.VMeterAddress)) {
//                        mNewDevicesArrayAdapter.add(getDeviceName(device.getName()) + "\n" + device.getAddress());
//                        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);
//                        findViewById(R.id.new_devices).setVisibility(View.VISIBLE);
//                    }
//                }
            }
        }
    };

    /*-----------------------------------------------------------------getDeviceName-----------------------------------------------------------------*/
    private String getDeviceName(String name) {
        if (name != null)
            if (name.contains("P25") || name.contains("P10"))
                return "BlueBambooPrinter";
            else if (name.contains("Amp'ed"))
                return "VivoTech 8800";
            else if (name.contains("eb101") || name.contains("Regency") || TextUtils.isDigitsOnly(name))
                return "Taxi Meter";
            else if (name.toUpperCase().startsWith("DOME") || name.toUpperCase().startsWith("SPP") || name.toUpperCase().startsWith("OBD"))
                return "Dome Light";

// else
// if (name.trim().length() == 4)
// try {
// if (Integer.parseInt(name) > 0)
// return "Taxi Meter";
// } catch (NumberFormatException nfe) {
// return name;
// }
        return name;
    }

}
