package io.tjeubaoit.android.mvc.dispatcher;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import io.tjeubaoit.android.mvc.Controller;
import io.tjeubaoit.android.mvc.View;
import io.tjeubaoit.android.mvc.message.Message;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class HandlerDispatcher extends AbstractDispatcher {

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public HandlerDispatcher(Context context) {
        super(context);
    }

    protected void startHandlerThreadIfNeed() {
        if (mHandlerThread == null || !mHandlerThread.isAlive()) {
            mHandlerThread = new HandlerThread("message-dispatcher", Process.THREAD_PRIORITY_BACKGROUND);
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());
        }
    }

    protected void stopHandlerThread() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mHandlerThread.quitSafely();
        } else {
            mHandlerThread.quit();
        }
    }

    protected Handler getHandler() {
        return mHandler;
    }

    @Override
    public void close() {
        stopHandlerThread();
        mHandler = null;
        mHandlerThread = null;
    }

    @Override
    protected void doDispatchToController(final Controller controller, final Message msg) {
        startHandlerThreadIfNeed();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                controller.handle(msg);
            }
        });
    }

    @Override
    protected void doDispatchToView(final View view, final Message msg) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                view.handle(msg);
            }
        });
    }
}
