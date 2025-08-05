package com.sunmi.rkidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sunmi.keyinject.binder.CommonCallback;
import com.sunmi.keyinject.binder.QueryKeyCallback;
import com.sunmi.keyinject.http.bean.KeyPropertyRspEntity;
import com.sunmi.rkidemo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    ActivityMainBinding binding;

    Button showKeyB;
    Button downloadKeyB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = getLayoutInflater();
        binding = ActivityMainBinding.inflate(layoutInflater);
        LinearLayout root = binding.getRoot();
        setContentView(root);

        showKeyB = binding.showKeyB;
        downloadKeyB = binding.downloadKeyB;

        showKeyB.setOnClickListener(this);
        downloadKeyB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download_key_b) {
            downloadKey();
        } else if (v.getId() == R.id.show_key_b) {
            queryKeyList();
        }
    }

    private void downloadKey() {
        if (MyApplication.app.mKeyInjectOpt == null) {
            ToastUtils.toast(MainActivity.this, "Please connect RKI SDK first!");
            return;
        }
        try {
            CommonCallback.Stub commonCallback = new CommonCallback.Stub() {

                @Override
                public void onSuccess() throws RemoteException {
                    Log.e("CommonCallback", "onSuccess");
                    ToastUtils.toast(MainActivity.this, "onSuccess");
                }

                @Override
                public void onFailure(int code) throws RemoteException {
                    Log.e("CommonCallback", "onFailure: " + code);
                    ToastUtils.toast(MainActivity.this, "onFailure");
                }

            };
            MyApplication.app.mKeyInjectOpt.downloadKey(0, commonCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void queryKeyList() {
        if (MyApplication.app.mKeyInjectOpt == null) {
            ToastUtils.toast(MainActivity.this, "Please connect RKI SDK first!");
            return;
        }
        QueryKeyCallback.Stub queryKeyCallback = new QueryKeyCallback.Stub() {

            @Override
            public void onResponse(List<KeyPropertyRspEntity> list) throws RemoteException {
                StringBuilder temp = new StringBuilder();
                for (KeyPropertyRspEntity entity : list) {
                    String string = entity.toString();
                    temp.append(string);
                    temp.append("\n");
                    Log.e("KeyPropertyRspEntity", string);
                }
            }

            @Override
            public void onFailure(int i) throws RemoteException {
                ToastUtils.toast(MainActivity.this, "onFailure");
            }

        };
        try {
            MyApplication.app.mKeyInjectOpt.queryKeyList(0, queryKeyCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}