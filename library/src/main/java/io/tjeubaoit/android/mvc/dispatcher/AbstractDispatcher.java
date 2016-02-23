package io.tjeubaoit.android.mvc.dispatcher;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.tjeubaoit.android.mvc.Controller;
import io.tjeubaoit.android.mvc.Mvc;
import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.async.AsyncResult;
import io.tjeubaoit.android.mvc.async.Handler;
import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.util.Logger;
import io.tjeubaoit.android.mvc.util.SimpleClassLoader;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public abstract class AbstractDispatcher implements Dispatcher {

    private static final Logger LOGGER = Logger.getLogger(AbstractDispatcher.class);

    private final Map<View, Controller> controllerMap = new HashMap<>();
    private final Context context;

    public AbstractDispatcher(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void attachView(View view) {
        Controller controller = controllerMap.get(view);
        if (controller == null) {
            Class<?> clazz = Mvc.getViewMapping().get(view.getClass().getName());
            try {
                controller = (Controller) SimpleClassLoader.loadClass(clazz,
                        new Class[]{Context.class, View.class},
                        new Object[]{context, view});
                controllerMap.put(view, controller);
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        } else {
            throw new IllegalStateException("View already attached to dispatcher");
        }
    }

    @Override
    public void detachView(View view) {
        controllerMap.remove(view);
    }

    @Override
    public void dispatchToController(View sender, String action) {
        dispatchToController(sender, action, null, null);
    }

    @Override
    public void dispatchToController(View sender, String action, Object body) {
        dispatchToController(sender, action, body, null);
    }

    @Override
    public <R> void dispatchToController(View sender, String action, Object body,
                                         Handler<AsyncResult<R>> replyHandler) {
        Controller controller = controllerMap.get(sender);
        if (controller != null) {
            Message msg = new Message.Builder()
                    .setAction(action)
                    .setBody(body)
                    .setOsHandler(new android.os.Handler())
                    .setReplyHandler(replyHandler)
                    .build();
            doDispatchToController(controller, msg);
        } else {
            throw new RuntimeException("No controller for handle action from: " + sender);
        }
    }

    @Override
    public void dispatchToView(View target, String action) {
        dispatchToView(target, action, null);
    }

    @Override
    public void dispatchToView(View target, String action, Object body) {
        Message msg = new Message.Builder()
                .setAction(action)
                .setBody(body)
                .build();
        doDispatchToView(target, msg);
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Shutdown dispatcher");
    }

    protected abstract void doDispatchToController(Controller controller, Message msg);

    protected abstract void doDispatchToView(View view, Message msg);

}
