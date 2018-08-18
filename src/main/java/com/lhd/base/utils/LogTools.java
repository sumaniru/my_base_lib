package com.lhd.base.utils;

import android.util.Log;

/**
 * Created by arutoria on 2017/7/11.
 */

public class LogTools {

    public final int DEBUG = 0;

    public final int INFO = 1;

    public final int ERROR = 2;

    public final int ME = 3;//特殊级别  用完即删

    public final int NOTHING = 4;

    public int level = DEBUG;

    private static LogTools logTools;

    public static LogTools getInstance() {
        if (logTools == null) {
            synchronized (LogTools.class) {
                if (logTools == null) {
                    logTools = new LogTools();
                }
            }
        }
        return logTools;
    }

    public void me(String msg) {
        if (ME >= level) {
            Log.d("bbbbb", msg);
        }
    }

    public void debug(String msg) {
        if (DEBUG >= level) {
            Log.d("YuePinDebug", msg);
        }
    }

    public void info(String msg) {
        if (INFO >= level) {
            Log.i("YuePinInfo", msg);
        }
    }

    public void error(String msg) {
        if (ERROR >= level) {
            Log.e("YuePinError", msg);
        }
    }


}
