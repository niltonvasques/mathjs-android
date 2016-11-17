package com.niltonvasques.mathjs;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import mathjs.niltonvasques.com.mathjs.MathJS;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public Timeout mGlobalTimeout = new Timeout(20, TimeUnit.SECONDS);
    
    @Test
    public void powExpressionWorks() throws Exception {
        MathJS math = new MathJS(InstrumentationRegistry.getTargetContext());
        final CountDownLatch signal = new CountDownLatch(1);
        math.eval("2^3", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                assertEquals("8", value);
                signal.countDown();
            }
        });
        signal.await();
        math.destroy();
    }
}
