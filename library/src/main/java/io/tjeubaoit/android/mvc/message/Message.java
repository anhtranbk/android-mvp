package io.tjeubaoit.android.mvc.message;

import io.tjeubaoit.android.mvc.async.AsyncResult;
import io.tjeubaoit.android.mvc.async.Handler;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface Message {

    final class Factory {

        public static <R> Message create(String action, Object body, android.os.Handler osHandler,
                                          Handler<AsyncResult<R>> replyHandler) {
            return new MessageImpl<>(action, body, osHandler, replyHandler);
        }
    }

    final class Builder {

        private String action;
        private Object body;
        private Message msg;
        private android.os.Handler osHandler;

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setBody(Object body) {
            this.body = body;
            return this;
        }

        public Builder setOsHandler(android.os.Handler osHandler) {
            this.osHandler = osHandler;
            return this;
        }

        public <R> Builder setReplyHandler(Handler<AsyncResult<R>> replyHandler) {
            if (osHandler == null) {
                osHandler = new android.os.Handler();
            }
            msg = Message.Factory.create(action, body, osHandler, replyHandler);
            return this;
        }

        public Message build() {
            if (msg == null) {
                if (osHandler == null) {
                    osHandler = new android.os.Handler();
                }
                msg = Message.Factory.create(action, body, osHandler, null);
            }
            return msg;
        }
    }

    String action();

    <T> T body();

    void reply(Object response);

    void fail(String failureMessage);

    void fail(Throwable throwable);
}
