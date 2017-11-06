package com.github.app.ui.demo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.github.app.R;
import com.github.app.adapter.BleAdapter;
import com.github.app.databinding.BluetoothLayoutBinding;
import com.github.app.ui.BaseDataActivity;
import com.github.app.utils.BleUtils;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny
 * on 2017/10/18.
 * 蓝牙开发
 */

public class BluetoothActivity extends BaseDataActivity<BluetoothLayoutBinding> {
    private BleAdapter mAdapter;
    private List<BluetoothLeDevice> list = new ArrayList<>();


    @Override
    protected void initOnCreate() {
        super.initOnCreate();
        BleUtils.init(this);
        initCheckSelfPermission();
    }

    @Override
    public int bindLayout() {
        return R.layout.bluetooth_layout;
    }

    @Override
    protected void initData() {
        if (mAdapter == null) {
            mAdapter = new BleAdapter(this, list);
            mBinding.listBle.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }


    private void initCheckSelfPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        onClickListener(mBinding.scanDevice);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.scan_device:
                //扫描所有设备
                scanAllDevice();
                break;
        }
    }

    private void scanAllDevice() {
        ViseBle.getInstance().startScan(new ScanCallback(new IScanCallback() {
            @Override
            public void onDeviceFound(BluetoothLeDeviceStore deviceStore) {
                deviceStore.getDeviceList();
            }

            @Override
            public void onScanFinish(BluetoothLeDeviceStore deviceStore) {
                list=deviceStore.getDeviceList();
            }

            @Override
            public void onScanTimeout() {

            }
        }));
    }

}
