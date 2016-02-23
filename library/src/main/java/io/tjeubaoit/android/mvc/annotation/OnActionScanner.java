package io.tjeubaoit.android.mvc.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.tjeubaoit.android.mvc.message.Message;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class OnActionScanner {

    public static Map<String, Method> getMethodsAnnotatedAsAction(Object obj) {
        Message msg = new Message.Builder().build();
        Map<String, Method> methodMap = new HashMap<>();

        // scan methods for mapping
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(OnAction.class)) continue;

            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1 || !paramTypes[0].isInstance(msg)) continue;

            for (String action : method.getAnnotation(OnAction.class).value()) {
                methodMap.put(action, method);
            }
        }

        return methodMap;
    }
}
