package com.niltonvasques.mathjs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

        findViewById(R.id.btnCompute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = ((EditText)findViewById(R.id.editTxtEquation));
                String answer = mMath.eval(edit.getText().toString());
                Toast.makeText(MainActivity.this, answer, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMath.destroy();
    }
}
