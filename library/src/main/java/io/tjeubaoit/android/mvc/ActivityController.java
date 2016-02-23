package io.tjeubaoit.android.mvc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Pair;

import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.util.Caster;
import io.tjeubaoit.android.mvc.util.Logger;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class ActivityController extends Controller {

    public static final String ACTION_ACTIVITY_CREATE = "action.ACTIVITY_CREATE";
    public static final String ACTION_ACTIVITY_START = "action.ACTIVITY_START";
    public static final String ACTION_ACTIVITY_RESTART = "action.ACTIVITY_RESTART";
    public static final String ACTION_ACTIVITY_RESUME = "action.ACTIVITY_RESUME";
    public static final String ACTION_ACTIVITY_POST_RESUME = "action.ACTIVITY_POST_RESUME";
    public static final String ACTION_ACTIVITY_PAUSE = "action.ACTIVITY_PAUSE";
    public static final String ACTION_ACTIVITY_STOP = "action.ACTIVITY_STOP";
    public static final String ACTION_ACTIVITY_DESTROY = "action.ACTIVITY_DESTROY";
    public static final String ACTION_ACTIVITY_RESULT = "action.ACTIVITY_RESULT";
    public static final String ACTION_BACK_PRESSED = "action.BACK_PRESSED";
    public static final String ACTION_SAVE_INSTANCE_STATE = "action.SAVE_INSTANCE_STATE";
    public static final String ACTION_RESTORE_INSTANCE_STATE = "action.RESTORE_INSTANCE_STATE";
    public static final String ACTION_CONFIGURATION_CHANGED = "action.CONFIGURATION_CHANGED";
    public static final String ACTION_LOW_MEMORY = "action.LOW_MEMORY";

    private static final Logger LOGGER = Logger.getLogger(ActivityController.class);

    private final Activity activity;

    public ActivityController(Context context, View view) {
        super(context, view);
        try {
            activity = Caster.cast(view);
        } catch (ClassCastException e) {
            throw new ClassCastException("ActivityController can only be attached to an Activity class");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handle(Message msg) {
        switch (msg.action()) {
            case ACTION_ACTIVITY_CREATE:
                onActivityCreate((Bundle) msg.body());
                break;
            case ACTION_ACTIVITY_START:
                onActivityStart();
                break;
            case ACTION_ACTIVITY_RESTART:
                onActivityRestart();
                break;
            case ACTION_ACTIVITY_RESUME:
                onActivityResume();
                break;
            case ACTION_ACTIVITY_POST_RESUME:
                onActivityPostResume();
                break;
            case ACTION_ACTIVITY_PAUSE:
                onActivityPause();
                break;
            case ACTION_ACTIVITY_STOP:
                onActivityStop();
                break;
            case ACTION_ACTIVITY_DESTROY:
                onActivityDestroy();
                break;
            case ACTION_ACTIVITY_RESULT:
                Object[] body = (Object[]) msg.body();
                onActivityResult((int) body[0], (int) body[1], (Intent) body[2]);
                break;
            case ACTION_BACK_PRESSED:
                onBackPressed();
                break;
            case ACTION_SAVE_INSTANCE_STATE:
                Pair<Bundle, PersistableBundle> pair = (Pair<Bundle, PersistableBundle>) msg.body();
                onSaveInstanceState(pair.first, pair.second);
                break;
            case ACTION_RESTORE_INSTANCE_STATE:
                onRestoreInstanceState((Bundle) msg.body());
                break;
            case ACTION_CONFIGURATION_CHANGED:
                onConfigurationChanged((Configuration) msg.body());
                break;
            case ACTION_LOW_MEMORY:
                onLowMemory();
                break;
            default:
                super.handle(msg);
        }
    }

    protected Activity getActivity() {
        return activity;
    }

    protected void onActivityCreate(Bundle savedInstanceState) {
        LOGGER.debug(activity.getClass().getName() + " onActivityCreate");
    }

    protected void onActivityStart() {
        LOGGER.debug(activity.getClass().getName() + " onActivityStart");
    }

    protected void onActivityRestart() {
        LOGGER.debug(activity.getClass().getName() + " onActivityRestart");
    }

    protected void onActivityResume() {
        LOGGER.debug(activity.getClass().getName() + " onActivityResume");
    }

    protected void onActivityPostResume() {
        LOGGER.debug(activity.getClass().getName() + " onActivityPostResume");
    }

    protected void onActivityPause() {
        LOGGER.debug(activity.getClass().getName() + " onActivityPause");
    }

    protected void onActivityStop() {
        LOGGER.debug(activity.getClass().getName() + " onActivityStop");
    }

    protected void onActivityDestroy() {
        LOGGER.debug(activity.getClass().getName() + " onActivityDestroy");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOGGER.debug(activity.getClass().getName() + " onActivityResult");
    }

    public void onBackPressed() {
        LOGGER.debug(activity.getClass().getName() + " onBackPressed");
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        LOGGER.debug(activity.getClass().getName() + " onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LOGGER.debug(activity.getClass().getName() + " onRestoreInstanceState");
    }

    protected void onConfigurationChanged(Configuration config) {
        LOGGER.debug(activity.getClass().getName() + " onConfigurationChanged");
    }

    protected void onLowMemory() {
        LOGGER.debug(activity.getClass().getName() + " onLowMemory");
    }

}
