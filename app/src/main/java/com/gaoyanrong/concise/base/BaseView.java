package com.gaoyanrong.concise.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author 高延荣
 * @date 2018/6/13 11:25
 * 描述: BaseView是一个View容器类，对外提供View布局，及生命周期
 */
public abstract class BaseView {
    /**
     * 上下文，在必要时，自行强转
     */
    protected Context mContext;
    /**
     * 当前View ID
     */
    protected String mId;
    /**
     * 重要，重要，重要！！！
     * 非常重要！！！
     * 用于数据的传递、获取，可根据数据变化进行不同操作
     */
    protected Bundle mBundle;
    /**
     * 资源
     */
    protected Resources mResources;
    /**
     * 填充
     */
    protected LayoutInflater mInflater;
    /**
     * 容器
     */
    protected View container;

    public BaseView(Context context, String id, Bundle bundle) {
        mContext = context;
        mId = id;
        mBundle = bundle;
        mResources = context.getResources();
        mInflater = LayoutInflater.from(context);

        initContainer();
        init();
    }


    /**
     * 初始化容器
     */
    private void initContainer() {
        container = mInflater.inflate(initLayoutID(), null);
        ButterKnife.bind(this, container);
    }

    /////////////////////////// 子类实现 //////////////////////////////////////

    /**
     * 布局ID
     * 在这里直接可以使用 ButterKnife获取对象
     *
     * @return layout
     */
    protected abstract int initLayoutID();

    /**
     * 类似于 Activity onCreate() ，做一些初始化动作。
     */
    protected abstract void init();


    ////////////////////////////// 子类重写 生命周期 //////////////////////////////////////

    /**
     * 类似于 Activity onResume
     */
    @CallSuper
    public void onResume() {
    }

    /**
     * 类似于  Activity onPause
     */
    @CallSuper
    public void onPause() {

    }

    /**
     * 类似于 Activity 的 onNewIntent，当前View如果已存在，则
     *
     * @param bundle 传递数据
     */
    @CallSuper
    public void onNewIntent(Bundle bundle) {

    }

    /**
     * 是否接收回退动作，默认不接收
     *
     * @param keyCode int
     * @param event   事件
     * @return 是否拦截回退
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    ////////////////////////////// 子类调用 //////////////////////////////////////



    ////////////////////////////// get set   //////////////////////////////////////


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Bundle getmBundle() {
        return mBundle;
    }

    public void setmBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }

    public View getContainer() {
        return container;
    }

    public void setContainer(View container) {
        this.container = container;
    }
}
