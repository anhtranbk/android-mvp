package io.tjeubaoit.android.mvc;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import io.tjeubaoit.android.mvc.annotation.OnActionScanner;
import io.tjeubaoit.android.mvc.common.AsyncResult;
import io.tjeubaoit.android.mvc.common.Handler;
import io.tjeubaoit.android.mvc.dispatcher.Dispatcher;
import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.common.logging.Logger;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public abstract class MvcActivity extends AppCompatActivity implements View {

    private static final Logger LOGGER = Logger.getLogger(MvcActivity.class);

    private final Dispatcher dispatcher;
    private final Map<String, Method> methodMap;

    public MvcActivity() {
        super();
        dispatcher = Mvc.getDispatcher();
        methodMap = OnActionScanner.getMethodsAnnotatedAsAction(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatcher.attachView(this);
        post(ActivityController.ACTION_ACTIVITY_CREATE, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        post(ActivityController.ACTION_ACTIVITY_START);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        post(ActivityController.ACTION_ACTIVITY_RESUME);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        post(ActivityController.ACTION_ACTIVITY_POST_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        post(ActivityController.ACTION_ACTIVITY_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        post(ActivityController.ACTION_ACTIVITY_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        post(ActivityController.ACTION_ACTIVITY_DESTROY);
        dispatcher.detachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Object[] body = new Object[3];
        body[0] = requestCode;
        body[1] = resultCode;
        body[2] = data;

        post(ActivityController.ACTION_ACTIVITY_RESULT, body);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        post(ActivityController.ACTION_BACK_PRESSED);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        post(ActivityController.ACTION_SAVE_INSTANCE_STATE, new Pair<>(outState, outPersistentState));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        post(ActivityController.ACTION_RESTORE_INSTANCE_STATE, savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        post(ActivityController.ACTION_CONFIGURATION_CHANGED, newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        post(ActivityController.ACTION_LOW_MEMORY);
    }

    @Override
    public void handle(Message msg) {
        Method method = methodMap.get(msg.action());
        if (method != null) {
            try {
                method.invoke(this, msg);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.error(null, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getTargetException());
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
