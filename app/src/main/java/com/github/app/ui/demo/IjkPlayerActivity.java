package com.github.app.ui.demo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.ConfigUtils;
import com.github.app.utils.LogUtils;
import com.github.app.utils.SPUtils;
import com.shan.ijkplayer_android.widget.media.AndroidMediaController;
import com.shan.ijkplayer_android.widget.media.IjkVideoView;

import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by benny
 * on 2017/10/17.
 */

public class IjkPlayerActivity extends BaseActivity {
    private IjkVideoView mVideoView;
    private boolean mBackPressed = false;
    private boolean isStart = false;//
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
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        AndroidMediaController mMediaController;
        //这里使用的是Demo中提供的AndroidMediaController类控制播放相关操作
        mMediaController = new AndroidMediaController(this, false);
        //mMediaController.setSupportActionBar(actionBar);
        //  mVideoView.setMediaController(mMediaController);
        //mVideoView.setHudView(tableLayout);

        //  mVideoView.setVideoPath("http://main.gslb.ku6.com/broadcast/sub?channel=910");
        //   mVideoView.setVideoPath("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
        mVideoView.setVideoPath("http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4");
        //mVideoView.setVideoPath("http://192.168.20.172:8080/examples/app/zuoban.mp4");
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
                break;
            case R.id.btn_stop:
                mBackPressed = true;
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
    protected void onStop() {
        super.onStop();
        onPauseVideo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onStartVideo();
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
        int pauseTime = (int) SPUtils.get(ConfigUtils.PAUSE_TIME, 0);
        if (pauseTime != 0) {
            mVideoView.seekTo(pauseTime);
        }
        isStart = true;
        mVideoView.start();
        startTime();

    }

    /**
     * 暂停
     */
    private void onPauseVideo() {
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
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
        stopTime();
    }

    /**
     * 开始播放计时 通过定时器间隔发送handle消息刷新播放时间
     */
    private void startTime() {
        countTime();
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
        SPUtils.put(ConfigUtils.PAUSE_TIME, 0);
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
        int totalSeconds = mVideoView.getDuration() / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        String ti = hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
        count_time.setText(ti);
    }
}
