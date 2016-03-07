package hackathon.com.taghit;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by hporat on 07/03/2016.
 */
public class TagNotificationListenerService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private ServiceBroadcastReceiver serviceBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("TagNotificationListenerService onCreate() has been initialized");
        serviceBroadcastReceiver = new ServiceBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.tagHit");
        registerReceiver(serviceBroadcastReceiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceBroadcastReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG, "onNotificationPosted");
        String pack = sbn.getPackageName();
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
        Bitmap id = sbn.getNotification().largeIcon;


        Log.i("Msg","pack "+pack);
        Log.i("Msg","ticker "+ticker);
        Log.i("Msg","title "+title);
        Log.i("Msg","text "+text);

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        msgrcv.putExtra("title", title);
        msgrcv.putExtra("text", text);
        if(id != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            msgrcv.putExtra("icon",byteArray);
        }



    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "NotificationRemove");
    }

    class ServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
