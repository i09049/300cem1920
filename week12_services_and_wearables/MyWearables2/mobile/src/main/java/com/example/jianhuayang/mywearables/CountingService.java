package com.example.jianhuayang.mywearables;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by jianhuayang on 25/11/2016.
 */

public class CountingService extends IntentService {

    public static final String REPORT_KEY = "REPORT_KEY";
    public static final String INTENT_KEY = "com.example.jianhuayang.mywearables.BROADCAST";

    public CountingService() {
        super("BackgroundCounting");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {



        int count = 0;
        while (count < 10) {
            synchronized (this) {
                try {
                    wait(1000);
                    count++;
                    Log.d(MainActivity.DEBUG_KEY, Integer.toString(count));

//                     send info using intent
//                    Intent intentNew = new Intent(getBaseContext(), DisplayActivity.class);
//                    intentNew.putExtra(REPORT_KEY, Integer.toString(count));
//                    intentNew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intentNew.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    getBaseContext().startActivity(intentNew);

//                    Intent localIntent = new Intent();
//                    localIntent.setAction(INTENT_KEY);
//                    localIntent.putExtra(REPORT_KEY, Integer.toString(count));
//                    sendBroadcast(localIntent);
//                    Log.d(MainActivity.DEBUG_KEY, "broadcasted");

                    //https://stackoverflow.com/questions/43093260/notification-not-showing-in-android-8-oreo
                    String CHANNEL_ID = "my_channel_01";
                    CharSequence name = "Channel human readable title";// The user-visible name of the channel.
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                            importance);
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this, CHANNEL_ID)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("My Wearables")
                                    .setContentText("Time elapsed: " + Integer.toString(count) + " seconds.");
                    Intent resultIntent = new Intent(this, DisplayActivity.class);
                    resultIntent.putExtra(REPORT_KEY, Integer.toString(count));

                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
                    taskStackBuilder.addParentStack(DisplayActivity.class);
                    taskStackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);

                    notificationManager.notify(123123, builder.build());
                } catch (Exception e) {
                }
            }
        }
        Log.d(MainActivity.DEBUG_KEY, "service finished");

    }
}
