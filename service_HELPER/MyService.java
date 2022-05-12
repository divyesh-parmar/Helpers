

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        System.out.println("Package:_" + getRunningApp());
        if (MainActivity.RunningApp.size() != 0)
            MainActivity.RunningApp.removeAll(MainActivity.RunningApp);
        onTaskRemoved(intent);
        return Service.START_STICKY;
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        return dateFormat.format(new Date()).toString();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                1000,
                restartPendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    public String getRunningApp() {

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) this
                .getSystemService(Context.USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000 * 60;

        // result
        String topActivity = null;

        // We get usage stats for the last minute
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, beginTime, endTime);

        // Sort the stats by the last time used
        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                topActivity = mySortedMap.get(mySortedMap.lastKey())
                        .getPackageName();
            }
        }
        if (topActivity != null)
            return topActivity;
        else
            return "default";

    }

}