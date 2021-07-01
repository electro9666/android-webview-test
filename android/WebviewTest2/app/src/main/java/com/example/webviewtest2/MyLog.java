package com.example.webviewtest2;

import android.content.Context;
import android.util.Log;

public class MyLog {
    public static void i(String tag, String s) {
        Log.d("MyLog", "(i)tag: " + tag + ", " + "s: " + s);
    }

    public static void toastMakeTextShow(Context context, String tag, String s) {
        Log.d("MyLog", "(toastMakeTextShow) tag: " + tag + ", " + "s: " + s);
    }

    public static void e(String tag, String s) {
        Log.d("MyLog", "(e)tag: " + tag + ", " + "s: " + s);
    }
}
