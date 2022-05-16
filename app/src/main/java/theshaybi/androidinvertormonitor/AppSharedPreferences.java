package theshaybi.androidinvertormonitor;


import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;


import org.json.JSONObject;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;


//
public class AppSharedPreferences {
    public static SharedPreferences backSeatSharedPreferences;
//
////    public static InetAddress getServerAddress(Context context) {
////        backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////        try {
////            String serverAddr = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SERVER_ADDRESS, null);
////            if (serverAddr != null)
////                return InetAddress.getByName(serverAddr);
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:getServerAddress()] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////        }
////        return null;
////    }
////
////    public static void setServerAddress(Context context, InetAddress serverAddress) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
////            if (serverAddress == null)
////                editor.remove(StaticClasses.SharedPreferenceKeys.SERVER_ADDRESS);
////            else
////                editor.putString(StaticClasses.SharedPreferenceKeys.SERVER_ADDRESS, serverAddress.getHostAddress());
////
////            editor.apply();
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:setServerAddress()] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////        }
////    }
//
//    public static boolean isCompanyProperty(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_COMPANY_PROPERTY, false);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:isCompanyProperty()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return false;
//        }
//    }
//
//    public static void setCompanyProperty(Context context, boolean isCompanyProperty) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_COMPANY_PROPERTY, isCompanyProperty);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setCompanyProperty()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static boolean isCompleteVideoDownloaded(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_VIDEO_DOWNLOAD_COMPLETE, false);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:isCompleteVideoDownloaded()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return false;
//        }
//    }
//
//    public static void setVideoDownloadingProgress(Context context, boolean isDownloadCompleted) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_VIDEO_DOWNLOAD_COMPLETE, isDownloadCompleted);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setVideoDownloadingProgress()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getServerURL(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SERVER_URL, BuildConfig.SERVER_URL);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getServerURL()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void saveServerURL(Context context, String serverURL) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.SERVER_URL, serverURL);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:saveServerURL()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getLastDownloadedAppVersion(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.LAST_SAVED_APP_VERSION, BuildConfig.VERSION_NAME.replace(".", ""));
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getLastDownloadedAppVersion()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return BuildConfig.VERSION_NAME;
//        }
//    }
//
//    public static void saveLastDownloadedAppVersion(Context context, String appVersion) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.LAST_SAVED_APP_VERSION, appVersion);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:saveLastDownloadedAppVersion()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getDeviceID(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DEVICE_ID, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setDeviceID()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setDeviceID(Context context, String value) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.DEVICE_ID, value);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setDeviceID()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//
//    public static String getDriverID(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_ID, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getDriverID()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setDriverID(Context context, String driverID) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_ID, driverID);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setDriverID()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getConfirmationNumber(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_TRIP_CONFIR_NUMBER, "-1");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getConfirmationNumber()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    private static void setConfirmationNumber(Context context, String confirmationNumber) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_TRIP_CONFIR_NUMBER, confirmationNumber);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setConfirmationNumber()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static void saveSDPerformPaymentRequest(Context context, Map<String, String> data, String confirmationNumber) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            if (backSeatSharedPreferences != null) {
//                editor.remove(StaticClasses.SharedPreferenceKeys.SDPerformPayment).commit();
//                if (data != null) {
//                    JSONObject jsonObject = new JSONObject(data);
//                    String jsonString = jsonObject.toString();
//                    editor.putString(StaticClasses.SharedPreferenceKeys.SDPerformPayment, jsonString).commit();
//                }
//                setConfirmationNumber(context, data != null ? data.get("ConfirmationNo") : confirmationNumber);
//            }
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:saveSDPerformPaymentRequest()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static Map<String, String> getSDPerformPaymentRequest(Context context) {
//        try {
//            Map<String, String> mapObject = new HashMap<>();
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            if (backSeatSharedPreferences != null) {
//                String jsonString = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SDPerformPayment, (new JSONObject()).toString());
//                JSONObject jsonObject = new JSONObject(jsonString);
//                Iterator<String> keysItr = jsonObject.keys();
//                while (keysItr.hasNext()) {
//                    String k = keysItr.next();
//                    String v = (String) jsonObject.get(k);
//                    mapObject.put(k, v);
//                }
//            }
//            return mapObject;
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getSDPerformPaymentRequest()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//        return null;
//    }
//
////    public static void saveTripData(Context context, TripData tripData, String calledFrom) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
////            if (tripData != null) {
////                if (ReceiptActivity.receiptActivity == null && CaptureSignature.captureSignatureActivity == null) {
////                    editor.putString(StaticClasses.SharedPreferenceKeys.SERVICE_ID, tripData.getServiceID());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CONFIRMATION_NUMBER, tripData.getConfirmationNumber());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.JOB_ID, tripData.getJobID());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CARD_TYPE, tripData.getCardType());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.AUTH_CODE, tripData.getAuthCode());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PRE_AUTH_AMOUNT, String.valueOf(tripData.getPreAuthAmount()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PRE_PAY_SALE_AMOUNT, String.valueOf(tripData.getPrePaySaleAmount()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CACHED_TRANSACTION_ID, String.valueOf(tripData.getCachedTransactionId()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CACHED_CREDIT_REFUNDABLE_TRANSACTION_ID, String.valueOf(tripData.getCacheCreditRefundableTransactionId()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.TRIP_PAY_STATUS, tripData.getTripPayStatus());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PAYMENT_METHOD, tripData.getPaymentMethod());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.TRIP_STATE, tripData.getTripState());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.FINAL_SALE_RESPONSE, tripData.getFinalSaleResponse());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.DISTANCE, tripData.getDistance());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.TIP_STATE, tripData.getTipState());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.INQUIRY_RESPONSE, tripData.getInquiryResponse());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CARD_NUMBER, String.valueOf(tripData.getCardNumber()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CARD_EXPIRY, String.valueOf(tripData.getCardExpiry()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CARD_BALANCE, String.valueOf(tripData.getCardBalance()));
////                    editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_SIGNATURE_REQUIRED, tripData.isSignatureRequired());
////                    editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_TIP_APPLICABLE, tripData.isTipApplicable());
////                    editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_MAX_TIP_IN_PERCENTAGE, tripData.isMaxTipInPercentage());
////                    editor.putString(StaticClasses.SharedPreferenceKeys.MAX_TIP, String.valueOf(tripData.getMaxTip()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_ADDRESS, String.valueOf(tripData.getPickUpAddress()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_LATITUDE, String.valueOf(tripData.getPickUpLatitude()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_LONGITUDE, String.valueOf(tripData.getPickUpLongitude()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_ADDRESS, String.valueOf(tripData.getDropOffAddress()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LATITUDE, String.valueOf(tripData.getDropOFFLatitude()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LONGITUDE, String.valueOf(tripData.getDropOFFLongitude()));
////
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_FARE, String.valueOf(tripData.getCurrentFare()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_EXTRA, String.valueOf(tripData.getCurrentExtra()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_TIP, String.valueOf(tripData.getCurrentTip()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.PAID_AMOUNT, String.valueOf(tripData.getPaidAmount()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.MIN_PRICE, String.valueOf(tripData.getMinPrice()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.FIXED_PRICE, String.valueOf(tripData.getFixedPrice()));
////                    editor.putString(StaticClasses.SharedPreferenceKeys.TOTAL, String.valueOf(tripData.getTotal()));
////                    StaticFunctions.WriteinLogFile("SaveTripData", calledFrom + " - Trip with Confirmation number: " + tripData.getConfirmationNumber() + "\n");
////                }
////            } else {
////                editor.putString(StaticClasses.SharedPreferenceKeys.CONFIRMATION_NUMBER, "-1");
////                editor.putString(StaticClasses.SharedPreferenceKeys.SERVICE_ID, "-1");
////                editor.putString(StaticClasses.SharedPreferenceKeys.JOB_ID, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CARD_TYPE, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.AUTH_CODE, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PRE_AUTH_AMOUNT, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PRE_PAY_SALE_AMOUNT, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CACHED_TRANSACTION_ID, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CACHED_CREDIT_REFUNDABLE_TRANSACTION_ID, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.TRIP_PAY_STATUS, "0");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PAYMENT_METHOD, "Cash");
////                editor.putString(StaticClasses.SharedPreferenceKeys.TRIP_STATE, "NONE");
////                editor.putString(StaticClasses.SharedPreferenceKeys.FINAL_SALE_RESPONSE, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.DISTANCE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.TIP_STATE, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.INQUIRY_RESPONSE, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CARD_NUMBER, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CARD_EXPIRY, "");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CARD_BALANCE, "0.00");
////                editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_SIGNATURE_REQUIRED, false);
////                editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_TIP_APPLICABLE, true);
////                editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_MAX_TIP_IN_PERCENTAGE, false);
////                editor.putString(StaticClasses.SharedPreferenceKeys.MAX_TIP, "100");
////
////                editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_ADDRESS, "Unknown Address");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_LATITUDE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PICK_UP_LONGITUDE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_ADDRESS, "Unknown Address");
////                editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LATITUDE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LONGITUDE, "0.00");
////
////                editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_FARE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_EXTRA, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_TIP, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.PAID_AMOUNT, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.MIN_PRICE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.FIXED_PRICE, "0.00");
////                editor.putString(StaticClasses.SharedPreferenceKeys.TOTAL, "0.00");
////                StaticFunctions.WriteinLogFile("SaveTripData", calledFrom + " - Trip null Confirmation number -1" + "\n");
////            }
////            editor.commit();
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:saveTripData:calledFrom ] \n[" + calledFrom + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////        }
////    }
////
////    public static TripData retrieveTripData(Context context, String calledFrom) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            String confirmationNumber = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CONFIRMATION_NUMBER, "-1");
////            StaticFunctions.WriteinLogFile("RetrieveTripData", calledFrom + " - Trip with Confirmation number: " + confirmationNumber + "\n");
////            if (!confirmationNumber.equals("-1")) {
////                String serviceID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SERVICE_ID, "");
////                String jobID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.JOB_ID, "");
////                String cardType = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CARD_TYPE, "");
////                String authCode = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.AUTH_CODE, "");
////                double preAuthAmount = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.PRE_AUTH_AMOUNT, "0.00"));
////                double prePaySaleAmount = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.PRE_PAY_SALE_AMOUNT, "0.00"));
////                String cachedTokenId = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CACHED_TRANSACTION_ID, "");
////                String cachedCreditRefundableTransactionID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CACHED_CREDIT_REFUNDABLE_TRANSACTION_ID, "0");
////                String tripPayStatus = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.TRIP_PAY_STATUS, "0");
////                String paymentMethod = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.PAYMENT_METHOD, "");
////                String tripState = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.TRIP_STATE, "NONE");
////                String finalSaleResponse = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.FINAL_SALE_RESPONSE, "");
////                String distance = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DISTANCE, "");
////                String tipState = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.TIP_STATE, "");
////                String inquiryResponse = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.INQUIRY_RESPONSE, "");
////                String cardNumber = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CARD_NUMBER, "");
////                String cardExpiry = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CARD_EXPIRY, "");
////
////                boolean isSignatureRequired = backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_SIGNATURE_REQUIRED, false);
////                boolean isTipApplicable = backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_TIP_APPLICABLE, false);
////                boolean isMaxTipInPercentage = backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_MAX_TIP_IN_PERCENTAGE, false);
////                boolean isAnnouncementON = backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_ANNOUNCEMENT_ON, false);
////
////                double maxTip = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.MAX_TIP, "0"));
////                double dropOffLatitude = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LATITUDE, "0.00"));
////                double dropOffLongitude = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DROP_OFF_LONGITUDE, "0.00"));
////                double cardBalance = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CARD_BALANCE, "0"));
////                double currentFare = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_FARE, "0.00"));
////                double currentExtra = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_EXTRA, "0.00"));
////                double currentTip = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_TIP, "0.00"));
////                double paidAmount = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.PAID_AMOUNT, "0.00"));
////                double minPrice = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.MIN_PRICE, "0.00"));
////                double fixedPrice = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.FIXED_PRICE, "0.00"));
////                double total = Double.valueOf(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.TOTAL, "0.00"));
////
////                return new TripData(serviceID, confirmationNumber, jobID, cardType, authCode, tripPayStatus, preAuthAmount, prePaySaleAmount, cachedTokenId, cachedCreditRefundableTransactionID, paymentMethod, tripState, inquiryResponse, finalSaleResponse, distance, tipState, cardBalance, cardNumber, cardExpiry, isSignatureRequired, isTipApplicable, isMaxTipInPercentage, isAnnouncementON, maxTip, dropOffLatitude, dropOffLongitude, currentFare, currentExtra, currentTip, paidAmount, minPrice, fixedPrice, total);
////            }
////            return null;
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:retrieveTripData:calledFrom ] \n[" + calledFrom + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////            return null;
////        }
////
////    }
////
////    public static void saveHeartBeatInfo(Context context, HeartBeatInfo heartBeatInfo) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
////            if (heartBeatInfo != null) {
////                editor.putString(StaticClasses.SharedPreferenceKeys.CLIENT_NAME, heartBeatInfo.getClientName());
////                editor.putString(StaticClasses.SharedPreferenceKeys.CLIENT_LOGO, heartBeatInfo.getClientLogo());
////                editor.putString(StaticClasses.SharedPreferenceKeys.CLIENT_CURRENCY, heartBeatInfo.getClientCurrency());
////                editor.putString(StaticClasses.SharedPreferenceKeys.CLIENT_ID, heartBeatInfo.getClientID());
////                editor.putString(StaticClasses.SharedPreferenceKeys.VEHICLE_NUMBER, heartBeatInfo.getVehicleNumber());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_NUMBER, heartBeatInfo.getDriverNumber());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_NAME, heartBeatInfo.getDriverName());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_STATUS, heartBeatInfo.getDriverStatus());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DISTANCE_UNIT, heartBeatInfo.getDistanceUnit());
////                editor.putString(StaticClasses.SharedPreferenceKeys.CLIENT_PHONE_NUMBER, heartBeatInfo.getClientPhoneNumber());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_CARD_EXPIRY, heartBeatInfo.getDriverCardExpiry());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_FACE_CARD_ID, heartBeatInfo.getDriverFaceCardID());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_PICTURE, heartBeatInfo.getDriverPicture());
////                editor.putString(StaticClasses.SharedPreferenceKeys.DRIVER_FACE_CARD_IMAGE, heartBeatInfo.getDriverFaceCardImage());
////                editor.apply();
////            }
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:saveHeartBeatInfo] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////        }
////    }
////
////    public static HeartBeatInfo getHeartBeatInfo(Context context) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            String clientName = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CLIENT_NAME, "");
////            String clientLogo = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CLIENT_LOGO, "");
////            String clientCurrency = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CLIENT_CURRENCY, "");
////            String clientID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CLIENT_ID, "");
////            String vehicleNumber = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.VEHICLE_NUMBER, "");
////            String driverNumber = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_NUMBER, "");
////            String driverName = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_NAME, "");
////            String driverStatus = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_STATUS, "");
////            String distanceUnit = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DISTANCE_UNIT, "");
////            String clientPhoneNumber = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CLIENT_PHONE_NUMBER, "");
////            String driverCardExpiry = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_CARD_EXPIRY, "");
////            String driverFaceCardID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_FACE_CARD_ID, "");
////            String driverPicture = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_PICTURE, "");
////            String driverFaceCardImage = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.DRIVER_FACE_CARD_IMAGE, "");
////
////            HeartBeatInfo heartBeatInfo = new HeartBeatInfo(clientName, clientLogo, clientCurrency, clientID, vehicleNumber, driverNumber, driverName, driverStatus, distanceUnit);
////            heartBeatInfo.setClientPhoneNumber(clientPhoneNumber);
////            heartBeatInfo.setDriverCardExpiry(driverCardExpiry);
////            heartBeatInfo.setDriverFaceCardID(driverFaceCardID);
////            heartBeatInfo.setDriverPicture(driverPicture);
////            heartBeatInfo.setDriverFaceCardImage(driverFaceCardImage);
////            return heartBeatInfo;
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:getHeartBeatInfo] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////            return null;
////        }
////
////    }
//
//    public static String getVehicleNo(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.VEHICLE_ID, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getVehicleNo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setVehicleNo(Context context, String driverID) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.VEHICLE_ID, driverID);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setVehicleNo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getWifiName(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.WIFI_NAME, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getWifiName] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setWifiName(Context context, String wifiName) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.WIFI_NAME, wifiName);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setWifiName] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getWifiPassword(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.WIFI_PASS, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getWifiPassword] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setWifiPassword(Context context, String wifiPass) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.WIFI_PASS, wifiPass);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setWifiPassword] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getPassword(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SDPIMPassword, StaticDeclarations.SDPIMPassword) + new SimpleDateFormat("dd", Locale.US).format(Calendar.getInstance().getTime());
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getPassword] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setPassword(Context context, String pimPassword) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.SDPIMPassword, pimPassword);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setPassword] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getPIM_ID(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.PIM_ID, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getPIM_ID] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setPIM_ID(Context context, String pimID) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.PIM_ID, pimID);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setPIM_ID] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getAffiliateID(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.AFFILIATE_ID, "-1");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getAffiliateID] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setAffiliateID(Context context, String affiliateID) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.AFFILIATE_ID, affiliateID);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setAffiliateID] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getLanguage(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.APP_LANGUAGE, "en");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getLanguage] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "en";
//        }
//    }
//
//    public static void setLanguage(Context context, String language) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.APP_LANGUAGE, language);
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setLanguage] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getRestartDeviceReason(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.RESTART_APP_REASON, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getRestartDeviceReason] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void setRestartDeviceReason(Context context, String reason) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.RESTART_APP_REASON, reason);
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setRestartDeviceReason:callled from ] \n[" + reason + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static void setLastReceivedDataUsage(Context context, double lastForegroundUsage) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.LAST_FOREGROUND_DATA_USAGE, String.valueOf(lastForegroundUsage));
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setLastReceivedDataUsage:] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static double getLastDataReceived(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return Double.parseDouble(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.LAST_FOREGROUND_DATA_USAGE, "0.00"));
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getLastDataReceived] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return 0.00;
//        }
//    }
//
//    public static void setLastSentDataUsage(Context context, double lastBackgroundUsage) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.LAST_BACKGROUND_DATA_USAGE, String.valueOf(lastBackgroundUsage));
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setLastSentDataUsage:] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static double getLastDataSent(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return Double.parseDouble(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.LAST_BACKGROUND_DATA_USAGE, "0.00"));
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getLastDataSent] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return 0.00;
//        }
//    }
//
//    public static void setCurrentDataUsage(Context context, double foregroundUsage, double backgroundUsage, double totalDataUsage) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_FOREGROUND_DATA_USAGE, String.format(Locale.US, "%.2f", foregroundUsage));
//            editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_BACKGROUND_DATA_USAGE, String.format(Locale.US, "%.2f", backgroundUsage));
//            editor.putString(StaticClasses.SharedPreferenceKeys.CURRENT_TOTAL_USAGE, String.format(Locale.US, "%.2f", totalDataUsage));
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setCurrentDataUsage] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
////    public static InternetDataUsage getCurrentDataUsage(Context context) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            double foreGroundUsage = Double.parseDouble(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_FOREGROUND_DATA_USAGE, "0.00"));
////            double backGroundUsage = Double.parseDouble(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_BACKGROUND_DATA_USAGE, "0.00"));
////            double totalUsage = Double.parseDouble(backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.CURRENT_TOTAL_USAGE, "0.00"));
////
////            return new InternetDataUsage(foreGroundUsage, backGroundUsage, totalUsage);
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:getCurrentDataUsage] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////            return null;
////        }
////    }
//
//    public static void savePreviousDate(Context context, String lastDate) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.LAST_DATE, lastDate);
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:savePreviousDate()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static String getPreviousDate(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.LAST_DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getPreviousDate()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
//        }
//    }
//
//    public static void setIsDataAlreadyCleared(Context context, boolean isDataCleared) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_DATA_ALREADY_CLEARED, isDataCleared);
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setIsDataAlreadyCleared()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static boolean isDataAlreadyCleared(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_DATA_ALREADY_CLEARED, false);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:isClearDataRequired()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return false;
//        }
//    }
//
//    public static void setIsDataLimitReached(Context context, boolean isDataLimitReached) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_DATA_LIMIT_REACHED, isDataLimitReached);
//            editor.commit();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setIsDataLimitReached()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static boolean isDataLimitReached(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_DATA_LIMIT_REACHED, false);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:isDataLimitReached()] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return false;
//        }
//    }
//
//    public static void setDomeLightInfo(Context context, String name, String address) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.DOME_LIGHT_NAME, name);
//            editor.putString(StaticClasses.SharedPreferenceKeys.DOME_LIGHT_ADDRESS, address);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setDomeLightInfo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static void setIngenicoInfo(Context context, String name, String address) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putString(StaticClasses.SharedPreferenceKeys.INGENICO_NAME, name);
//            editor.putString(StaticClasses.SharedPreferenceKeys.INGENICO_ADDR, address);
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setIngenicoInfo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
////    public static Device getSwiperBluetoothDeviceInfo(Context context) {
////        try {
////            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////            String deviceType = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_TYPE, "");
////            String deviceName = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_NAME, "");
////            String deviceID = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_IDENTIFIER, "");
////            String commType = backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_COMM_TYPE, "Bluetooth");
////            if (deviceName.isEmpty())
////                return null;
////            else
////                return new Device(DeviceType.getEnum(deviceType), CommunicationType.getEnum(commType), deviceName, deviceID);
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:getSwiperBluetoothDeviceInfo] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////            return null;
////        }
////    }
//
////    public static void saveSwiperBluetoothDeviceInfo(Context context, Device device) {
////        try {
////            if (device != null) {
////                backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
////                SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
////                editor.putString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_TYPE, device.getName().startsWith("RP75") ? "RP750x" : "RP450c");
////                editor.putString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_NAME, device.getName());
////                editor.putString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_IDENTIFIER, device.getIdentifier());
////                editor.putString(StaticClasses.SharedPreferenceKeys.SWIPER_DEVICE_COMM_TYPE, device.getConnectionType().name());
////                editor.apply();
////            }
////        } catch (Exception e) {
////            String exception = "[Exception in AppSharedPreferences:saveSwiperBluetoothDeviceInfo] \n[" + e.getLocalizedMessage() + "]";
////            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
////        }
////    }
//
    public static void saveTunnelBluetoothInfo(Context context, String address) {
        try {
            if (!address.isEmpty()) {
                backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
                editor.putString("TUNNEL_CONNECTION_ID", address);
                editor.apply();
            }
        } catch (Exception e) {
            String exception = "[Exception in AppSharedPreferences:saveTunnelBluetoothInfo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
        }
    }
