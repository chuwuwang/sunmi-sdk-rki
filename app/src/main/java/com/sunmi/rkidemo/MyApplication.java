package com.sunmi.rkidemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.sunmi.keyinject.binder.KeyInjectOpt;
import com.sunmi.rki.SunmiRKIKernel;

import java.util.Objects;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static Context sContext;
    public static MyApplication app;
    public KeyInjectOpt mKeyInjectOpt;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        app = this;
        mConnectHandler.sendEmptyMessageDelayed(1, 1000);
    }

    public void bindRKISDKService() {
        SunmiRKIKernel.getInstance().initRKISDK(this, connectRKISDKCallback);
    }

    @SuppressLint("HandlerLeak")
    private final Handler mConnectHandler = new Handler(Objects.requireNonNull(Looper.myLooper())) {

        @Override
        public void handleMessage(Message msg) {
            if (mKeyInjectOpt == null) {
                bindRKISDKService();
                mConnectHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                mConnectHandler.removeCallbacksAndMessages(null);
            }
        }

    };

    SunmiRKIKernel.ConnectRKISDKCallback connectRKISDKCallback = new SunmiRKIKernel.ConnectRKISDKCallback() {

        @Override
        public void onConnectRKISDK() {

            mKeyInjectOpt = SunmiRKIKernel.getInstance().mKeyInjectOpt;
            Log.e(TAG, "onConnectRKISDK");
        }

        @Override
        public void onDisconnectRKISDK() {

            mKeyInjectOpt = null;
            Log.e(TAG, "onDisconnectRKISDK");
            bindRKISDKService();
        }
    };
}
