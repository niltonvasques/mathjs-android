package mathjs.niltonvasques.com.mathjs;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MathJSTest {
    private static final double DELTA = 0.000000001d;
    private class AssertPair {
        public String expected;
        public String actual;
        public AssertPair(String expected, String actual) {
            this.expected = expected;
            this.actual = actual;
        }
    }

    @Rule
    public Timeout mGlobalTimeout = new Timeout(20, TimeUnit.SECONDS);
    
    @Test
    public void mathExpressionsWorks() throws Exception {
        MathJS math = new MathJS();

        assertEval(math, "(2^3)*5+25.55-1", Math.pow(2, 3)*5+25.55-1);
        assertEval(math, "1.2 * (2 + 4.5)", 7.8);
        assertEval(math, "sin(45 deg) ^ 2", 0.5);
        assertEval(math, "det([-1, 2; 3, 1])", -7);

        math.destroy();
    }

    @Test
    public void complexNumbersWorks() throws Exception {
        MathJS math = new MathJS();

        assertEquals("3 + 2i", math.eval("9 / 3 + 2i"));

        math.destroy();
    }

    @Test
    public void conversionExpressions() throws Exception {
        MathJS math = new MathJS();

        assertEquals("2.0000000000000004 inch", math.eval("5.08 cm to inch"));

        math.destroy();
    }

    @Test
    public void ternaryOperatorWorks() throws Exception {
        MathJS math = new MathJS();

        assertEval(math, "( 2 * 5 > 10 ? 20 : 0 )", ( 2 * 5 > 10 ? 20 : 0 ));
        assertEval(math, "( 2 * 6 > 10 ? 20 : 0 )", ( 2 * 6 > 10 ? 20 : 0 ));

        math.destroy();
    }

    @Test
    public void asyncEvalWorks() throws Exception {
        final AssertPair pair = new AssertPair("8.0", "");
        MathJS math = new MathJS();
        final CountDownLatch signal = new CountDownLatch(1);
        math.asyncEval("2^3", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                pair.actual = value;
                signal.countDown();
            }
        });
        signal.await();
        assertEquals(pair.expected, pair.actual);

        math.destroy();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionAfterBeenDestroyed() {
        MathJS math = new MathJS();

        math.destroy();

        math.eval("2 * 2");
    }

    private void assertEval(MathJS math, String expr, double expected) {
        assertEquals(expected, Double.parseDouble(math.eval(expr)), DELTA);
    }
}
