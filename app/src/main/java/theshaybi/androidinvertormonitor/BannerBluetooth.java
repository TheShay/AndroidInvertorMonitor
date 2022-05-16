package theshaybi.androidinvertormonitor;

import theshaybi.androidinvertormonitor.messages.MeterMessageReader;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/*
 * |-------------------| | BlueTooth Connection Class |
 * |--------------------------------------------------|
 */

public class BannerBluetooth extends Thread {
    private        BluetoothSocket             mmSocket;
    private        BluetoothServerSocket       mmServerSocket;
    private        BluetoothDevice             mmDevice          = null;
    private        InputStream                 mmInStream;
    private        OutputStream                mmOutStream;
    private        String                      address;
    public static  boolean                     isreceive         = false;
    public         boolean                     isConnectionAlive = false;
    private static BluetoothConnectionCallback bt_callback;

    public void setBluetoothConnectionCallback(BluetoothConnectionCallback callback) {
        bt_callback = callback;
    }


    public interface BluetoothConnectionCallback {
        void onConnectionStatusChange(boolean isConnectionSuccessful, BluetoothDevice mmDevice);

        void onBackseatMessage(JSONObject data) throws JSONException;
    }

    /*---------------------------------------------------------------connectToBluetooth-------------------------------------------------------------------*/
    public BannerBluetooth(BluetoothDevice device) {

        this.setName("BlueTooth");
        mmDevice = device;

    }


    /*---------------------------------------------------------------accept-------------------------------------------------------------------*/
    public void accept() {
        // Cancel discovery because it will slow down the connection
        try {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        } catch (Exception e) {
            for (IMessageListener list : Common.msg_listeners.values())
                list.exception("[exception in BlueTooth][connect][" + e.getLocalizedMessage() + "]");
        }

        try {
            if (mmSocket != null) {
                mmSocket.close();
                mmSocket = null;
            }
            if (mmServerSocket != null) {
                mmServerSocket.close();
                mmServerSocket = null;
            }
        } catch (IOException e) {
            Log.e("BlueTooth", "mmSocket.close() method failed ", e);
        }

        try {
            try {
                mmServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("SPP", UUID.fromString("ff156768-b5ad-11e8-96f8-529269fb1459"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            mmSocket = mmServerSocket.accept(30 * 1000);
            if (mmSocket != null) {
                mmInStream = mmSocket.getInputStream();
                mmOutStream = mmSocket.getOutputStream();
                mmDevice = mmSocket.getRemoteDevice();
                Common.backseatDevice = mmDevice;
                address = mmDevice.getAddress();
                mmInStream.available();
                isConnectionAlive = isreceive = true;
                bt_callback.onConnectionStatusChange(true, mmDevice);
                mmServerSocket.close();
                mmServerSocket = null;
                start_io();
            }
        } catch (IOException e) {
            isConnectionAlive = isreceive = false;
            bt_callback.onConnectionStatusChange(false, mmDevice);
            Log.e("BlueTooth", "Socket's listen() method failed", e);
        } catch (Exception e) {
            Log.e("BlueTooth", "Socket's listen() method failed", e);
        }
    }

    /*---------------------------------------------------------------connect-------------------------------------------------------------------*/
    public void connect(String meterType) {

        // Cancel discovery because it will slow down the connection
        try {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
            isreceive = true;
        } catch (Exception e) {
            for (IMessageListener list : Common.msg_listeners.values())
                list.exception("[exception in Meter_bluetooth][connect][" + e.getLocalizedMessage() + "]");
        }

        try {
            synchronized (currentThread()) {
                try {

                    if (mmSocket != null)
                        mmSocket.close();

                    try {
                        mmSocket = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("ff156768-b5ad-11e8-96f8-529269fb1459"));
                    } catch (Exception e) {
                        Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);
                        mmSocket = (BluetoothSocket) m.invoke(mmDevice, 1);
                    }
                    if (mmSocket == null) {
                        isConnectionAlive = false;
                        bt_callback.onConnectionStatusChange(false, mmDevice);
                        return;
                    }

                    mmSocket.connect();
                    mmInStream = mmSocket.getInputStream();
                    mmOutStream = mmSocket.getOutputStream();
                    address = mmDevice.getAddress();
                    mmInStream.available();
                    isConnectionAlive = true;
                    bt_callback.onConnectionStatusChange(true, mmDevice);

                    //                        unlockMeter(meterType);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    isConnectionAlive = false;
                    bt_callback.onConnectionStatusChange(false, mmDevice);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            isConnectionAlive = false;
            bt_callback.onConnectionStatusChange(false, mmDevice);
        }
    }

    /*---------------------------------------------------------------run----------------------------------------------------------------------------------*/
    @Override
    public void run() {

        try {
            currentThread().setName("BlueTooth");
            accept();
        } catch (Exception e) {
            LogExceptionMSG("[exception in BlueTooth][run][" + e.getLocalizedMessage() + "]");
        }


    }// run

    private void start_io() {
        byte[] bytes;
        MeterMessageReader reader = new MeterMessageReader(mmInStream);
        // Keep listening to the InputStream until an exception occurs
        while (isreceive) {
            try {
                // Read from the InputStream
                bytes = reader.getMessage();
                if (bytes != null) {
                    Log.w(getClass().getSimpleName(), "Bytes from BlueTooth <-- " + new String(bytes, "utf-8"));
                    byte[] newBuffer = new byte[bytes.length - 2];
                    System.arraycopy(bytes, 1, newBuffer, 0, bytes.length - 2);
                    JSONObject json = new JSONObject(new String(newBuffer));
                    bt_callback.onBackseatMessage(json);
                }// if bytes!=null
            } catch (IOException e) {
                isConnectionAlive = isreceive = false;
                bt_callback.onConnectionStatusChange(false, mmDevice);
                LogExceptionMSG("[exception in BlueTooth][run][" + e.getLocalizedMessage() + "]");
            } catch (Exception e) {
                LogExceptionMSG("[exception in BlueTooth][run][" + e.getLocalizedMessage() + "]");
            }

        }// while

        try {
            if (mmSocket != null) {
                mmSocket.close();
                mmSocket = null;
            }
        } catch (IOException e) {
            isConnectionAlive = isreceive = false;
            LogExceptionMSG("[exception in BlueTooth][run][" + e.getLocalizedMessage() + "] IOException");
        } catch (Exception re) {
            LogExceptionMSG("[exception in BlueTooth][run][" + re.getLocalizedMessage() + "] ");
        }
    }

    /*----------------------------------------------------------------LogExceptionMSG---------------------------------------------------------------------------*/
    private void LogExceptionMSG(String MSG) {
        for (IMessageListener list : Common.msg_listeners.values()) {
            list.LogException(MSG);
        }
    }

    /*--------------------------------------------------------------cancel--------------------------------------------------------------------------------*/
    public void cancel() {

        isConnectionAlive = isreceive = false;
        try {
            if (mmSocket != null) {
                mmSocket.close();
                mmSocket = null;
            }
            if (mmServerSocket != null) {
                mmServerSocket.close();
                mmServerSocket = null;
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        this.interrupt();
    }// Cancel

    /*---------------------------------------------------------------getAddress-------------------------------------------------------------------*/
    public String getAddress() {

        return address;

    }

}// Class Bluetooth
