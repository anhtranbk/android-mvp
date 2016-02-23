package io.tjeubaoit.android.mvc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class SimpleClassLoader {

    public static Object loadClass(Class<?> clazz, Class<?>[] paramTypes, Object[] params)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {

        Constructor<?> constructor = clazz.getConstructor(paramTypes); // get default constructor
        return constructor.newInstance(params);
    }
}
