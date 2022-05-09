package theshaybi.androidinvertormonitor.classes;


public class Constants {

    public static final String PREFS_NAME = "PrefsFile";
    public static final String GREEN="1";
    public static final String GREY="0";
    public static final String CRITICAL="3";
    public static final String CHARGING="4";

    public static final int CONNECTION_TIMEOUT=15;
    public static final int HOTSPOT_FAILED=111;
    public static final int APP_UPDATE=112;
    public static class MessageType{
        public static final String BANNER_RESTART = "BANNER_RESTART";
    }
    public class APIs{
        public static final int BANNER_STATUS_UPDATE = 1001;
    }
    public static String GetURIFor(int api) {

        switch (api) {
            case APIs.BANNER_STATUS_UPDATE:
                return BackSeatStatus.serverIP + "/PIMAndBanner/PIMOrBannerStatusUpdate";
            default:
                return "http://";
        }
    }

    public static String getApiName(int api) {

        switch (api) {
            case APIs.BANNER_STATUS_UPDATE:
                return "BannerStatusUpdate";
            default:
                return "Unknown";
        }
    }

}
