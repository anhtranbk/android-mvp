package io.tjeubaoit.android.mvc;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import io.tjeubaoit.android.mvc.annotation.OnActionScanner;
import io.tjeubaoit.android.mvc.common.Handler;
import io.tjeubaoit.android.mvc.dispatcher.Dispatcher;
import io.tjeubaoit.android.mvc.message.Message;
import io.tjeubaoit.android.mvc.common.logging.Logger;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public abstract class Controller implements Handler<Message> {

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    protected final Context context;
    private final Dispatcher dispatcher;
    private final WeakReference<View> view;
    private final Map<String, Method> methodMap;

    public Controller(Context context, View view) {
        this.dispatcher = Mvc.getDispatcher();
        this.context = context;
        this.view = new WeakReference<>(view);
        this.methodMap = OnActionScanner.getMethodsAnnotatedAsAction(this);
    }

    public final Context getContext() {
        return context;
    }

    public final View getView() {
        return view.get();
    }

    protected void post(String action) {
        post(action, null);
    }

    protected void post(String action, Object body) {
        View view = this.view.get();
        if (view != null) {
            dispatcher.dispatchToView(view, action, body);
        } else {
            LOGGER.error("Target view destroyed");
        }
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
}
