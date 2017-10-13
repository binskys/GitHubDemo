package com.github.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.app.R;
import com.github.app.utils.DataBean;
import com.github.app.utils.DataUtils;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class WebActivity extends BaseActivity {
    private WebView webView;
    private DataBean dataBean;

    @Override
    public int bindLayout() {
        return R.layout.web_layout;
    }

    @Override
    public void initView() {
        super.initView();
        webView = (WebView) findViewById(R.id.webView);
        dataBean = (DataBean) getIntent().getSerializableExtra("beanData");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dataBean != null) {
            setTitle(dataBean.getTitle());
            webView.loadUrl(dataBean.getAddress());
        }
    }
}
