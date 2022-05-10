package theshaybi.androidinvertormonitor.classes;

import theshaybi.androidinvertormonitor.Common;


public class BackSeatStatus{

    public static String statusMsg= "PIM NOT CONNECTED - Please Wait";
    public static String ingenicoConnectivityStatus= Constants.GREY;
    public static String ingenicoBatteryStatus= Constants.GREY;
    public static String isIngenicoLoggedIn= Constants.GREY;
    public static String pimBatteryStatus            = Constants.GREY;
    public static String bluetoothConnectivityStatus = Constants.GREY;
    public static String pimInternetStatus           = Constants.GREY;
    public static String usbMeterCommunication       = Constants.GREY;
    public static String isTunnelConnected           = Constants.GREY;
    public static String vehicleId                   = "";
    public static String IMEI                        = "";
    public static String pimBatteryLevel             = "0";
    public static String ingenicoBatteryLevel        = "0";
    public static String serverIP                    = ""; //Common.reteriveIpAddress(); Todo
    public static int    paramsY                     = -111;
    public static Long   updatedTimeStamp            = System.currentTimeMillis();

    public static void setDefaultStatus(){
        statusMsg= "PIM NOT CONNECTED - Please Wait";
        ingenicoConnectivityStatus= Constants.GREY;
        ingenicoBatteryStatus= Constants.GREY;
        isIngenicoLoggedIn= Constants.GREY;
        pimBatteryStatus= Constants.GREY;
        bluetoothConnectivityStatus= Constants.GREY;
        pimInternetStatus= Constants.GREY;
        usbMeterCommunication = Constants.GREY;
        isTunnelConnected = Constants.GREY;
        vehicleId= "";
        IMEI= "";
        updatedTimeStamp= System.currentTimeMillis();
    }
}
