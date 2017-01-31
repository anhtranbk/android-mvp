package io.tjeubaoit.android.mvc.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TODO: Class description here.
 *
 * @author <a href="https://github.com/tjeubaoit">tjeubaoit</a>
 */
public class FutureImplTest {

    private final String textOk = "ok", textFail = "fail";
    private Future<String> future1, future2, future3, future4;

    @Before
    public void setup() throws Exception {
        future1 = new FutureImpl<>(textOk);
        future2 = new FutureImpl<>((Throwable) null);
        future3 = new FutureImpl<>(new NoStackTraceThrowable(textFail));
        future4 = new FutureImpl<>(textFail, true);
    }

    @Test
    public void result() throws Exception {
        assertEquals(textOk, future1.result());
        assertEquals(null, future2.result());
        assertEquals(null, future3.result());
    }

    @Test
    public void cause() throws Exception {
        Throwable throwable = new NoStackTraceThrowable(textFail);

        assertEquals(null, future1.cause());
        assertEquals(throwable, future3.cause());
        assertEquals(throwable, future4.cause());
    }

    @Test
    public void succeeded() throws Exception {
        assertTrue(future1.succeeded());
        assertTrue(future2.succeeded());
        assertFalse(future3.succeeded());
        assertFalse(future4.succeeded());
    }

    @Test
    public void failed() throws Exception {
        assertFalse(future1.failed());
        assertFalse(future2.failed());
        assertTrue(future3.failed());
        assertTrue(future4.failed());
    }

    @Test
    public void isComplete() throws Exception {
        Future<String> future = new FutureImpl<>();

        assertFalse(future.isComplete());
        assertTrue(future1.isComplete());
        assertTrue(future2.isComplete());
        assertTrue(future3.isComplete());
        assertTrue(future4.isComplete());
    }

    @Test
    public void setHandler() throws Exception {
        final AtomicBoolean handlerCalled = new AtomicBoolean(false);

        Future<String> future = new FutureImpl<>();
        future.setHandler(new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                handlerCalled.set(true);
            }
        });
        future.complete();

        assertTrue(handlerCalled.get());
    }

    @Test
    public void complete() throws Exception {
        Future<String> future = new FutureImpl<>();
        future.complete();

        assertTrue(future.isComplete());
        assertTrue(future.succeeded());
    }

    @Test
    public void complete1() throws Exception {
        Future<String> future = new FutureImpl<>();
        future.complete(textOk);

        assertTrue(future.isComplete());
        assertEquals(textOk, future.result());
    }

    @Test
    public void fail() throws Exception {
        Future<String> future = new FutureImpl<>();
        future.fail(textFail);

        assertTrue(future.isComplete());
        assertEquals(new NoStackTraceThrowable(textFail), future.cause());
    }

    @Test
    public void fail1() throws Exception {
        Future<String> future = new FutureImpl<>();
        future.fail(new NoStackTraceThrowable(textFail));

        assertTrue(future.isComplete());
        assertEquals(new NoStackTraceThrowable(textFail), future.cause());
    }

    @Test
    public void testTouchAfterCompleted() throws Exception {
        try {
            future1.complete();
        } catch (IllegalStateException e) {
            assertTrue(true);
            return;
        }
        Assert.fail("Complete a completed future");
    }

}