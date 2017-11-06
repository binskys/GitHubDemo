package com.github.app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.app.R;
import com.github.app.bean.BleBean;
import com.github.app.bean.DataBean;
import com.github.app.ui.WebActivity;
import com.vise.baseble.model.BluetoothLeDevice;

import java.util.List;
import java.util.UUID;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class BleAdapter extends GitHubBaseAdapter<BluetoothLeDevice> {
    public BleOpenCallBack callBack;

    public BleAdapter(Context context, List<BluetoothLeDevice> list) {
        super(context, list);
    }

    public void setCallBack(BleOpenCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected View itemView(final int position, View convertView, ViewGroup parent) {
        GitHubHolder hubHolder = null;
        if (convertView == null) {
            hubHolder = new GitHubHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bluetooth_layout_item, null);
            hubHolder.title = (TextView) convertView.findViewById(R.id.tv_name);
            hubHolder.address = (TextView) convertView.findViewById(R.id.tv_address);
            hubHolder.btn_open = (Button) convertView.findViewById(R.id.btn_open);
            convertView.setTag(hubHolder);
        } else {
            hubHolder = (GitHubHolder) convertView.getTag();
        }
       final BluetoothLeDevice device=list.get(position);
        hubHolder.title.setText(device.getName() + "\n" +
                        device.getAddress()+"\n"
               // +uuid.toString()
        );
        hubHolder.address.setText("");
        hubHolder.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.open(list.get(position).getAddress(),device);
            }
        });
        return convertView;
    }

    public interface BleOpenCallBack {
        void open(String address, BluetoothLeDevice device);
    }
}
