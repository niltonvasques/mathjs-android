package mathjs.niltonvasques.com.javascriptmath;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mathjs.niltonvasques.com.javascriptmath.math.MathJS;

import static mathjs.niltonvasques.com.javascriptmath.math.MathJS.MATH_JS_HTML;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final WebView webView = (WebView) findViewById(R.id.webiew);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MathJS math = new MathJS(this);
        math.eval("2 * 5 ^ 2", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                System.out.println("MathJS.onEvaluated "+value);
            }
        });
        math.eval("2 * 5 ^ 2 + 33", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                System.out.println("MathJS.onEvaluated "+value);
            }
        });
    }
}
