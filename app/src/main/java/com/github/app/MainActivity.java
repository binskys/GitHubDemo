package com.github.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.app.adapter.GitHubAdapter;
import com.github.app.ui.BaseActivity;
import com.github.app.ui.WebActivity;
import com.github.app.ui.demo.PatternLockActivity;
import com.github.app.ui.demo.ShadowActivity;
import com.github.app.utils.DataBean;
import com.github.app.utils.DataUtils;
import com.github.app.utils.ToastUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import com.willowtreeapps.spruce.sort.SortFunction;
import com.willowtreeapps.spruce.sort.SpruceTimedView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private ListView github;
    private GitHubAdapter mAdapter;
    private List<DataBean> list = new ArrayList<>();
    private Animator spruceAnimator;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        list= DataUtils.getDataList();
        github = (ListView) findViewById(R.id.demo_list);
        github.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchItem (parent,view,position);
            }
        });
        github.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initAnim();
            }
        });

    }

    private void initAnim() {
        spruceAnimator = new Spruce.SpruceBuilder(github)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(github, 800),
                        ObjectAnimator.ofFloat(github, "translationX", -github.getWidth(), 0f).setDuration(800))
                .start();
    }



    private void switchItem(AdapterView<?> parent, View view, int position) {
        Bundle bundle=new Bundle();
        switch (position){
            case 0:
                mAdapter.notifyDataSetChanged();
               ToastUtils.toast("当前界面动画");
                break;
            case 1:
                startActivity(PatternLockActivity.class,null);
                break;
            case 2:
                startActivity(ShadowActivity.class,null);
                break;
            default:
                ToastUtils.toast("无数据");
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (spruceAnimator!=null){
            spruceAnimator.start();
        }
        initData();
    }

    private void initData() {
        if (mAdapter == null) {
            mAdapter = new GitHubAdapter(this,list);
            github.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
