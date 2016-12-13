package io.tjeubaoit.android.mvc.dispatcher;

import android.content.Context;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;

import io.tjeubaoit.android.mvc.Controller;
import io.tjeubaoit.android.mvc.Mvc;
import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.common.AsyncResult;
import io.tjeubaoit.android.mvc.common.Handler;
import io.tjeubaoit.android.mvc.common.logging.Logger;
import io.tjeubaoit.android.mvc.common.util.SimpleClassLoader;
import io.tjeubaoit.android.mvc.message.Message;

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
            if (clazz == null) {
                LOGGER.debug(null, new RuntimeException("No controller map with view: " + view));
                return;
            }

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
    public <R> void dispatchToController(View sender, String action, Object body,
                                         Handler<AsyncResult<R>> replyHandler) {
        Controller controller = controllerMap.get(sender);
        if (controller != null) {
            Message msg = new Message.Builder()
                    .setAction(action)
                    .setBody(body)
                    .setLooper(Looper.myLooper())
                    .setReplyHandler(replyHandler)
                    .build();
            doDispatchToController(controller, msg);
        } else {
            LOGGER.debug(null, new RuntimeException("No controller for handle action from: " + sender));
        }
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
    public void close() {
        LOGGER.debug("Shutdown dispatcher");
    }

    protected abstract void doDispatchToController(Controller controller, Message msg);

    protected abstract void doDispatchToView(View view, Message msg);

}
