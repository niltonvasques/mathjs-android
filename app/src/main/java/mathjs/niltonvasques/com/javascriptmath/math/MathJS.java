package mathjs.niltonvasques.com.javascriptmath.math;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import mathjs.niltonvasques.com.javascriptmath.BuildConfig;

/**
 * Created by niltonvasques on 11/17/16.
 */

// Roboeletric still not supports API 24 stuffs
@Config(sdk = 23, constants=BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class MathJS {
    private WebView mWebView;
    public MathJS(Context context) {
        mWebView = new WebView(context);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadDataWithBaseURL("file:///android_asset/", mMathJSHtml, "text/html",
                "utf-8", "");
    }

    public void eval(String expr, final MathJSResult result) {
        mWebView.evaluateJavascript("(function() { return math.eval('2 * 5'); })();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                System.out.println("MainActivity.onReceiveValue "+value);
                result.onEvaluated(value);
            }
        });
    }

    public interface MathJSResult {
        public void onEvaluated(String value);
    }

    private String mMathJSHtml = "<!DOCTYPE HTML>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <script src=\"file:///android_asset/math.js\" type=\"text/javascript\"></script>\n" +
            "</head>\n" +
            "<body>\n" +
            "</body>\n" +
            "</html>";
}
