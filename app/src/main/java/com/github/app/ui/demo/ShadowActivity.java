package com.github.app.ui.demo;

import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yinglan.shadowimageview.ShadowImageView;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class ShadowActivity extends BaseActivity {
    ShadowImageView shadow;
    private AppCompatSeekBar seekBar;
    private int resId = 1;
    @Override
    public int bindLayout() {
        return R.layout.shadow_layout;
    }

    @Override
    public void bindView() {
        super.bindView();

        this.shadow = (ShadowImageView) findViewById(R.id.shadow);
        this.seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);

        shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = R.mipmap.lotus;
                switch (resId) {
                    case 1:
                        res = R.mipmap.mountain;
                        resId = 2;
                        break;
                    case 2:
                        res = R.mipmap.sunset;
                        resId = 3;
                        break;
                    case 3:
                        res = R.mipmap.red;
                        resId = 4;
                        break;
                    case 4:
                        res = R.mipmap.lotus;
                        resId = 1;
                        break;
                }
                if (resId == 1 || resId == 3)
                    shadow.setImageResource(res);
                else
                    shadow.setImageDrawable(getResources().getDrawable(res));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ToastUtils.toast(progress+"");
                shadow.setImageRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        loadNetImage();
    }

    private void loadNetImage() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);

        //此处加载的是本地的图片，网络图片用法一至
        ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.lotus, new ImageView(this), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ((ShadowImageView)findViewById(R.id.shadowd)).setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }
}
