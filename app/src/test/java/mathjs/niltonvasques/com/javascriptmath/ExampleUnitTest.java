package mathjs.niltonvasques.com.javascriptmath;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import mathjs.niltonvasques.com.javascriptmath.math.MathJS;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", sdk = 23)
public class ExampleUnitTest {
    @Rule
    public Timeout mGlobalTimeout = new Timeout(20, TimeUnit.SECONDS);

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("ExampleUnitTest.addition_isCorrect 1");
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        System.out.println("ExampleUnitTest.addition_isCorrect 2");
        final CountDownLatch signal = new CountDownLatch(1);
        System.out.println("ExampleUnitTest.addition_isCorrect 3");
        MathJS math = new MathJS(activity);
        System.out.println("ExampleUnitTest.addition_isCorrect 4");
        math.eval("2^3", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                System.out.println("ExampleUnitTest.onEvaluated "+value);
                assertEquals("8", value);
                signal.countDown();
            }
        });
        System.out.println("ExampleUnitTest.addition_isCorrect 5");
        signal.await();
    }
}