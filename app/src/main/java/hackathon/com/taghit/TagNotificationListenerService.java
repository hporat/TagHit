package hackathon.com.taghit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
            //Remove notification in all cases
           this.cancelNotification(sbn.getKey());

           /* if (Context.NOTIFICATION_SERVICE!=null) {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(sbn.getId());
            }*/

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

            Log.i(TAG, "packageName: " + packageName + " from: " + ticker + " group: " + groupName + " message: " + message);
            GroupsTags.addGroup(groupName);
            List<String> tags = GroupsTags.getTags(groupName);
            boolean isImportantMessage = false;
            if (tags != null && tags.size()>0) {
                for (String tag : tags) {
                    if (message.toLowerCase().contains(tag) ||
                            (ticker != null && ticker.toLowerCase().contains(tag)) ||
                            (group != null && group.toLowerCase().contains(tag))) {
                        isImportantMessage = true;
                        // we want this message
                    }
                }
            } else {
                isImportantMessage = true;
            }
            if(message.endsWith("new messages")) isImportantMessage=false;
            if(message.endsWith("chats") && message.contains("messages")) isImportantMessage=false;
            if(isImportantMessage){
                NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
                ncomp.setContentTitle(group);
                ncomp.setContentText(message);
                ncomp.setTicker(ticker);
                ncomp.setSmallIcon(R.drawable.notification_icon);
                ncomp.setLargeIcon(sbn.getNotification().largeIcon);
                ncomp.setAutoCancel(true);
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                ncomp.setAutoCancel(true);
                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(launchIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                ncomp.setContentIntent(resultPendingIntent);
                nManager.notify((int)System.currentTimeMillis(),ncomp.build());
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
