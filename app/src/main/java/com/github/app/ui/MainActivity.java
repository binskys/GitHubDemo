package com.github.app.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.app.R;
import com.github.app.adapter.GitHubAdapter;
import com.github.app.broadcastreceiver.BRUtils;
import com.github.app.service.GitHubService;
import com.github.app.ui.demo.BluetoothActivity;
import com.github.app.ui.demo.CircleBarActivity;
import com.github.app.ui.demo.IjkPlayerActivity;
import com.github.app.ui.demo.PatternLockActivity;
import com.github.app.ui.demo.RxJavaActivity;
import com.github.app.ui.demo.ShadowActivity;
import com.github.app.bean.DataBean;
import com.github.app.utils.DataUtils;
import com.github.app.utils.LogUtils;
import com.github.app.service.ServiceUtils;
import com.github.app.utils.ToastUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private ListView github;
    private GitHubAdapter mAdapter;
    private List<DataBean> list = new ArrayList<>();
    private Animator spruceAnimator;
    private GitHubService.MyBinder myBinder;

    /*   创建了一个ServiceConnection的匿名类，
    在里面重写了onServiceConnected()方法和onServiceDisconnected()方法，
    这两个方法分别会在Activity与Service建立关联和解除关联的时候调用
     */ private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("onServiceDisconnected()");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d("onServiceConnected()");//关联绑定服务
            myBinder = (GitHubService.MyBinder) service;
            myBinder.startDownload();
        }
    };

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void bindView() {
        super.bindView();
        list = DataUtils.getDataList();
        github = (ListView) findViewById(R.id.demo_list);
        github.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchItem(parent, view, position);
            }
        });
        github.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initAnim();
            }
        });
        initOnClick();
    }

    private void initAnim() {
        spruceAnimator = new Spruce.SpruceBuilder(github)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(github, 800),
                        ObjectAnimator.ofFloat(github, "translationX", -github.getWidth(), 0f).setDuration(800))
                .start();
    }


    private void switchItem(AdapterView<?> parent, View view, int position) {
        Bundle bundle = new Bundle();
        switch (list.get(position).getTag()) {
            case 11:
                startActivity(BluetoothActivity.class, null);
                break;
            case 1:
                mAdapter.notifyDataSetChanged();
                ToastUtils.toast("当前界面动画");
                break;
            case 2:
                startActivity(PatternLockActivity.class, null);
                break;
            case 3:
                startActivity(ShadowActivity.class, null);
                break;
            case 4:
                startActivity(RxJavaActivity.class, null);
                break;
            case 5:
                startWeb(list.get(position));
                break;
            case 6:
                startActivity(CircleBarActivity.class, null);
                break;
            case 7:
            case 8:
            case 9:
                startWeb(list.get(position));
            break;
            case 10:
                startActivity(IjkPlayerActivity.class,null);
                break;
            default:
                ToastUtils.toast("无数据");
                break;
        }
    }

    private void startWeb(DataBean dataBean) {
        Intent intent=new Intent(this, WebActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("beanData",dataBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
        initData();
    }

    private void initData() {
        if (mAdapter == null) {
            mAdapter = new GitHubAdapter(this, list);
            github.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initOnClick() {
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.unbind_service).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.un_register).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_start:
                ServiceUtils.start(this);
                break;
            case R.id.btn_stop:
                ServiceUtils.stop(this);
                break;
            case R.id.bind_service:
                ServiceUtils.bind(this,connection);
                break;
            case R.id.unbind_service:
                ServiceUtils.unBind(this,connection);
                break;
            case R.id.register:
                BRUtils.register(this,BRUtils.NET_ACTION);//监听网络状态广播
                break;
            case R.id.un_register:
                BRUtils.unRegister(this);
                break;
            case R.id.btn_send:
                Timer time=new Timer();
                TimerTask task=  new TimerTask() {
                    @Override
                    public void run() {
                        BRUtils.send(MainActivity.this,BRUtils.NET_ACTION);
                    }
                };
                time.schedule(task,1000,3000);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BRUtils.unRegister(this);
    }
}
