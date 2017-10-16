package com.github.app.ui.demo;

import com.github.app.R;
import com.github.app.ui.BaseActivity;
import com.github.app.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by benny
 * on 2017/10/14.
 */

public class RxJavaActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.rxjava_layout;
    }

    @Override
    public void initView() {
        super.initView();

        funOne();
        funTwo();
    }

    private void funOne() {

        // 步骤1：创建被观察者 Observable & 生产事件// 即 顾客入饭店 - 坐下餐桌 - 点菜

        //  1. 创建被观察者 Observable 对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生事件并通知观察者
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        // 步骤2：创建观察者 Observer 并 定义响应事件行为// 即 开厨房 - 确定对应菜式

        Observer<Integer> observer = new Observer<Integer>() {
            // 通过复写对应方法来 响应 被观察者
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d("开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                LogUtils.d("对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtils.d("对Complete事件作出响应");
            }
        };


        // 步骤3：通过订阅（subscribe）连接观察者和被观察者
        // 即 顾客找到服务员 - 点菜 - 服务员下单到厨房 - 厨房烹调
        observable.subscribe(observer);
    }
    private  void funTwo(){
        // RxJava的流式操作
        Observable.create(new ObservableOnSubscribe<String>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("“来客人了”");
                emitter.onNext("“客人找服务员点餐”");
                emitter.onNext("“服务员去厨房下单”");
                emitter.onNext("“厨房开工”");
                emitter.onNext("“服务员给客人送餐”");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            // 2. 通过通过订阅（subscribe）连接观察者和被观察者
            // 3. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d("开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(String value) {
                LogUtils.d("对Next事件"+ value +"作出响应"  );
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                LogUtils.d("对Complete事件作出响应");
            }

        });
    }
}
