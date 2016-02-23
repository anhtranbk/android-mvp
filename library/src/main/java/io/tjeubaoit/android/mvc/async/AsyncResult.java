package io.tjeubaoit.android.mvc.async;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public interface AsyncResult<T> {

    boolean succeeded();

    boolean failed();

    T result();

    Throwable cause();
}
