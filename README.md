# mathjs-android
An android wrapper library to mathjs.org javascript library

## Installation

#### Gradle
Mathjs is available on jitpack.

```gradle
    compile 'com.github.niltonvasques:mathjs-android:0.1.0'
```

### Usage
```java
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
```