//
//    public static String getTunnelBluetoothInfo(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getString(StaticClasses.SharedPreferenceKeys.TUNNEL_CONNECTION_ID, "");
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getTunnelBluetoothInfo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return "";
//        }
//    }
//
//    public static void clearIngenicoDeviceInfo(Context context) {
//        try {
//            SharedPreferences prefs = context.getSharedPreferences(StaticClasses.SharedPreferenceKeys.INGENICO_BLUETOOTH, Context.MODE_PRIVATE);
//            prefs.edit().clear().apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:clearIngenicoDeviceInfo] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
//
//    public static boolean getSystemBarSettings(Context context) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            return backSeatSharedPreferences.getBoolean(StaticClasses.SharedPreferenceKeys.IS_System_Bar_Shown, false);
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:getSystemBarSettings] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//            return false;
//        }
//    }
//
//    static void setSystemBarSettings(Context context, boolean isSystemSettingShown) {
//        try {
//            backSeatSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = backSeatSharedPreferences.edit();
//            editor.putBoolean(StaticClasses.SharedPreferenceKeys.IS_System_Bar_Shown, isSystemSettingShown);
//            userInfoPrefs.edit().putBoolean("IS_System_Bar_Shown", isSystemSettingShown).apply();
//            editor.apply();
//        } catch (Exception e) {
//            String exception = "[Exception in AppSharedPreferences:setSystemBarSettings] \n[" + e.getLocalizedMessage() + "]";
//            StaticFunctions.WriteinLogFile("AppSharedPreferences", exception + "\n");
//        }
//    }
}
