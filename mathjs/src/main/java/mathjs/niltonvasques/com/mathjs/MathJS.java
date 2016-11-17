package mathjs.niltonvasques.com.mathjs;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.ConcurrentLinkedQueue;

import mathjs.niltonvasques.com.mathjs.utils.ThreadUtils;

/**
 * A class that wrapper the mathjs.org javascript library to help compute math expressions.
 *
 * Uses {@link #eval} method to evaluate expressions.
 * And when not need more the library, call {@link #destroy()} to deallocate resources from memory.
 *
 * Created by niltonvasques on 11/17/16.
 */
public class MathJS extends WebViewClient {
    private WebView mWebView;
    private boolean mLoaded = false;
    private ConcurrentLinkedQueue<Expression> expressions = new ConcurrentLinkedQueue<>();
    public MathJS(final Context context) {
        ThreadUtils.runOnMain(new Runnable() {
            @Override
            public void run() {
                mWebView = new WebView(context);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAllowFileAccess(true);
                mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
                mWebView.loadDataWithBaseURL("file:///android_asset/", MATH_JS_HTML, "text/html",
                        "utf-8", "");
                mWebView.setWebViewClient(MathJS.this);
            }
        });
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        System.out.println("MathJS.onPageFinished");
        mLoaded = true;
        while(!expressions.isEmpty()) {
            evaluateExpression(expressions.poll());
        }
    }

    /**
     * Evaluate n math expression using mathjs library asynchronously.
     *
     * @param expr expression to be evaluated.
     * @param result the callback that will be called after the expression be evaluated.
     */
    public void eval(String expr, final MathJSResult result) {
        Expression expression = new Expression(expr, result);
        if (!mLoaded) {
            expressions.offer(expression);
        } else {
            evaluateExpression(expression);
        }
    }

    /**
     * Deallocate WebView instance used by the library.
     */
    public void destroy() {
        ThreadUtils.runOnMain(new Runnable() {
            @Override
            public void run() {
                if(mWebView != null) {
                    mWebView.clearHistory();
                    mWebView.clearCache(true);
                    mWebView.loadUrl("about:blank");
                    mWebView.freeMemory();
                    mWebView.pauseTimers();
                    mWebView = null;
                }
            }
        });
    }

    private void evaluateExpression(final Expression expr) {
        if (mWebView == null)
            throw new IllegalStateException("Cannot evaluate after been destroyed!");
        mWebView.evaluateJavascript("(function() { return math.eval('"+expr.expr+"'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        System.out.println("MathJS.onReceiveValue "+s);
                        expr.callback.onEvaluated(s);
                    }
                });
    }

    private class Expression {
        public String expr;
        public MathJSResult callback;
        Expression(String expression, MathJSResult result) {
            expr = expression;
            callback = result;
        }
    }

    /**
     * Callback interface used to communicate evaluation results.
     */
    public interface MathJSResult {
        void onEvaluated(String value);
    }

    private static String MATH_JS_HTML = "<!DOCTYPE HTML>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <script src=\"file:///android_asset/math.min.js\" type=\"text/javascript\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "</body>\n" +
            "</html>";
}

