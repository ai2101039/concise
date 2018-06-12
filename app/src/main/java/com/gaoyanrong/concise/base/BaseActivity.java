package com.gaoyanrong.concise.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gaoyanrong.concise.R;
import com.jaeger.library.StatusBarUtil;
import com.joker.api.Permissions4M;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 高延荣
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
     * 容器
     */
    protected ViewGroup baseContainer;


    /**
     * 等待界面
     */
    @BindView(R.id.base_load)
    ViewStub viewStubLoad;
    private View baseLoad;
    /**
     * 等待界面 --> TextView
     */
    private TextView loadText;

    /**
     * 错误界面
     */
    @BindView(R.id.base_error)
    ViewStub viewStubError;
    private View baseError;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initStatusBar();
        ButterKnife.bind(this);
        initContentView();
    }


    /**
     * 设置StatusBar
     */
    private void initStatusBar() {
        int statusBarColor = initStatusBarColor();
        if (statusBarColor != 0)
            StatusBarUtil.setColor(this, resources.getColor(statusBarColor), 0);
    }

    /**
     * 初始化容器界面
     */
    private void initView() {
        inflater = getLayoutInflater();
        resources = getResources();
        baseContainer = (ViewGroup) inflater.inflate(R.layout.activity_base, null);
        baseContainer.addView(inflater.inflate(initLayoutID(), null), 0);
        setContentView(baseContainer);
    }
    //////////////////////////     子类重写     /////////////////////////////

    /**
     * 状态栏颜色，如果为 0 ，则表示不设置颜色
     *
     * @return int
     */
    protected int initStatusBarColor() {
        return R.color._2789EC;
    }


    /**
     * 请求网络失败时候，再次请求动作
     *
     * @param v View
     */
    protected void onRetryClick(View v) {

    }

    //////////////////////////     子类实现     /////////////////////////////

    /**
     * 子类界面布局ID
     *
     * @return ID
     */
    protected abstract int initLayoutID();

    /**
     * 初始化控件对象，这里不做findViewByID，请使用注解ButterKnife代替。这里建议用于View适配等工作
     */
    protected abstract void initContentView();

    //////////////////////////     子类调用     /////////////////////////////

    /**
     * 显示等待界面
     *
     * @param loadContent String
     */
    protected void showBaseLoading(String loadContent) {
        if (baseLoad == null) {
            baseLoad = viewStubLoad.inflate();
            loadText = baseLoad.findViewById(R.id.loadText);
        }

        if (baseLoad.getVisibility() != View.VISIBLE) {
            if (!TextUtils.isEmpty(loadContent)) {
                loadText.setText(loadContent);
            }
            baseLoad.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏等待界面
     */
    protected void hideBaseLoading() {
        if (baseLoad != null && baseLoad.getVisibility() != View.GONE) {
            loadText.setText("正在加载");
            baseLoad.setVisibility(View.GONE);
        }
        hideSoftKeyboard();
    }

    /**
     * 显示错误页面，设置刷新监听
     */
    protected void showBaseError() {
        if (baseError == null) {
            baseError = viewStubError.inflate();
            baseError.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick(v);
                }
            });
        }
    }

    /**
     * 隐藏错误页面
     */
    protected void hideBaseError() {
        if (baseError != null && baseError.getVisibility() != View.GONE) {
            baseError.setVisibility(View.GONE);
        }
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

    /**
     * 权限申请
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
