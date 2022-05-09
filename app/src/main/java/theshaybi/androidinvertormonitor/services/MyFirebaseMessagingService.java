package theshaybi.androidinvertormonitor.services;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    public MyFirebaseMessagingService() {
//        super();
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage message) {
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        try {
//            Map data = message.getData();
//            if (data.containsKey("MessageType")) {
//                if (data.get("MessageType").equals(BANNER_RESTART)) {
//                    Common.disableBluetoothState();
//                    Common.enableBluetoothState();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
