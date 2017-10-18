package com.github.app.ui.demo;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;


import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.ConfigUtils;
import com.github.app.utils.LogUtils;
import com.github.app.utils.SPUtils;
import com.github.app.utils.ScreenUtils;
import com.shan.ijkplayer_android.widget.media.AndroidMediaController;
import com.shan.ijkplayer_android.widget.media.IMediaController;
import com.shan.ijkplayer_android.widget.media.IjkVideoView;

import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by benny
 * on 2017/10/17.
 */

public class IjkPlayerActivity extends BaseActivity {
    private IjkVideoView mVideoView;
    private int screenWidth=0;
    private int screenHeight=0;
    private boolean mBackPressed = false;//是否停止播放标识
    private boolean isStart = false;//是否重新播放标识
    private AppCompatSeekBar mSeekBar;
    private TextView time;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    refreshTime();
                    break;
            }
        }
    };
    private Timer timer;
    private TimerTask task;
    private TextView count_time;
    private int seekBarMax = 1000;
    private boolean isCountTime=true;//是否重新获取视频总时长标识
    protected String videoPath="http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4";
    @Override
    public int bindLayout() {
        return R.layout.ijkpalyer_layout;
    }

    @Override
    public void bindView() {
        super.bindView();
        initView();
        initPlayer();
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        isCountTime=true;
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        AndroidMediaController mMediaController;
        //这里使用的是Demo中提供的AndroidMediaController类控制播放相关操作
        mMediaController = new AndroidMediaController(this, false);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(videoPath);
    }

    private void initView() {
        time = (TextView) findViewById(R.id.tv_time);
        count_time = (TextView) findViewById(R.id.count_time);
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mSeekBar = (AppCompatSeekBar) findViewById(R.id.mSeekBar);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_fangda).setOnClickListener(this);
        mSeekBar.setMax(seekBarMax);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.d(1111 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogUtils.d(2222222 + "");

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mVideoView.seekTo(seekBar.getProgress() * mVideoView.getDuration() / seekBarMax);
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_start:
                onStartVideo();
                break;
            case R.id.btn_pause:
                onPauseVideo();
                isStart=false;
                break;
            case R.id.btn_stop:
                mBackPressed = true;
                isStart=true;
                onStopVideo();
                break;
            case R.id.btn_fangda:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseVideo();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onStopVideo();
    }

    /**
     * 播放
     */
    private void onStartVideo() {
        if (isStart){
            //停止后重新播放
            mVideoView.setVideoPath(videoPath);
        }
        int pauseTime = (int) SPUtils.get(ConfigUtils.PAUSE_TIME, 0);
        if (pauseTime != 0) {
            mVideoView.seekTo(pauseTime);
        }
        mVideoView.start();
        startTime();

    }

    /**
     * 暂停
     */
    private void onPauseVideo() {
        if (mVideoView==null||!mVideoView.isPlaying())return;
        mVideoView.pause();
        SPUtils.put(ConfigUtils.PAUSE_TIME, mVideoView.getCurrentPosition());
        stopTime();

    }

    /**
     * 停止
     */
    private void onStopVideo() {
        //点击返回或不允许后台播放时 释放资源
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            Log.i("tag", "onStopVideo: "+mVideoView.getDuration());
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
        SPUtils.put(ConfigUtils.PAUSE_TIME, 0);
        stopTime();
    }

    /**
     * 开始播放计时 通过定时器间隔发送handle消息刷新播放时间
     */
    private void startTime() {
        if(isCountTime) {
            countTime();//计算总时长
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //视频开始播放时使用handle.sendMessageDelayed更新时间显示
                handler.sendEmptyMessage(10);
            }
        };
        timer.schedule(task, 1, 500);

    }

    /**
     * 停止播放后，时间也停止
     */
    private void stopTime() {
        if (timer == null || task == null) return;
        timer.cancel();
        task.cancel();
    }

    /**
     * 刷新时间
     */
    private void refreshTime() {
        int totalSeconds = mVideoView.getDuration() / 1000;
        int currentSeconds = mVideoView.getCurrentPosition() / 1000;
        int seconds = currentSeconds % 60;
        int minutes = (currentSeconds / 60) % 60;
        int hours = currentSeconds / 3600;
        String ti = hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
        time.setText(ti);
        Log.i("info", "totalSeconds: " + totalSeconds);
        Log.i("info", "currentSeconds: " + currentSeconds);
        if (currentSeconds != 0 || totalSeconds != 0) {
            int progress = currentSeconds * seekBarMax / totalSeconds;
            mSeekBar.setProgress(progress);
        }

    }

    /**
     * 计算视频总时间
     */

    private void countTime() {
        isCountTime=false;
        int totalSeconds = mVideoView.getDuration() / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        String ti = hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
        count_time.setText(ti);
    }
    private static final int SIZE_DEFAULT = 0;
    private static final int SIZE_4_3 = 1;
    private static final int SIZE_16_9 = 2;
    private int currentSize = SIZE_16_9;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onPauseVideo();
        //重新获取屏幕宽高
        initScreenInfo();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//切换为横屏
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mVideoView.getLayoutParams();
            lp.height = screenHeight;
            lp.width = screenWidth;
            mVideoView.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mVideoView.getLayoutParams();
            lp.height = screenWidth * 9 / 16;
            lp.width = screenWidth;
            mVideoView.setLayoutParams(lp);
        }
        setScreenRate(currentSize);
    }

    private void initScreenInfo() {
        screenWidth= ScreenUtils.getScreenWidth();
        screenHeight= ScreenUtils.getScreenHeight();
                
    }

    public void setScreenRate(int rate) {
        int width = 0;
        int height = 0;
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {// 横屏
            if (rate == SIZE_DEFAULT) {
                width = mVideoView.getmVideoWidth();
                height = mVideoView.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenHeight / 3 * 4;
                height = screenHeight;
            } else if (rate ==SIZE_16_9) {
                width = screenHeight / 9 * 16;
                height = screenHeight;
            }
        } else { //竖屏
            if (rate == SIZE_DEFAULT) {
                width = mVideoView.getmVideoWidth();
                height = mVideoView.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenWidth;
                height = screenWidth * 3 / 4;
            } else if (rate == SIZE_16_9) {
                width = screenWidth;
                height = screenWidth * 9 / 16;
            }
        }
        if (width > 0 && height > 0) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mVideoView.getmRenderView().getView().getLayoutParams();
            lp.width = width;
            lp.height = height;
            mVideoView.getmRenderView().getView().setLayoutParams(lp);
        }
    }
}
