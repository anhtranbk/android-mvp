package io.tjeubaoit.android.mvc.util;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class ReflectionUtils {

    public static Set<Class<?>> getTypesAnnotatedWith(Context context, String[] prefixes,
                                                      Class<? extends Annotation> annotation)
            throws IOException, ClassNotFoundException {

        Set<Class<?>> classes = getClasspathClasses(context, prefixes);
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                set.add(clazz);
            }
        }

        return set;
    }

    public static Set<Class<?>> getClasspathClasses(Context context, String[] prefixes)
            throws IOException, ClassNotFoundException {

        Set<Class<?>> classes = new HashSet<>();
        DexFile dex = new DexFile(context.getApplicationInfo().sourceDir);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<String> entries = dex.entries();

        while (entries.hasMoreElements()) {
            String entry = entries.nextElement();
            for (String prefix : prefixes) {
                if (entry.startsWith(prefix)) {
                    classes.add(classLoader.loadClass(entry));
                    break;
                }
            }
        }

        return classes;
    }
}
