package com.example.mvc;

import android.app.Application;

import io.tjeubaoit.android.mvc.Mvc;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init mvc context
        Mvc.init(getApplicationContext());
//        Mvc.init(getApplicationContext(), DispatcherType.HANDLER.create(getApplicationContext()));
    }
}
