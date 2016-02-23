package io.tjeubaoit.android.mvc.util;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class Caster {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) throws ClassCastException {
        return (T) object;
    }
}
