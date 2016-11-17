package mathjs.niltonvasques.com.mathjs;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.ConcurrentLinkedQueue;

import mathjs.niltonvasques.com.mathjs.utils.ThreadUtils;

/**
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

    private void evaluateExpression(final Expression expr) {
        mWebView.evaluateJavascript("(function() { return math.eval('"+expr.expr+"'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        System.out.println("MathJS.onReceiveValue "+s);
                        expr.callback.onEvaluated(s);
                    }
                });
    }

    public void eval(String expr, final MathJSResult result) {
        Expression expression = new Expression(expr, result);
        if (!mLoaded) {
            expressions.offer(expression);
        } else {
            evaluateExpression(expression);
        }
    }

    private class Expression {
        public String expr;
        public MathJSResult callback;
        Expression(String expression, MathJSResult result) {
            expr = expression;
            callback = result;
        }
    }

    public interface MathJSResult {
        void onEvaluated(String value);
    }

    public static String MATH_JS_HTML = "<!DOCTYPE HTML>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <script src=\"file:///android_asset/math.min.js\" type=\"text/javascript\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "</body>\n" +
            "</html>";
}

