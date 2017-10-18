package com.github.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.app.R;

/**
 * 图片三级缓存工具类(Glide)
 * Created by benny on 17-10-16.
 */

public class ImageCacheUtils {
    private static Context context;
    private static String imageUrl;
    private static ImageView imageView;
    private static int errorId;
    private static int placeholderId;
    private static boolean isCrossFade = false;
    private static boolean isDiskCache = false;
    private static boolean isAsGif = false;
    private static float thumbnail = 1.0f;
    private static boolean isOverride = false;
    private static int with = 0;
    private static int height = 0;

    /**
     * 默认加载
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);

    }

    /**
     * 以下是各种加载情况，如果不够就自行添加 各参数意思自己行下拉
     * @param context
     * @param imageUrl
     * @param imageView
     * @param placeholderId
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId, boolean isCrossFade) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId, boolean isCrossFade, boolean isAsGif) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId, boolean isCrossFade, boolean isAsGif, int with, int height) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView,boolean isOverride,int with, int height) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId, boolean isCrossFade, boolean isAsGif, int with, int height, float thumbnail) {
        loadImage(context, imageUrl, imageView, placeholderId, errorId, isCrossFade, isAsGif,isOverride, with, height, thumbnail, isDiskCache);
    }
    
    /**
     * @param context       上下文
     * @param imageUrl      资源地址
     * @param imageView     UI控件
     * @param placeholderId 加载中占位
     * @param errorId       加载失败
     * @param isCrossFade   加载切换动画
     * @param isAsGif       是否加载gif
     * @param with          设置图片示大小.with
     * @param height        设置图片示大小.height
     * @param thumbnail     是
     * @param isDiskCache   是否使用缓存
     */
    private static void loadImage(Context context, String imageUrl, ImageView imageView, int placeholderId, int errorId, boolean isCrossFade, boolean isAsGif,boolean isOverride,int with, int height, float thumbnail, boolean isDiskCache) {
        if (TextUtils.isEmpty(imageUrl) || imageView == null)
            return;
        imageUrl = imageUrl.contains("http") ? imageUrl : "http:" + imageUrl;
        DrawableTypeRequest<String> typeRequest = Glide.with(context)//传入函数
                .load(imageUrl);//资源
        if (isAsGif) typeRequest.asGif();//加载gif
        if (placeholderId != 0) {
            typeRequest.placeholder(errorId);//占位符
        }
        if (errorId != 0) {
            typeRequest.error(errorId);//加载失败
        }
        if (isCrossFade) typeRequest.crossFade();//加载时切换动画
        if (isOverride&&with != 0 && height != 0) typeRequest.override(with, height);// 调整图片大小
        if (thumbnail != 1.0f) typeRequest.thumbnail(thumbnail);
        if (isDiskCache) typeRequest.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用缓存
        typeRequest.into(imageView);

    }

}
