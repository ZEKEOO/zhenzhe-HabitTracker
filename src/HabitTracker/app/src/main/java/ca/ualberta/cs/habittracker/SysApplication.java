package ca.ualberta.cs.habittracker;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by ZEKE_XU on 30/09/2016.
 */

public class SysApplication extends Application {

    private LinkedList<Activity> mActivities = new LinkedList<Activity>();
    private static SysApplication instance;

    private SysApplication() {}

    public synchronized static SysApplication getInstance() {
        if (instance == null) {
            instance = new SysApplication();
        }
        return instance;
    }

    /* Put every activity into management */
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /* When exiting, finish every activity */
    public void exitApplication() {
        for (Activity activity : mActivities) {
            if (activity != null)
                activity.finish();
        }
        Log.d("SysApplication",  "exitApplication");
        System.exit(0);
    }
}
