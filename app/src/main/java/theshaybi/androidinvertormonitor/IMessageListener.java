package theshaybi.androidinvertormonitor;
import theshaybi.androidinvertormonitor.messages.MeterMessage;

public interface IMessageListener {

    void receivedHandshakeResponse(String[] msg);

    void receivedLoginResponse(String[] columns);

    void receivedLogoffResponse(String[] msg);

    void receivedBookinResponse(String msg);

    void receivedAVLResp(String msg);

    void receivedBidUpdate(String msg);

    void receivedTripDetails(String msg);

    void receivedTripOffer(String msg);

    void receivedLocationChange(String address);

    void receivedMeterMessage(MeterMessage msg);

    void receivedBackseatMessage(MeterMessage msg);

    void receivedVivotechMessage(String msg);

    void receivedZFT(String[] rows);

    void receivedFlushBid(String packetString);

    void exception(String exception);

    void exceptionToast(String exception);

    void receivedPaymentResp(String packetString);

    void receivedManifest(String packetString);

    void invalidServerIP(String string);

    void receivedClearTrip(String packetString);

    void receivedTripUpdate(String TripUpdate);

    void receivedSDTripFare(String TripFare);

    void receivedRegisterResponse(String[] columns);

    void receivedAppUpdate(String packetRcvd);

    void enableLoginButton(boolean value);

    void receivedNoShowResponse(String packetRcvd);

//    void receivedTurnONGPS(Status status);

    String getName();

    void receivedVivotechError(String string);

    void receivedForcedLogout(String string);

//    void receivedCreditCardData(CreditCard ccInfo);

    void receivedEstimatedFareResp(String msg);

    void receivedSystemBroadCast(String action);

    void showProgressDialog(String string);

    void hideProgressDialog();

    void receivedHeartBeatChange();

    void receivedPopupMsg(String msg, String showAsToast);

    void receivedTextMsg(String msg);

    void receivedAdvancedMsg(String column);

    void receivedSDInactiveRequest(String Body);

    void receivedSDBreakEnded(String Body);

    void SD_BookOut();

    void LogException(String Message);

    void LogExceptionWithoutToast(String Message);

    void AvlSentNotifier();

    void receivedEmergencyConfirmation();

    void fetchAssignedAndPendingTrips();

//    void receivedTaxiRideData(PIM_TaxiRide data);

    void receivedVerifoneData(byte[] data);
}
