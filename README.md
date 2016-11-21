# mathjs-android

[![Build Status](https://travis-ci.org/niltonvasques/mathjs-android.svg?branch=travis)](https://travis-ci.org/niltonvasques/mathjs-android)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MathJS%20Android-green.svg?style=flat)](http://android-arsenal.com/details/1/4675)

An android wrapper library to mathjs.org javascript library

## Installation

#### Gradle
Mathjs is available on jitpack.

Add jitpack to your root build.gradle:

```gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

Add the library to app build.gradle

```gradle
    compile 'com.github.niltonvasques:mathjs-android:v0.2.0'
```

### Usage
```java
    MathJS math = new MathJS();
    
    // Synchronously evaluating
    String answer = math.eval("2 * 5 ^ 2");
    
    // Asynchronously evaluating
    math.asyncEval("2 * 5 ^ 2 + 33", new MathJS.MathJSResult() {
        @Override
        public void onEvaluated(String value) {
            System.out.println("MathJS.onEvaluated "+value);
        }
    });
    
    math.destroy(); //Call after the library been used
```

#### Evaluator syntax

To see more syntax examples see official [#eval()](http://mathjs.org/docs/expressions/parsing.html#eval) docs from mathjs

```java
    // expressions
    math.eval('1.2 * (2 + 4.5)');     // 7.8
    math.eval('5.08 cm to inch');     // 2 inch
    math.eval('sin(45 deg) ^ 2');     // 0.5
    math.eval('9 / 3 + 2i');          // 3 + 2i
    math.eval('det([-1, 2; 3, 1])');  // -7
```
