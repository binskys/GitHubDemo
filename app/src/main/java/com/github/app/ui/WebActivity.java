package com.github.app.ui;

import android.webkit.WebView;

import com.github.app.R;
import com.github.app.bean.DataBean;

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
    public void bindView() {
        super.bindView();
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
