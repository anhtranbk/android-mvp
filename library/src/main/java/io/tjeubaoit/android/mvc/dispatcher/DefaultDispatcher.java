package io.tjeubaoit.android.mvc.dispatcher;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import io.tjeubaoit.android.mvc.Controller;
import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.message.Message;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class DefaultDispatcher extends AbstractDispatcher {

    public DefaultDispatcher(Context context) {
        super(context);
    }

    @Override
    protected void doDispatchToController(Controller controller, Message msg) {
        controller.handle(msg);
    }

    @Override
    protected void doDispatchToView(final View view, final Message msg) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                view.handle(msg);
            }
        });
    }
}
