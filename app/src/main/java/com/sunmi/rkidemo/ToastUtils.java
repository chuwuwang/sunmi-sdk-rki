package com.sunmi.rkidemo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {

    private static final Looper mainLooper = Looper.getMainLooper();
    private static final Handler handler = new Handler(mainLooper);

    public static void toast(final Context context, final String text) {
        handler.post( () -> Toast.makeText(context, text, Toast.LENGTH_SHORT).show() );
    }

    public static void toast(final Context context, final int resId) {
        handler.post( () -> Toast.makeText(context, resId, Toast.LENGTH_SHORT).show() );
    }

}