package io.tjeubaoit.android.mvc;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import io.tjeubaoit.android.mvc.annotation.OnActionScanner;
import io.tjeubaoit.android.mvc.async.AsyncResult;
import io.tjeubaoit.android.mvc.async.Handler;
import io.tjeubaoit.android.mvc.dispatcher.Dispatcher;
import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.util.Logger;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public abstract class MvcFragment extends Fragment implements View {

    private static final Logger LOGGER = Logger.getLogger(MvcFragment.class);

    private final Dispatcher dispatcher;
    private final Map<String, Method> methodMap;

    public MvcFragment() {
        super();
        dispatcher = Mvc.getDispatcher();
        methodMap = OnActionScanner.getMethodsAnnotatedAsAction(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dispatcher.attachView(this);
        post(FragmentController.ACTION_FRAGMENT_ATTACH);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        post(FragmentController.ACTION_FRAGMENT_DETACH);
        dispatcher.detachView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post(FragmentController.ACTION_FRAGMENT_CREATE, savedInstanceState);
    }

    @Override
    public void onViewCreated(android.view.View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        post(FragmentController.ACTION_FRAGMENT_VIEW_CREATED, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        post(FragmentController.ACTION_ACTIVITY_CREATED, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Object[] body = new Object[3];
        body[0] = requestCode;
        body[1] = resultCode;
        body[2] = data;

        post(FragmentController.ACTION_ACTIVITY_RESULT, body);
    }

    @Override
    public void onStart() {
        super.onStart();
        post(FragmentController.ACTION_FRAGMENT_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        post(FragmentController.ACTION_FRAGMENT_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        post(FragmentController.ACTION_FRAGMENT_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        post(FragmentController.ACTION_FRAGMENT_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        post(FragmentController.ACTION_FRAGMENT_DESTROY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        post(FragmentController.ACTION_FRAGMENT_DESTROY_VIEW);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        post(FragmentController.ACTION_CONFIGURATION_CHANGED, newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        post(FragmentController.ACTION_SAVE_INSTANCE_STATE, outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        post(FragmentController.ACTION_VIEW_STATE_RESTORED, savedInstanceState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        post(FragmentController.ACTION_LOW_MEMORY);
    }

    @Override
    public void handle(Message msg) {
        Method method = methodMap.get(msg.action());
        if (method != null) {
            try {
                method.invoke(this, msg);
            } catch (Exception e) {
                LOGGER.error(null, e);
                if (!(e instanceof IllegalAccessException)
                        && !(e instanceof IllegalArgumentException)
                        && !(e instanceof InvocationTargetException)) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected Dispatcher getDispatcher() {
        return dispatcher;
    }

    protected void post(String action) {
        post(action, null, null);
    }

    protected void post(String action, Object body) {
        post(action, body, null);
    }

    protected <T> void post(String action, Object body, Handler<AsyncResult<T>> replyHandler) {
        dispatcher.dispatchToController(this, action, body, replyHandler);
    }

}
