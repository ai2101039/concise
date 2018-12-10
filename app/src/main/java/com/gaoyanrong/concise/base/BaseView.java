package com.gaoyanrong.concise.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;


import com.gaoyanrong.concise.manager.UiManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 高延荣
 */
public abstract class BaseView {
    protected Context mContext;
    protected String mId;
    protected Bundle mBundle;
    protected Resources mResources;
    protected LayoutInflater mInflater;

    /**
     * 实现类 布局 View
     */
    protected View container;
    /**
     * ButterKnife 绑定对象
     */
    private Unbinder unbinder;

    public BaseView(Context mContext, String mId, Bundle mBundle) {
        this.mContext = mContext;
        this.mId = mId;
        this.mBundle = mBundle;
        this.mResources = mContext.getResources();
        mInflater = LayoutInflater.from(mContext);

        onCreate();
    }


    /////////////////////////// 通用调用 //////////////////////////////////////

    /**
     * @param id
     * @param <T>
     * @return findView
     */
    protected <T extends View> T findViewById(@IdRes int id) {
        return container.findViewById(id);
    }

    /**
     * @return 布局View对象
     */
    public View getContainer() {
        return container;
    }

    /**
     * @param jumpId 要跳转到的ViewID，在ViewMapping内
     * @param bundle 数据
     */
    public void changeView(String jumpId, Bundle bundle) {
        UiManager.getInstance().changeView(jumpId, bundle);
    }

    /////////////////////////// 子类实现 //////////////////////////////////////

    /**
     * 传入继承 BaseView 的类 所想要用的布局ID
     *
     * @return 布局ID
     */
    protected abstract int initLayoutID();


    /////////////////////////// 生命周期 //////////////////////////////////////

    @CallSuper
    public void onCreate() {
        container = mInflater.inflate(initLayoutID(), null);
        unbinder = ButterKnife.bind(this,container);
    }

    @CallSuper
    public void onResume() {

    }

    @CallSuper
    public void onPause() {

    }

    @CallSuper
    public void onDestory() {
        unbinder.unbind();
    }

    @CallSuper
    public void onNewIntent() {

    }

    /**
     * @param keyCode key值
     * @param event   事件
     * @return 是否接收按下动作，默认不接收
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
