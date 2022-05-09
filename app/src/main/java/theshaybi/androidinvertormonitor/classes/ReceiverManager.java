package theshaybi.androidinvertormonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class ReceiverManager {

    private static List<BroadcastReceiver> receivers = new ArrayList<BroadcastReceiver>();
    private static ReceiverManager ref;
    private        Context         context;

    private ReceiverManager(Context context) {
        this.context = context;
    }

    public static synchronized ReceiverManager init(Context context) {
        if (ref == null) ref = new ReceiverManager(context);
        return ref;
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter intentFilter) {
        if (receivers.contains(receiver))
            receivers.remove(receiver);

        receivers.add(receiver);
        return context.registerReceiver(receiver, intentFilter);
    }

    public static boolean isReceiverRegistered(BroadcastReceiver receiver) {
        return receivers.contains(receiver);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (isReceiverRegistered(receiver)) {
            try {
            receivers.remove(receiver);
            context.unregisterReceiver(receiver);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}