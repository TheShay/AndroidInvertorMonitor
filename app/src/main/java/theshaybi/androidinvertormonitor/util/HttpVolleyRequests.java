package theshaybi.androidinvertormonitor.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import theshaybi.androidinvertormonitor.Common;
import theshaybi.androidinvertormonitor.classes.Constants;
import theshaybi.androidinvertormonitor.interfaces.CallbackResponseListener;


public class HttpVolleyRequests {
    public static RequestQueue mRequestQueue = null;
    private final CallbackResponseListener callBackListener;
    Context context;
    AlertDialog dialog;
    private JSONArray jsonArray;
    private JSONObject tempObj;

    public HttpVolleyRequests(Context context, CallbackResponseListener callBackListener) {
        this.callBackListener = callBackListener;
        this.context = context;
        try {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(context);
            }
        } catch (Exception e) {
            String exception = "[Exception in HttpVolleyRequests:HttpVolleyRequests] \n[" + e.getLocalizedMessage() + "]";
            e.printStackTrace();
        }
    }

    public void getHttpResponse(final int apiNameCallCode, final Activity callerContext, String otherDetails) {

        String URL = Constants.GetURIFor(apiNameCallCode);
        final JsonObjectRequest jsonRequest = new JsonObjectRequest
                (URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                successResponse(response, callerContext, apiNameCallCode);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        errorResponse(error, apiNameCallCode, callerContext);
                    }
                });
