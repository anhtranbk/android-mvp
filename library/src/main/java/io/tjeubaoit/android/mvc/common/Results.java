package io.tjeubaoit.android.mvc.common;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class Results {

    public static <T> AsyncResult<T> ok(T result) {
        return Future.Factory.succeededFuture(result);
    }

    public static <T> AsyncResult<T> ok() {
        return Future.Factory.succeededFuture();
    }

    public static <T> AsyncResult<T> fail(String failureMessage) {
        return Future.Factory.failedFuture(failureMessage);
    }

    public static <T> AsyncResult<T> fail(Throwable throwable) {
        return Future.Factory.failedFuture(throwable);
    }

    public static <T> AsyncResult<T> fail() {
        return Future.Factory.failedFuture(new NoStackTraceThrowable("Unknown error"));
    }
}
