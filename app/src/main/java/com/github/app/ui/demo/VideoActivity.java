package com.github.app.ui.demo;

import android.os.Environment;
import android.view.View;

import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.Config;
import com.github.app.utils.VideoUrl;
import com.github.app.widget.MediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benny
 * on 2017/10/14.
 * 视频播放器
 */

public class VideoActivity extends BaseActivity implements
        PLMediaPlayer.OnPreparedListener,
        PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnCompletionListener,
        PLMediaPlayer.OnVideoSizeChangedListener,
        PLMediaPlayer.OnErrorListener {
    private PLVideoTextureView mVideoView;
    private boolean isPath = false;
    private boolean isLiveStreaming=true;

    @Override
    public int bindLayout() {
        return R.layout.video_layout;
    }

    @Override
    public void initView() {
        super.initView();
        mVideoView = (PLVideoTextureView) findViewById(R.id.PLVideoTextureView);
        initSetting();
        isPath = true;

        //本地的视频  需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/fl1234.mp4" ;
        initSetAddress(VideoUrl.big_buck_bunny);
        initPlayStatus();
    }

    private void initPlayStatus() {
        //  4.3.10 播放控制
        //  如果已经关联了 MediaController，可以直接通过该控件实现播放过程的控制，
        // 包括：暂停、继续、停止等，当然，
        // 您也可以通过 PLVideoView 提供的接口自行进行播放过程的控制，相关函数如下：
      //  mVideoView.start();
//        mVideoView.pause();
//        mVideoView.stopPlayback();
    }

    /**
     * 4.3.9 设置播放地址
     */
    private void initSetAddress(String videoPath) {
        if (isPath) {
            mVideoView.setVideoPath(videoPath);
            mVideoView.start();
        } else {
            Map<String, String> headers = new HashMap<>();
            mVideoView.setVideoPath(videoPath, headers);
        }
    }

    private void initSetting() {
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        if (!isLiveStreaming && cache) {
            options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
        }
        mVideoView.setAVOptions(options);
        mVideoView.setDebugLoggingEnabled(true);
        // 关联播放控制器
        MediaController mMediaController = new MediaController(this,!isLiveStreaming,isLiveStreaming);
        mVideoView.setMediaController(mMediaController);

        //设置加载动画
        View loadingView = findViewById(R.id.progressBar);
        mVideoView.setBufferingIndicator(loadingView);

        // 设置播放状态监听器
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);
        mVideoView.setOnErrorListener(this);

        //设置画面预览模式
        //PLVideoView 和 PLVideTextureView 提供了各种画面预览模式，
        //包括：原始尺寸、适应屏幕、全屏铺满、16:9、4:3 等，设置方法如下：
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_4_3);

        //4.3.7 设置画面旋转
        // PLVideTextureView 还支持画面旋转，支持播放画面以 0度，90度，180度，270度旋转，设置方法如下：
        mVideoView.setDisplayOrientation(90); // 旋转90度

        //4.3.8 设置播放画面镜像变换
        mVideoView.setMirror(true);
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {

    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int i, int i1) {

    }
}
