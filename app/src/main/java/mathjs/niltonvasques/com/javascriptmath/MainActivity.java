package mathjs.niltonvasques.com.javascriptmath;

import android.app.Activity;
import android.os.Bundle;

import mathjs.niltonvasques.com.javascriptmath.math.MathJS;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MathJS math = new MathJS(getApplicationContext());
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