// Add the request to the queue
        jsonRequest.setTag(context);
        mRequestQueue.add(jsonRequest);

    }

    public void clearCache() {
        mRequestQueue.getCache().clear();
    }

    public void cancelRequestsOfTag() {
        mRequestQueue.cancelAll(context);
    }

    public void loadImage(final String path, final ImageView image) {
//        if (BookingApplication.hashMapForImages.get(path) != null) {
//            image.setImageBitmap(BookingApplication.hashMapForImages.get(path));
//            if (image.getId() == R.id.iv_splash_parent && BookingApplication.callerContext.getClass().getName().equals(ActivitySplash.class.getName()))
//                image.performClick();
//        } else {
//            ImageRequest request = new ImageRequest(path,
//                    new Response.Listener<Bitmap>() {
//                        @Override
//                        public void onResponse(Bitmap bitmap) {
//                            if (image != null) {
//                                BookingApplication.hashMapForImages.put(path, bitmap);
//                                image.setImageBitmap(bitmap);
//                                if (image.getId() == R.id.iv_splash_parent && BookingApplication.callerContext.getClass().getName().equals(ActivitySplash.class.getName()))
//                                    image.performClick();
//                            }
//                        }
//                    }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888,
//                    new Response.ErrorListener() {
//                        public void onErrorResponse(VolleyError error) {
//                            if (image != null)
//                                if (image.getId() == R.id.iv_main_logo && BookingApplication.callerContext.getClass().getName().equals(ActivitySplash.class.getName()))
//                                    image.performClick();
//                        }
//                    });
//            request.setShouldCache(true);
//            request.setRetryPolicy(new DefaultRetryPolicy(Constants.CONNECTION_TIMEOUT * 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            mRequestQueue.add(request);
//        }
    }

    public static void addCustomRequest(Request request) {
        mRequestQueue.add(request);
    }


    /**
     * @param keyValuePair       postData
     * @param apiNameCallCode    which Api has been Called
     * @param shouldRequestCache whether you want it to cache this data or not
     * @param requestRetries     Number of retires that the request should be executed if network timeout
     */

    public void postHttp(final int apiNameCallCode, final Activity callerContext, Map<String, String> keyValuePair, boolean shouldRequestCache, int requestRetries) {
//        keyValuePair.put("AppID", BookingApplication.appID);
//        keyValuePair.put("UserID", BookingApplication.userInfoPrefs.getString("UserID", "0"));
//        keyValuePair.put("language", BookingApplication.userInfoPrefs.getString("lang", "en"));
        String url = Constants.GetURIFor(apiNameCallCode);
        createRequestForPost(url, apiNameCallCode, callerContext, keyValuePair, shouldRequestCache, requestRetries);

    }

    public void createRequestForPost(String url, final int apiNameCallCode, final Activity callerContext, Map<String, String> keyValuePair, boolean shouldRequestCache, int requestRetries) {
        final Map<String, String> myKeyValue = keyValuePair;

        try {
            JSONObject jsonArray = new JSONObject(keyValuePair);
            Log.d("Volley", "Calling " + Constants.getApiName(apiNameCallCode));
            Log.d("Volley", "Request: " + jsonArray.toString());

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (url, jsonArray,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // the response is already constructed as a JSONObject!
                                    Log.d("Volley", "Response: " + response.toString());
                                    successResponse(response, callerContext, apiNameCallCode);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            errorResponse(error, apiNameCallCode, callerContext);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    return myKeyValue;
                }
            };
            // Add the request to the queue
            jsonRequest.setShouldCache(shouldRequestCache);
            jsonRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.CONNECTION_TIMEOUT * 1000, requestRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonRequest.setTag(context);
            mRequestQueue.add(jsonRequest);
        } catch (Exception e) {
            String exception = "[Exception in HttpVolleyRequests:postHttp] \n[" + e.getLocalizedMessage() + "]";
            e.printStackTrace();
        }
    }

    private void errorResponse(VolleyError error, int apiNameCallCode, Activity callerContext) {
        try {
            Log.d("JSON Fault", Constants.GetURIFor(apiNameCallCode));

            JSONObject JSONResp;
            NetworkResponse response = error.networkResponse;
            if ((response != null) && (response.data != null)) {
                String json = new String(response.data);
                switch (response.statusCode) {
                    case 400:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 401:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 403:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 405:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 406:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 407:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 404:
                        JSONResp = new JSONObject(new String(response.data));
                        if (!JSONResp.has("ReasonPhrase")) {
                            JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                        }
                        break;
                    case 501:
                        JSONResp = new JSONObject();
                        JSONResp.put("ReasonPhrase", "Network error.");
                        break;
                    case 500:
                    case 502:
                    case 503:
                    case 504:
                    case 505:
                        JSONResp = new JSONObject();
                        json = trimMessage(json, "Message");
                        if (json != null) {
                            JSONResp.put("ReasonPhrase", json);
                        } else {
                            JSONResp.put("ReasonPhrase", "Network error.");
                        }
                        break;
                    default:
                        JSONResp = new JSONObject();
                        json = trimMessage(json, "MessageDetail");
                        if (json != null) {
                            JSONResp.put("ReasonPhrase", json);

                        } else {
                            JSONResp.put("ReasonPhrase", "Network error.");
                        }
                        break;
                }
                //Additional cases
            } else {
                JSONResp = new JSONObject();

                if (error.getClass().equals(TimeoutError.class)) {
                    // Show timeout error message
                    JSONResp.put("ReasonPhrase", "Connection Timeout!!!");
                } else if (error.getClass().equals(NoConnectionError.class)) {
                    JSONResp.put("ReasonPhrase", "Server not available, please try again in a few minutes.");
                } else {
                    JSONResp.put("ReasonPhrase", "Unknown Error!!!");
                }

            }
            JSONResp.put("Fault", true);
            JSONResp.put("ResponseType", "Fault");
            if (JSONResp.has("ReasonPhrase")) {
                Log.d("Volley", "Response: " + JSONResp.toString());
//                currentCallbackListener.callbackResponseReceived(apiNameCallCode, callerContext, JSONResp, null, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        error.printStackTrace();
    }


    /**
     * success Response.
     *
     * @param JSONResp JSONObject response from server.
     */
    private void successResponse(JSONObject JSONResp, Activity callerContext, int apiCalled) {
        try {

            try {

                if (JSONResp != null) {
                    //if (apiCalled == BookingApplication.APIs.SubmitReferredBy)
                    //BookingApplication.currentCallbackListener.callbackResponseReceived(BookingApplication.APIs.SubmitReferredBy, callerContext, JSONResp, null, false);
//                    if (apiCalled == Constants.APIs.BANNER_STATUS_UPDATE)
//                        callBackListener.callbackResponseReceived(Constants.APIs.BANNER_STATUS_UPDATE, callerContext, JSONResp, null, true);
//                    else
//                        Common.showCustomToast(0, "Unknown Response\n\n" + JSONResp.toString(), true);
                }
            } catch (Exception e) {
                Common.showCustomToast(0, e.getLocalizedMessage(), true);
            }

        } catch (Exception e) {
            String exception = "[Exception in HttpVolleyRequests:successResponse] \n[" + e.getLocalizedMessage() + "]";
            e.printStackTrace();
        }
    }

    private String trimMessage(String json, String key) {
        String trimmedString;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            String exception = "[Exception in HttpVolleyRequests:trimMessage] \n[" + e.getLocalizedMessage() + "]";
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

}
