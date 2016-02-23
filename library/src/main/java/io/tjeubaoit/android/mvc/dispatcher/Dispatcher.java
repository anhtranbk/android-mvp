package io.tjeubaoit.android.mvc.dispatcher;

import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.async.AsyncResult;
import io.tjeubaoit.android.mvc.async.Handler;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface Dispatcher {

    void attachView(View view);

    void detachView(View view);

    void dispatchToController(View sender, String action);

    void dispatchToController(View sender, String action, Object body);

    <R> void dispatchToController(View sender, String action, Object body,
                                  Handler<AsyncResult<R>> replyHandler);

    void dispatchToView(View target, String action);

    void dispatchToView(View target, String action, Object body);

    void shutdown();
}
