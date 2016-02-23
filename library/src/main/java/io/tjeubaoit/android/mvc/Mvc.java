package io.tjeubaoit.android.mvc;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.tjeubaoit.android.mvc.annotation.ViewMapping;
import io.tjeubaoit.android.mvc.dispatcher.Dispatcher;
import io.tjeubaoit.android.mvc.dispatcher.DispatcherType;
import io.tjeubaoit.android.mvc.util.Logger;
import io.tjeubaoit.android.mvc.util.ReflectionUtils;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class Mvc {

    private static final Logger LOGGER = Logger.getLogger(Mvc.class);
    static volatile Mvc singleton;

    private Dispatcher dispatcher;
    private final Map<String, Class<?>> viewMapping = new HashMap<>();

    public static void init(Context context) {
        init(context, DispatcherType.DEFAULT.create(context), context.getPackageName());
    }

    public static void init(Context context, Dispatcher dispatcher) {
        init(context, dispatcher, context.getPackageName());
    }

    public static void init(Context context, Dispatcher dispatcher, String... prefixes) {
        synchronized (Mvc.class) {
            if (singleton == null) {
                singleton = new Mvc(context, dispatcher, prefixes);
            }
        }
    }

    public static Dispatcher getDispatcher() {
        return getInstance().dispatcher;
    }

    public static Map<String, Class<?>> getViewMapping() {
        return getInstance().viewMapping;
    }

    static Mvc getInstance() {
        if (singleton == null)
            throw new IllegalStateException("Dispatcher must be initialized by calling " +
                    "Mvc.init(Context, IDispatcher, String...) prior to calling Mvc.getDispatcher()");
        return singleton;
    }

    Mvc(Context context, Dispatcher dispatcher, String[] prefixes) {
        this.dispatcher = dispatcher;
        try {
            this.init(context, prefixes);
            LOGGER.info("Init Mvc application successfully");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void init(Context context, String[] prefixes) throws IOException, ClassNotFoundException {
        final Set<Class<?>> annotatedPresentClasses = ReflectionUtils.getTypesAnnotatedWith(context,
                prefixes, ViewMapping.class);
        for (Class<?> clazz : annotatedPresentClasses) {
            for (Class<?> aClass : clazz.getAnnotation(ViewMapping.class).value()) {
                LOGGER.debug(String.format("Map %s with controller %s", aClass.getName(), clazz.getName()));
                viewMapping.put(aClass.getName(), clazz);
            }
        }
    }
}
