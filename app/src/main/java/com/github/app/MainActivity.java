package com.github.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.app.adapter.GitHubAdapter;
import com.github.app.service.GitHubService;
import com.github.app.ui.BaseActivity;
import com.github.app.ui.WebActivity;
import com.github.app.ui.demo.PatternLockActivity;
import com.github.app.ui.demo.ShadowActivity;
import com.github.app.utils.DataBean;
import com.github.app.utils.DataUtils;
import com.github.app.utils.LogUtils;
import com.github.app.utils.ServiceUtils;
import com.github.app.utils.ToastUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import com.willowtreeapps.spruce.sort.SortFunction;
import com.willowtreeapps.spruce.sort.SpruceTimedView;

import java.util.ArrayList;
import java.util.List;

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
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
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
        switch (position) {
            case 0:
                mAdapter.notifyDataSetChanged();
                ToastUtils.toast("当前界面动画");
                break;
            case 1:
                startActivity(PatternLockActivity.class, null);
                break;
            case 2:
                startActivity(ShadowActivity.class, null);
                break;
            default:
                ToastUtils.toast("无数据");
                break;
        }
    }


    @Override
    protected void onResume() {
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
        }
    }
}
