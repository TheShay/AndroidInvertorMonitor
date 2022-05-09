package theshaybi.androidinvertormonitor;

import java.io.Serializable;

/**
 * Created by sLatif on 5/10/2017.
 */

public class BluetoothDeviceInfo implements Serializable {
    private String deviceName;
    private String deviceIdentifier;

    public BluetoothDeviceInfo(String deviceName, String deviceIdentifier) {
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;
    }

    public String toString() {
        return this.getDeviceName() + "    " + " \n(" + this.getDeviceIdentifier() + ") \n";
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }


}
