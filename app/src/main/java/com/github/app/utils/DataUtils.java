package com.github.app.utils;

import java.util.ArrayList;
import java.util.List;

import static com.github.app.utils.UrlList.CircleBar;

/**
 * Created by benny
 * on 2017/10/13.
 */

public class DataUtils {
    public static List<DataBean> getDataList() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("1. Spruce Android Animation Library (and iOS)",
        "Spruce 是一个轻量级的动画库，可以帮助排版屏幕上的动画。使用有很多不同的动画库时，开发人员和程序员需要确保每个视图都能够在适当的时间活动。 Spruce 可以帮助设计师获得复杂的多视图动画，而不是让开发人员在原型阶段就感到畏惧。"
        ,"https://github.com/willowtreeapps/spruce-android"));
        list.add(new DataBean("2. PatternLockView",
        "PatternLockView 这个库可以在应用中简单快速的实现图形锁机制。它有大 量的个性化选项可以用于改变功能和外观，以此满足你的需求，非常的实 用。重点是它还支持响应式的 RxJava 2 视图绑定。",
                "https://github.com/aritraroy/PatternLockView"));
        list.add(new DataBean("3. ShadowImageView",
        "PatternLockView 这个库可以在应用中简单快速的实现图形锁机制。它有大 量的个性化选项可以用于改变功能和外观，以此满足你的需求，非常的实 用。重点是它还支持响应式的 RxJava 2 视图绑定。",
                "https://github.com/yingLanNull/ShadowImageView"));
        list.add(new DataBean("4. RxJava的运用",
        "清晰 & 易懂的 Rxjava 入门教程",
                "http://www.jianshu.com/p/0cd258eecf60"));
        list.add(new DataBean("5. 视频播放器",
        "PLDroidPlayer 是一个适用于 Android 平台的音视频播放器 SDK，可高度定制化和二次开发，为 Android 开发者提供了简单、快捷的接口，帮助开发者在 Android 平台上快速开发播放器应用。",
                "https://developer.qiniu.com/pili/sdk/1210/the-android-client-sdk"));
        list.add(new DataBean("6. 自定义圆环进度条",
        "看着别人的敲了一边",
                UrlList.CircleBar));
        list.add(new DataBean("7. glide的基本使用以及原理 ",
        "Glide是Google推荐的一套快速高效的图片加载框架，作者是bumptech",
                UrlList.GlideURL));
        list.add(new DataBean("8. Android四大图片缓存（Imageloader,Picasso,Glide,Fresco）原理、特性对比",
        "Android四大图片缓存（Imageloader,Picasso,Glide,Fresco）原理、特性对比",
                UrlList.ImageCache));

        return list;
    }
}
