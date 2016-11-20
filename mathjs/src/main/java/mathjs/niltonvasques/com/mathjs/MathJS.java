package mathjs.niltonvasques.com.mathjs;

import com.squareup.duktape.Duktape;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * A class that wrapper the mathjs.org javascript library to help compute math expressions.
 *
 * Uses {@link #eval} method to evaluate expressions synchronously.
 * Uses {@link #asyncEval} method to evaluate expressions asynchronously.
 * And when not need more the library, call {@link #destroy()} to deallocate resources from memory.
 *
 * Created by niltonvasques on 11/17/16.
 */
public class MathJS {
    private Duktape mDuktape;
    private Object mLock = new Object();
    /**
     * Callback interface used to communicate evaluation results.
     */
    public interface MathJSResult {
        void onEvaluated(String value);
    }

    public MathJS() {
        InputStream stream = MathJS.class.getResourceAsStream("/math.min.js");
        final String mathString = new Scanner(stream, Charset.defaultCharset().name())
                .useDelimiter("\\A").next();
        mDuktape = Duktape.create();
        mDuktape.evaluate(mathString);
    }

    /**
     * Evaluate n math expression using mathjs library synchronously.
     *
     * @param expr expression to be evaluated.
     * @return result the expression been evaluated.
     */
    public String eval(String expr) {
        synchronized (mLock) {
            if (mDuktape == null)
                throw new IllegalStateException("Cannot evaluate after been destroyed!");
            return evaluteExpression(expr);
        }
    }

    /**
     * Evaluate n math expression using mathjs library asynchronously.
     *
     * @param expr expression to be evaluated.
     * @param callback that will be called after the expression be evaluated.
     */
    public void asyncEval(final String expr, final MathJSResult callback) {
        if (mDuktape == null)
            throw new IllegalStateException("Cannot evaluate after been destroyed!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mLock) {
                    String answer = evaluteExpression(expr);
                    callback.onEvaluated(answer);
                }
            }
        }).start();
    }

    /**
     * Deallocate duktape instance used by the library.
     */
    public void destroy() {
        synchronized (mLock) {
            mDuktape.close();
            mDuktape = null;
        }
    }

    private String evaluteExpression(String expr) {
        String function = buildJSEvalFunction(expr);
        return mDuktape.evaluate(function).toString();
    }

    private String buildJSEvalFunction(String expr) {
        return "(function() { return math.eval('" + expr + "').toString(); })();";
    }
}
