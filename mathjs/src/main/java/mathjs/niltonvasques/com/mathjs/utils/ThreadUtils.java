package mathjs.niltonvasques.com.mathjs.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by niltonvasques on 11/17/16.
 */

public class ThreadUtils {
    public static boolean isOnMain() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void ensureNotOnMain(){
        if(isOnMain())
            throw new DispatchOnMainThreadException();
    }

    public static void runOnMain(Runnable runnable) {
        if(isOnMain())
            runnable.run();
        else
            new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void runInBackground(final Runnable runnable) {
        new Thread(runnable).start();
    }

    public static int getId() {
        return android.os.Process.myTid();
    }

    public static class DispatchOnMainThreadException extends RuntimeException {}
}

