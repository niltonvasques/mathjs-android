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
        mMath = new MathJS();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, mMath.eval("2 * 5 ^ 2"), Toast.LENGTH_SHORT).show();
        mMath.asyncEval("2 * 5 ^ 2 + 33", new MathJS.MathJSResult() {
            @Override
            public void onEvaluated(final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMath.destroy();
    }
}
