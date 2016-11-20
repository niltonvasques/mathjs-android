package mathjs.niltonvasques.com.mathjs;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;

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

    private MathJS mSut;

    @Before
    public void setup() {
         mSut = new MathJS();
    }

    @After
    public void cleanup() {
        mSut.destroy();
    }

    @Rule
    public Timeout mGlobalTimeout = new Timeout(20, TimeUnit.SECONDS);
    
    @Test
    public void mathExpressionsWorks() throws Exception {
        assertNumericEval(mSut, "(2^3)*5+25.55-1", Math.pow(2, 3)*5+25.55-1);
        assertNumericEval(mSut, "1.2 * (2 + 4.5)", 7.8);
        assertNumericEval(mSut, "sin(45 deg) ^ 2", 0.5);
        assertNumericEval(mSut, "det([-1, 2; 3, 1])", -7);

    }

    @Test
    public void complexNumbersWorks() throws Exception {
        assertEquals("3 + 2i", mSut.eval("9 / 3 + 2i"));
    }

    @Test
    public void conversionExpressions() throws Exception {
        assertEquals("2.0000000000000004 inch", mSut.eval("5.08 cm to inch"));
    }

    @Test
    public void ternaryOperatorWorks() throws Exception {
        assertNumericEval(mSut, "( 2 * 5 > 10 ? 20 : 0 )", ( 2 * 5 > 10 ? 20 : 0 ));
        assertNumericEval(mSut, "( 2 * 6 > 10 ? 20 : 0 )", ( 2 * 6 > 10 ? 20 : 0 ));
    }

    @Test
    public void asyncEvalWorks() throws Exception {
        assertNumericAsyncEval(mSut, "2^3", 8);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionAfterBeenDestroyed() {
        MathJS math = new MathJS();

        math.destroy();

        math.eval("2 * 2");
    }

    private void assertNumericEval(MathJS math, String expr, double expected) {
        assertEquals(expected, Double.parseDouble(math.eval(expr)), DELTA);
    }

    private void assertNumericAsyncEval(MathJS math, String expr, double expected)
            throws InterruptedException {
        final AssertPair pair = new AssertPair("", "");
        final CountDownLatch signal = new CountDownLatch(1);
        math.asyncEval(expr, new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                pair.actual = value;
                signal.countDown();
            }
        });
        signal.await();
        assertEquals(expected, Double.parseDouble(pair.actual), DELTA);
    }
}
