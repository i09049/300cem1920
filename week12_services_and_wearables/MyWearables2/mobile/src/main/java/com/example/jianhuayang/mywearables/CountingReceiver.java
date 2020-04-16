package com.example.jianhuayang.mywearables;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jianhuayang on 25/11/2016.
 */

public class CountingReceiver extends BroadcastReceiver {
    public CountingReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(MainActivity.DEBUG_KEY, "on receive");
        String timeElapsed = intent.getStringExtra(CountingService.REPORT_KEY);
        Log.d(MainActivity.DEBUG_KEY, "time elapsed: " + timeElapsed);

        Intent intentNew = new Intent(context, DisplayActivity.class);
        intentNew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentNew.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNew.putExtra(CountingService.REPORT_KEY, timeElapsed);
        context.startActivity(intentNew);
    }

}
