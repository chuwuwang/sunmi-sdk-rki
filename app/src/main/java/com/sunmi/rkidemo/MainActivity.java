package com.sunmi.rkidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.sunmi.keyinject.binder.CommonCallback;
import com.sunmi.keyinject.binder.QueryKeyCallback;
import com.sunmi.keyinject.http.bean.KeyPropertyRspEntity;
import com.sunmi.rkidemo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    ActivityMainBinding binding;
    Button downloadKeyB;
    Button showKeyB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        downloadKeyB = binding.downloadKeyB;
        showKeyB = binding.showKeyB;
        downloadKeyB.setOnClickListener(this);
        showKeyB.setOnClickListener(this);
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

        try {
            if (MyApplication.app.mKeyInjectOpt != null) {
                MyApplication.app.mKeyInjectOpt.downloadKey(0, new CommonCallback.Stub() {
                    @Override
                    public void onSuccess() throws RemoteException {
                        ToastUtils.toast(MainActivity.this, "onSuccess");
                    }

                    @Override
                    public void onFailure(int i) throws RemoteException {
                        ToastUtils.toast(MainActivity.this, "onFailure");
                    }
                });
            } else {
                ToastUtils.toast(MainActivity.this, "Please connect RKI SDK first!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void queryKeyList() {

        try {
            if (MyApplication.app.mKeyInjectOpt != null) {
                MyApplication.app.mKeyInjectOpt.queryKeyList(0, new QueryKeyCallback.Stub() {

                    @Override
                    public void onResponse(List<KeyPropertyRspEntity> list) throws RemoteException {

                        StringBuilder temp = new StringBuilder();
                        for (KeyPropertyRspEntity keyPropertyRspEntity : list) {
                            temp.append(keyPropertyRspEntity.toString());
                            temp.append("\n");
                            Log.e("KeyPropertyRspEntity", keyPropertyRspEntity.toString());
                        }
                    }

                    @Override
                    public void onFailure(int i) throws RemoteException {
                        ToastUtils.toast(MainActivity.this, "onFailure");
                    }
                });
            } else {
                ToastUtils.toast(MainActivity.this, "Please connect RKI SDK first!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
