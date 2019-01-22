package com.gaoyanrong.concise.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import com.gaoyanrong.concise.manager.UiManager;
import com.lzy.okgo.OkGo;

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
    protected BaseActivity activity;

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
        activity = (BaseActivity) mContext;
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

    /**
     * 回退
     */
    public void rollback() {
        UiManager.getInstance().rollback();
    }


    /**
     * 等待/错误页面
     */
    public void showLoading() {
        activity.showBaseLoading(null);
    }

    public void showLoading(String loading) {
        activity.showBaseLoading(loading);
    }

    public void hideLoading() {
        activity.hideBaseLoading();
    }

    public void showError() {
        activity.showBaseError();
    }

    public void hideError() {
        activity.hideBaseError();
    }

    public void showSoftKeyboard(EditText editText){
        activity.showSoftKeyboard(editText);
    }

    public void hideSoftKeyboard(){
        activity.hideSoftKeyboard();
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
        unbinder = ButterKnife.bind(this, container);
    }

    @CallSuper
    public void onResume() {
    }

    @CallSuper
    public void onPause() {
        hideLoading();
    }

    @CallSuper
    public void onDestroy() {
        unbinder.unbind();
        OkGo.getInstance().cancelAll();
    }

    @CallSuper
    public void onNewIntent(Bundle bundle) {

    }

    /////////////////////////// 子类重写 //////////////////////////////////////


    /**
     * @param keyCode key值
     * @param event   事件
     * @return 是否接收按下动作，默认不接收
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * @return 是否保留，举例
     * 我们这个小架构里面，如果当前栈结构为  ABCD，A为栈内启动模式，当跳转到A时，BCD被移出栈，并且被移出 ViewMap
     * 当我们从A 再跳转到B时候，B会重新被创建。
     * 而有时候，我们其实是希望保留这个BaseView不被移出 ViewMap，所以我们增加一个保留函数，用于ViewMap判断
     */
    public boolean reserve() {
        return false;
    }

    /////////////////////////// get //////////////////////////////////////

    /**
     * @return
     */
    public Bundle getmBundle() {
        return mBundle;
    }
}
