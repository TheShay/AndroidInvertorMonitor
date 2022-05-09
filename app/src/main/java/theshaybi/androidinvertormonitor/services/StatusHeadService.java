//package theshaybi.androidinvertormonitor.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.graphics.PixelFormat;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class StatusHeadService extends Service {
//    private WindowManager mWindowManager;
//    public  View          mChatHeadView;
//    public  LinearLayout  ll_status_buttons, ll_dim_hotspot;
//    public RelativeLayout rl_status_bar, chat_head_root;
//    public TextView tv_status_header, tv_pim_battery_percentage, tv_ingenico_battery_percentage;
//    public ImageView internet_status_pim, wifiStatus, bluetoothStatus, batteryStatus, ingenicoStatus, ingenicoBatteryStatus, meterStatus, btn_drag;
//    private static Timer timer;
//    boolean userExpanded = false;
//
//    public StatusHeadService() {
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //Inflate the chat head layout we created
//        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.chathead_layout, null);
//        btn_drag = (ImageView) mChatHeadView.findViewById(R.id.btn_drag);
//        meterStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_pim_meter_status);
//        ingenicoStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_pim_ingenico_status);
//        ingenicoBatteryStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_pim_ingenico_battery_status);
//        wifiStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_wifi_status);
//        internet_status_pim = (ImageView) mChatHeadView.findViewById(R.id.iv_internet_status_pim);
//        batteryStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_pim_battery_status);
//        bluetoothStatus = (ImageView) mChatHeadView.findViewById(R.id.iv_bluetooth_status);
//        tv_status_header = (TextView) mChatHeadView.findViewById(R.id.tv_status_header);
//        tv_pim_battery_percentage = (TextView) mChatHeadView.findViewById(R.id.tv_pim_battery_percentage);
//        tv_ingenico_battery_percentage = (TextView) mChatHeadView.findViewById(R.id.tv_ingenico_battery_percentage);
//        if (wifiApManager == null)
//            wifiApManager = new WifiApManager(this);
////        Common.checkLocationSettings();
//        //Add the view to the window.
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        //Specify the chat head position
////Initially view will be added to top-left corner
//        Common.currentActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        boolean isInstalled = Common.isPackageInstalled();
//        params.gravity = Gravity.CENTER;
//        if (isInstalled) {
//            params.width = (int) (displayMetrics.widthPixels * 0.90);
//            params.gravity = Gravity.LEFT + Gravity.TOP;
//        }
//        params.x = 0;
//        if (BackSeatStatus.paramsY == -111) {
//            if (isInstalled)
//                params.y = 0;
//            else
//                params.y = (int) (height * 0.70);
//        } else
//            params.y = BackSeatStatus.paramsY;
//
//        btn_drag.setOnTouchListener(new View.OnTouchListener() {
//            private int lastAction;
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//
//                        //remember the initial position.
//                        initialX = params.x;
//                        initialY = params.y;
//
//                        //get the touch location
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//
//                        lastAction = event.getAction();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        //As we implemented on touch listener with ACTION_MOVE,
//                        //we have to check if the previous action was ACTION_DOWN
//                        //to identify if the user clicked the view or not.
//                        initialX = params.x;
//                        initialY = params.y;
//
//                        //get the touch location
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//
//                        lastAction = event.getAction();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        //Calculate the X and Y coordinates of the view.
//                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
//                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
//                        BackSeatStatus.paramsY = params.y;
//
//                        //Update the layout with new X & Y coordinate
//                        mWindowManager.updateViewLayout(mChatHeadView, params);
//                        lastAction = event.getAction();
//                        return true;
//                }
//                return false;
//            }
//        });
//
//        //Add the view to the window
//        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        mWindowManager.addView(mChatHeadView, params);
//
//        ll_status_buttons = (LinearLayout) mChatHeadView.findViewById(R.id.ll_status_buttons);
//        ll_dim_hotspot = (LinearLayout) mChatHeadView.findViewById(R.id.ll_dim_hotspot);
//        chat_head_root = (RelativeLayout) mChatHeadView.findViewById(R.id.chat_head_root);
//        rl_status_bar = (RelativeLayout) mChatHeadView.findViewById(R.id.rl_status_bar);
//        rl_status_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ll_status_buttons.getVisibility() == View.VISIBLE) {
//                    ll_status_buttons.setVisibility(View.GONE);
//                    userExpanded = false;
//                } else {
//                    ll_status_buttons.setVisibility(View.VISIBLE);
//                    userExpanded = true;
//                }
//            }
//        });
//        final ImageView btn_close = (ImageView) mChatHeadView.findViewById(R.id.btn_close);
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopSelf();
//            }
//        });
//        if (timer == null)
//            timer = new Timer();
//        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
//        ll_dim_hotspot.setOnClickListener(new DoubleClickListener() {
//
//            @Override
//            public void onSingleClick(View v) {
//
//            }
//
//            @Override
//            public void onDoubleClick(View v) {
//                if (wifiApManager == null)
//                    wifiApManager = new WifiApManager(StatusHeadService.this);
//                wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
//            }
//        });
//    }
//
//
//
//    private class mainTask extends TimerTask {
//        public void run() {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                public void run() {
//                    String wifiStatus;
//                    if (wifiApManager.isWifiApEnabled())
//                        wifiStatus = GREEN;
//                    else
//                        wifiStatus = GREY;
//
//                    updateStatus(BackSeatStatus.statusMsg, wifiStatus, BackSeatStatus.pimInternetStatus, BackSeatStatus.bluetoothConnectivityStatus, BackSeatStatus.pimBatteryStatus, BackSeatStatus.ingenicoConnectivityStatus, BackSeatStatus.ingenicoBatteryStatus, BackSeatStatus.usbMeterCommunication, BackSeatStatus.isTunnelConnected, BackSeatStatus.pimBatteryLevel, BackSeatStatus.ingenicoBatteryLevel);
//
//                }
//            });
//        }
//    }
//
//    public void updateStatus(String text, String wifiStatus, String internet_status, String bluetoothStatus, String batteryStatus, String ingenicoStatus, String ingenicoBatteryStatus, String meterStatus, String isTunnelConnected, String pimBatteryPercentage, String ingenicoBatteryPercentage) {
//        //check if status changed then show ui if hidden
//        if (chat_head_root.getVisibility() == View.GONE)
//            if (!tv_status_header.getText().toString().equals(text))
//                chat_head_root.setVisibility(View.VISIBLE);
//        boolean allGrean = true;
//        tv_status_header.setText(text);
//        tv_ingenico_battery_percentage.setText(ingenicoBatteryPercentage + "%");
//        tv_pim_battery_percentage.setText(pimBatteryPercentage + "%");
//
//        if (wifiStatus.equalsIgnoreCase(GREEN)) {
//            this.wifiStatus.setImageResource(R.drawable.ic_wifi_green_24dp);
//        } else if (wifiStatus.equalsIgnoreCase(GREY)) {
//            this.wifiStatus.setImageResource(R.drawable.ic_wifi_grey_24dp);
//            allGrean = false;
//        }
//
//        if (internet_status.equalsIgnoreCase(GREEN)) {
//            this.internet_status_pim.setImageResource(R.drawable.ic_wifi_green_24dp);
//        } else if (internet_status.equalsIgnoreCase(GREY)) {
//            this.internet_status_pim.setImageResource(R.drawable.ic_wifi_grey_24dp);
//            allGrean = false;
//        } else if (internet_status.equalsIgnoreCase(CRITICAL)) {
//            this.internet_status_pim.setImageResource(R.drawable.ic_wifi_red);
//            allGrean = false;
//        }
//
//        if (ingenicoBatteryStatus.equalsIgnoreCase(GREEN)) {
//            this.ingenicoBatteryStatus.setImageResource(R.drawable.ic_battery_green_24dp);
//        } else if (ingenicoBatteryStatus.equalsIgnoreCase(GREY)) {
//            this.ingenicoBatteryStatus.setImageResource(R.drawable.ic_battery_unknown_grey_24dp);
//            allGrean = false;
//        } else if (ingenicoBatteryStatus.equalsIgnoreCase(CHARGING)) {
//            this.ingenicoBatteryStatus.setImageResource(R.drawable.ic_battery_charging);
//        } else if (ingenicoBatteryStatus.equalsIgnoreCase(CRITICAL)) {
//            this.ingenicoBatteryStatus.setImageResource(R.drawable.ic_battery_alert_24dp);
//            allGrean = false;
//        }
//
//        if (ingenicoStatus.equalsIgnoreCase(GREEN)) {
//            this.ingenicoStatus.setImageResource(R.drawable.ic_ingenico_24dp);
//        } else if (ingenicoStatus.equalsIgnoreCase(GREY)) {
//            this.ingenicoStatus.setImageResource(R.drawable.ic_ingenico_grey_24dp);
//            allGrean = false;
//        }
//
//        if (batteryStatus.equalsIgnoreCase(GREEN)) {
//            this.batteryStatus.setImageResource(R.drawable.ic_battery_green_24dp);
//        } else if (batteryStatus.equalsIgnoreCase(GREY)) {
//            this.batteryStatus.setImageResource(R.drawable.ic_battery_unknown_grey_24dp);
//            allGrean = false;
//        } else if (batteryStatus.equalsIgnoreCase(CHARGING)) {
//            this.batteryStatus.setImageResource(R.drawable.ic_battery_charging);
//        } else if (batteryStatus.equalsIgnoreCase(CRITICAL)) {
//            this.batteryStatus.setImageResource(R.drawable.ic_battery_alert_24dp);
//            allGrean = false;
//        }
//
//        if (meterStatus.equalsIgnoreCase(GREEN) && isTunnelConnected.equalsIgnoreCase(GREEN)) {
//            this.meterStatus.setImageResource(R.drawable.ic_featured_meter_green_24dp);
//        } else if (meterStatus.equalsIgnoreCase(GREEN)) {
//            //orange
//            this.meterStatus.setImageResource(R.drawable.ic_meter_orange);
//            allGrean = false;
//        } else if (meterStatus.equalsIgnoreCase(GREY)) {
//            this.meterStatus.setImageResource(R.drawable.ic_meter_grey_24dp);
//            allGrean = false;
//        }
//
//        if (bluetoothStatus.equalsIgnoreCase(GREEN)) {
//            this.bluetoothStatus.setImageResource(R.drawable.ic_bluetooth_green_24dp);
//            chat_head_root.setBackgroundResource(R.drawable.pale_background);
//        } else if (bluetoothStatus.equalsIgnoreCase(GREY)) {
//            this.bluetoothStatus.setImageResource(R.drawable.ic_bluetooth_grey_24dp);
//            chat_head_root.setBackgroundResource(R.drawable.pale_background_transparent);
//            allGrean = false;
//        }
//        if (!userExpanded) {
//            if (allGrean == true) {
//                ll_status_buttons.setVisibility(View.GONE);
//            } else {
//                ll_status_buttons.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
//        timer.purge();
//        timer = null;
//    }
//
//    public abstract class DoubleClickListener implements View.OnClickListener {
//
//        private static final long DOUBLE_CLICK_TIME_DELTA = 1000;//milliseconds
//
//        long lastClickTime = 0;
//
//        @Override
//        public void onClick(View v) {
//            long clickTime = System.currentTimeMillis();
//            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
//                onDoubleClick(v);
//            } else {
//                onSingleClick(v);
//            }
//            lastClickTime = clickTime;
//        }
//
//        public abstract void onSingleClick(View v);
//
//        public abstract void onDoubleClick(View v);
//    }
//}