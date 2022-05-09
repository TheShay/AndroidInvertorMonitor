package theshaybi.androidinvertormonitor.interfaces;

import android.app.Activity;
import android.location.Address;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public abstract interface CallbackResponseListener {
    public abstract void callbackResponseReceived(int apiCalled, Activity callingActivity, JSONObject jsonResponse, List<Address> addressList, boolean paramBoolean) throws JSONException;
}
