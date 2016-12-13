package io.tjeubaoit.android.mvc.message;

import android.os.Looper;

import io.tjeubaoit.android.mvc.common.AsyncResult;
import io.tjeubaoit.android.mvc.common.Handler;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface Message {

    final class Factory {

        public static <R> Message create(String action, Object body, Looper looper,
                                          Handler<AsyncResult<R>> replyHandler) {
            return new MessageImpl<>(action, body, looper, replyHandler);
        }
    }

    final class Builder {

        private String action;
        private Object body;
        private Message msg;
        private Looper looper;

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setBody(Object body) {
            this.body = body;
            return this;
        }

        public Builder setLooper(Looper looper) {
            this.looper = looper;
            return this;
        }

        public <R> Builder setReplyHandler(Handler<AsyncResult<R>> replyHandler) {
            if (looper == null) {
                looper = Looper.myLooper();
            }
            msg = Message.Factory.create(action, body, looper, replyHandler);
            return this;
        }

        public Message build() {
            if (msg == null) {
                if (looper == null) {
                    looper = Looper.myLooper();
                }
                msg = Message.Factory.create(action, body, looper, null);
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
