package com.niltonvasques.mathjs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import mathjs.niltonvasques.com.mathjs.MathJS;


public class MainActivity extends Activity {

    private MathJS mMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMath = new MathJS(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMath.eval("2 * 5 ^ 2", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });
        mMath.eval("2 * 5 ^ 2 + 33", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(String value) {
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMath.destroy();
    }
}
