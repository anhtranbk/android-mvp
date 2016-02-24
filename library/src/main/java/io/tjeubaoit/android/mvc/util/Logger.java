package io.tjeubaoit.android.mvc.util;

import android.util.Log;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class Logger {

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

    private final String mTag;

    private Logger(Class<?> clazz) {
        mTag = clazz.getSimpleName();
    }

    public void debug(Object msg) {
        if (msg != null) {
            Log.d(mTag, msg.toString());
        }
    }

    public void debug(Object msg, Throwable tr) {
        if (msg != null) {
            Log.d(mTag, msg.toString(), tr);
        } else {
            Log.d(mTag, "Exception", tr);
        }
    }

    public void info(Object msg) {
        if (msg != null) {
            Log.i(mTag, msg.toString());
        }
    }

    public void info(Object msg, Throwable tr) {
        if (msg != null) {
            Log.i(mTag, msg.toString(), tr);
        } else {
            Log.i(mTag, "Exception", tr);
        }
    }

    public void warn(Object msg) {
        if (msg != null) {
            Log.w(mTag, msg.toString());
        }
    }

    public void warn(Object msg, Throwable tr) {
        if (msg != null) {
            Log.w(mTag, msg.toString(), tr);
        } else {
            Log.w(mTag, "Exception", tr);
        }
    }

    public void error(Object msg) {
        if (msg != null) {
            Log.e(mTag, msg.toString());
        }
    }

    public void error(Object msg, Throwable tr) {
        if (msg != null) {
            Log.e(mTag, msg.toString(), tr);
        } else {
            Log.e(mTag, "Exception", tr);
        }
    }
}
