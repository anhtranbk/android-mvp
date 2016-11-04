package io.tjeubaoit.android.mvc.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
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
        DexFile baseDex = new DexFile(context.getApplicationInfo().sourceDir);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        List<DexFile> dexFiles = new ArrayList<>();
        dexFiles.add(baseDex);

        // support instant run
        File[] files = new File(getInstantRunOutputDir(context)).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().matches(INSTANT_RUN_REGEX)) {
                    dexFiles.add(new DexFile(file.getAbsoluteFile()));
                }
            }
        }

        for (DexFile dex : dexFiles) {
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
        }

        return classes;
    }

    static final String INSTANT_RUN_DIR = "/files/instant-run/dex";
    static final String INSTANT_RUN_REGEX = "slice-slice_[0-9]+-classes.dex";

    static String getInstantRunOutputDir(Context context) {
        return context.getApplicationInfo().dataDir + INSTANT_RUN_DIR;
    }
}
