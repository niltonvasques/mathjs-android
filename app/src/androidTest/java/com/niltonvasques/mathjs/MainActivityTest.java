package com.niltonvasques.mathjs;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public Timeout mGlobalTimeout = new Timeout(20, TimeUnit.SECONDS);
}
