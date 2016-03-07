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
import java.util.List;

import hackathon.com.taghit.model.GroupsTags;

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

        String packageName = sbn.getPackageName();
        if (packageName.equalsIgnoreCase("com.whatsapp")) {
            Log.i(TAG,"onNotificationPosted - whatsapp notification");

            String ticker ="";
            if(sbn.getNotification().tickerText !=null) {
                ticker = sbn.getNotification().tickerText.toString();
            }
            Bundle extras = sbn.getNotification().extras;
            String group = extras.getString("android.title");
            String[] splitByAssetrix = group.split("@");
            String groupName = splitByAssetrix.length > 1 ? splitByAssetrix[1].trim() : group;
            String message = extras.getCharSequence("android.text").toString();

            // if it is a summary of few massages
            if(message.endsWith("new messages")){};

            // get the group name
            if(groupName.contains("@"))
            {
                // group name appear after '@'
                groupName=groupName.substring(groupName.lastIndexOf("@",groupName.length()));
            }

            GroupsTags.addGroup(groupName);
            Log.i(TAG, "packageName: " + packageName + " from: " + ticker + " group: " + groupName + " message: " + message);

            List<String> tags = GroupsTags.getTags(groupName.toLowerCase());
            if (tags != null) {
                boolean isImportantMessage = false;
                for (String tag :tags) {
                    if (message.toLowerCase().contains(tag) || (ticker!=null && ticker.contains(tag))) {
                        isImportantMessage = true;
                        // we want this message
                    }
                }

                //Remove notification if it is nor important
                if (!isImportantMessage) {
                    Log.i(TAG, "This message should be filtered");
                    this.cancelNotification(sbn.getKey());
                }

                if(isImportantMessage){
                    // todo - create notification
                }
            }
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
