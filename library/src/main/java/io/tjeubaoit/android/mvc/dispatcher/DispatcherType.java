package io.tjeubaoit.android.mvc.dispatcher;

import android.content.Context;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public enum DispatcherType {

    DEFAULT {
        public AbstractDispatcher create(Context context) {
            return new HandlerDispatcher(context);
        }
    },
    HANDLER {
        public AbstractDispatcher create(Context context) {
            return new HandlerDispatcher(context);
        }
    };

    public AbstractDispatcher create(Context context) {
        throw new UnsupportedOperationException();
    }
}
