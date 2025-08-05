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

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static Context sContext;
    public static MyApplication app;

    public KeyInjectOpt mKeyInjectOpt;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        sContext = getApplicationContext();

        mConnectHandler.sendEmptyMessageDelayed(1, 1000);
    }

    public void bindRKISDKService() {
        SunmiRKIKernel.getInstance().initRKISDK(this, connectRKISDKCallback);
    }

    private final Looper mainLooper = Looper.getMainLooper();

    @SuppressLint("HandlerLeak")
    private final Handler mConnectHandler = new Handler(mainLooper) {

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
            Log.e(TAG, "onConnectRKISDK");
            mKeyInjectOpt = SunmiRKIKernel.getInstance().mKeyInjectOpt;
        }

        @Override
        public void onDisconnectRKISDK() {
            Log.e(TAG, "onDisconnectRKISDK");
            mKeyInjectOpt = null;
            bindRKISDKService();
        }

    };

}