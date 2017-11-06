package com.github.app.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.github.app.broadcastreceiver.MyBroadcastReceiver;

/**
 * @author  by benny
 * on 2017/10/13.
 */

public class BaseDataActivity<T extends ViewDataBinding>  extends AppCompatActivity implements View.OnClickListener{

    protected T mBinding;
    protected MyBroadcastReceiver receiver=new MyBroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceiveData(context,intent);
        }
    };

    protected void onReceiveData(Context context, Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
    }

    private void setView() {
        LinearLayout layout=new LinearLayout(this);
        LayoutParams mParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(mParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        if (bindLayout()!=0){
            mBinding= DataBindingUtil.inflate(LayoutInflater.from(this),bindLayout(),null,false);
            layout.addView(mBinding.getRoot(), mParams);
            initOnCreate();
            initData();
            initEvent();
        }
        setContentView(layout);
    }

    protected void initOnCreate() {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    protected void onClickListener(View view) {
        view.setOnClickListener(this);
    }
    public int bindLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onClick(View v) {

    }
}
