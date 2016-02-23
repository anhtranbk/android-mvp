package io.tjeubaoit.android.mvc;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.util.Caster;
import io.tjeubaoit.android.mvc.util.Logger;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class FragmentController extends Controller {

    public static final String ACTION_FRAGMENT_CREATE = "action.FRAGMENT_CREATE";
    public static final String ACTION_FRAGMENT_VIEW_CREATED = "action.FRAGMENT_VIEW_CREATED";
    public static final String ACTION_FRAGMENT_ATTACH = "action.FRAGMENT_ATTACH";
    public static final String ACTION_FRAGMENT_DETACH = "action.FRAGMENT_DETACH";
    public static final String ACTION_ACTIVITY_CREATED = "action.ACTIVITY_CREATED";
    public static final String ACTION_FRAGMENT_START = "action.FRAGMENT_START";
    public static final String ACTION_FRAGMENT_RESUME = "action.FRAGMENT_RESUME";
    public static final String ACTION_FRAGMENT_PAUSE = "action.FRAGMENT_PAUSE";
    public static final String ACTION_FRAGMENT_STOP = "action.FRAGMENT_STOP";
    public static final String ACTION_FRAGMENT_DESTROY = "action.FRAGMENT_DESTROY";
    public static final String ACTION_FRAGMENT_DESTROY_VIEW = "action.FRAGMENT_DESTROY_VIEW";
    public static final String ACTION_ACTIVITY_RESULT = "action.ACTIVITY_RESULT";
    public static final String ACTION_SAVE_INSTANCE_STATE = "action.SAVE_INSTANCE_STATE";
    public static final String ACTION_VIEW_STATE_RESTORED = "action.VIEW_STATE_RESTORED";
    public static final String ACTION_CONFIGURATION_CHANGED = "action.CONFIGURATION_CHANGED";
    public static final String ACTION_LOW_MEMORY = "action.LOW_MEMORY";

    private static final Logger LOGGER = Logger.getLogger(FragmentController.class);

    private Fragment fragment;

    public FragmentController(Context context, View view) {
        super(context, view);
        try {
            fragment = Caster.cast(view);
        } catch (ClassCastException e) {
            throw new ClassCastException("FragmentController can only be attached to a Fragment class");
        }
    }

    @Override
    public void handle(Message msg) {
        switch (msg.action()) {
            case ACTION_FRAGMENT_CREATE:
                onFragmentCreate((Bundle) msg.body());
                break;
            case ACTION_FRAGMENT_VIEW_CREATED:
                onFragmentViewCreated((Bundle) msg.body());
                break;
            case ACTION_ACTIVITY_CREATED:
                onActivityCreated((Bundle) msg.body());
                break;
            case ACTION_FRAGMENT_ATTACH:
                onFragmentAttach();
                break;
            case ACTION_FRAGMENT_DETACH:
                onFragmentDetach();
                break;
            case ACTION_FRAGMENT_START:
                onFragmentStart();
                break;
            case ACTION_FRAGMENT_STOP:
                onFragmentStop();
                break;
            case ACTION_FRAGMENT_RESUME:
                onFragmentResume();
                break;
            case ACTION_FRAGMENT_PAUSE:
                onFragmentPause();
                break;
            case ACTION_FRAGMENT_DESTROY:
                onFragmentDestroy();
                break;
            case ACTION_FRAGMENT_DESTROY_VIEW:
                onFragmentDestroyView();
                break;
            case ACTION_ACTIVITY_RESULT:
                Object[] body = (Object[]) msg.body();
                onActivityResult((int) body[0], (int) body[1], (Intent) body[2]);
                break;
            case ACTION_SAVE_INSTANCE_STATE:
                onSaveInstanceState((Bundle) msg.body());
                break;
            case ACTION_VIEW_STATE_RESTORED:
                onViewStateRestored((Bundle) msg.body());
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

    protected Fragment getFragment() {
        return fragment;
    }

    protected void onFragmentCreate(Bundle savedInstanceState) {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentCreate");
    }

    protected void onFragmentViewCreated(Bundle savedInstanceState) {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentViewCreated");
    }

    protected void onFragmentAttach() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentAttach");
    }

    protected void onFragmentDetach() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentDetach");
    }

    protected void onActivityCreated(Bundle savedInstanceState) {
        LOGGER.debug(fragment.getClass().getName() + " onActivityCreated");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LOGGER.debug(fragment.getClass().getName() + " onActivityResult");
    }

    protected void onFragmentResume() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentResume");
    }

    protected void onFragmentPause() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentPause");
    }

    protected void onFragmentStart() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentStart");
    }

    protected void onFragmentStop() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentStop");
    }

    protected void onFragmentDestroy() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentDestroy");
    }

    protected void onFragmentDestroyView() {
        LOGGER.debug(fragment.getClass().getName() + " onFragmentDestroyView");
    }

    public void onSaveInstanceState(Bundle outState) {
        LOGGER.debug(fragment.getClass().getName() + " onSaveInstanceState");
    }

    protected void onViewStateRestored(Bundle savedInstanceState) {
        LOGGER.debug(fragment.getClass().getName() + " onViewStateRestored");
    }

    protected void onConfigurationChanged(Configuration config) {
        LOGGER.debug(fragment.getClass().getName() + " onConfigurationChanged");
    }

    protected void onLowMemory() {
        LOGGER.debug(fragment.getClass().getName() + " onLowMemory");
    }

}
