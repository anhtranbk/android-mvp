package io.tjeubaoit.android.mvc.dispatcher;

import java.io.Closeable;

import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.common.AsyncResult;
import io.tjeubaoit.android.mvc.common.Handler;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface Dispatcher extends Closeable {

    void attachView(View view);

    void detachView(View view);

    <R> void dispatchToController(View sender, String action, Object body,
                                  Handler<AsyncResult<R>> replyHandler);

    void dispatchToView(View target, String action, Object body);
}
