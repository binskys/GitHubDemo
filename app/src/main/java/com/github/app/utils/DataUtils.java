package com.github.app.utils;

import com.github.app.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class DataUtils {
    public static List<DataBean> getDataList() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("11.  Android BLE蓝牙4.0开发",
                "蓝牙发展至今经历了8个版本的更新。1.1、1.2、2.0、2.1、3.0、4.0、4.1、4.2。那么在1.x~3.0之间的我们称之为传统蓝牙，4.x开始的蓝牙我们称之为低功耗蓝牙也就是蓝牙ble。低功耗蓝牙较传统蓝牙，传输速度更快，覆盖范围更广，安全性更高，延迟更短，耗电极低等等优点。",
                UrlList.ijkPalyer,11));

















        list.add(new DataBean("1. Spruce Android Animation Library (and iOS)",
        "Spruce 是一个轻量级的动画库，可以帮助排版屏幕上的动画。使用有很多不同的动画库时，开发人员和程序员需要确保每个视图都能够在适当的时间活动。 Spruce 可以帮助设计师获得复杂的多视图动画，而不是让开发人员在原型阶段就感到畏惧。"
        ,"https://github.com/willowtreeapps/spruce-android",1));
        list.add(new DataBean("2. PatternLockView",
        "PatternLockView 这个库可以在应用中简单快速的实现图形锁机制。它有大 量的个性化选项可以用于改变功能和外观，以此满足你的需求，非常的实 用。重点是它还支持响应式的 RxJava 2 视图绑定。",
                "https://github.com/aritraroy/PatternLockView",2));
        list.add(new DataBean("3. ShadowImageView",
        "PatternLockView 这个库可以在应用中简单快速的实现图形锁机制。它有大 量的个性化选项可以用于改变功能和外观，以此满足你的需求，非常的实 用。重点是它还支持响应式的 RxJava 2 视图绑定。",
                "https://github.com/yingLanNull/ShadowImageView",3));
        list.add(new DataBean("4. RxJava的运用",
        "清晰 & 易懂的 Rxjava 入门教程",
                "http://www.jianshu.com/p/0cd258eecf60",4));
        list.add(new DataBean("5. 视频播放器",
        "PLDroidPlayer 是一个适用于 Android 平台的音视频播放器 SDK，可高度定制化和二次开发，为 Android 开发者提供了简单、快捷的接口，帮助开发者在 Android 平台上快速开发播放器应用。",
                "https://developer.qiniu.com/pili/sdk/1210/the-android-client-sdk",5));
        list.add(new DataBean("6. 自定义圆环进度条",
        "看着别人的敲了一边",
                UrlList.CircleBar,6));
        list.add(new DataBean("7. glide的基本使用以及原理 ",
        "Glide是Google推荐的一套快速高效的图片加载框架，作者是bumptech",
                UrlList.GlideURL,7));
        list.add(new DataBean("8. Picasso使用简介及分析 ",
        "Picasso是Square公司出品的一款非常优秀的开源图片加载库，是目前Android开发中超级流行的图片加载库之一",
                UrlList.PicassoURL,8));
        list.add(new DataBean("9. Android四大图片缓存（Imageloader,Picasso,Glide,Fresco）原理、特性对比",
        "Android四大图片缓存（Imageloader,Picasso,Glide,Fresco）原理、特性对比",
                UrlList.ImageCache,9));
        list.add(new DataBean("10. ijkplayer应用",
        "ijkplayer是一个基于FFmpeg的轻量级Android/iOS视频播放器",
                UrlList.ijkPalyer,10));
        return list;
    }
}
