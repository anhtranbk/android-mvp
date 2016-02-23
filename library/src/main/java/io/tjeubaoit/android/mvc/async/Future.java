package io.tjeubaoit.android.mvc.async;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface Future<T> extends AsyncResult<T> {

    final class Factory {

        public static <T> Future<T> failedFuture(String failureMessage) {
            return new FutureImpl<>(failureMessage, false);
        }

        public static <T> Future<T> failedFuture(Throwable t) {
            return new FutureImpl<>(t);
        }

        public static <T> Future<T> future() {
            return new FutureImpl<>();
        }

        public static <T> Future<T> succeededFuture() {
            return new FutureImpl<>((Throwable) null);
        }

        public static <T> Future<T> succeededFuture(T result) {
            return new FutureImpl<>(result);
        }
    }

    void complete();

    void complete(T result);

    void fail(String failureMessage);

    void fail(Throwable throwable);

    boolean isComplete();

    void setHandler(Handler<AsyncResult<T>> handler);
}
