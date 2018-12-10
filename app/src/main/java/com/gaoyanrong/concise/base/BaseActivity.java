package com.gaoyanrong.concise.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoyanrong.concise.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 高延荣
 * @date 2018/11/30 14:18
 * 描述: activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 填充
     */
    protected LayoutInflater inflater;
    /**
     * 资源
     */
    protected Resources resources;

    /**
     * 加载等待
     */
    @BindView(R.id.loading)
    RelativeLayout loading;
    /**
     * 加载等待文本
     */
    @BindView(R.id.loadText)
    TextView loadText;


    /**
     * 错误Stub
     */
    @BindView(R.id.viewStub)
    ViewStub viewStub;

    private View error;


    private int statusBarColor = R.color._2789EC;
    protected ViewGroup baseContainer;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initStatusBar();
        unbinder = ButterKnife.bind(this);
        initContentView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //////////////////////////////////   子类实现     ///////////////////////////////////////////////////

    /**
     * 布局ID layout
     *
     * @return 布局ID
     */
    protected abstract int initLayoutID();

    /**
     * 初始化控件对象，这里不做findViewByID，请使用注解ButterKnife代替。这里建议用于View适配等工作
     */
    protected abstract void initContentView(@Nullable Bundle savedInstanceState);

    //////////////////////////////////////   子类调用    //////////////////////////////////////////////////

    /**
     * 设置 statusBar颜色
     *
     * @param colorRes 颜色
     */
    protected void setStatusBar(@ColorRes int colorRes) {
        statusBarColor = colorRes;
    }

    //////////////////////////////////////    普通逻辑       //////////////////////////////////////////////////

    protected void showBaseLoading(String loadContent) {
        if (loading.getVisibility() != View.VISIBLE) {
            if (!TextUtils.isEmpty(loadContent)) {
                loadText.setText(loadContent);
            }
            loading.setVisibility(View.VISIBLE);
        }
    }

    protected void hideBaseLoading() {
        if (loading.getVisibility() != View.GONE) {
            loadText.setText("正在加载");
            loading.setVisibility(View.GONE);
        }
        hideSoftKeyboard();
    }

    protected void showBaseError() {
        if (error == null) {
            error = viewStub.inflate();
            error.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick(v);
                }
            });
        }
        if (error.getVisibility() != View.VISIBLE) {
            error.setVisibility(View.VISIBLE);
        }
    }

    protected void hideBaseError() {
        if (error != null && error.getVisibility() != View.GONE) {
            error.setVisibility(View.GONE);
        }
    }

    /**
     * 子类重写
     * @param v
     */
    protected void onRetryClick(View v){

    }


    /**
     * 设置StatusBar
     */
    private void initStatusBar() {
        StatusBarUtil.setColor(this, resources.getColor(statusBarColor), 0);
    }

    private void initUI() {
        inflater = getLayoutInflater();
        resources = getResources();
        baseContainer = (ViewGroup) inflater.inflate(R.layout.activity_base, null);
        baseContainer.addView(inflater.inflate(initLayoutID(), null), 0);
        setContentView(baseContainer);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(baseContainer.getWindowToken(), 0);
        }
    }

}
