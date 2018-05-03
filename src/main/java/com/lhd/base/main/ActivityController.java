package com.lhd.base.main;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 17/8/22.
 */

public class ActivityController {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void removeAll() {
        for (Activity activity : activityList) {
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
