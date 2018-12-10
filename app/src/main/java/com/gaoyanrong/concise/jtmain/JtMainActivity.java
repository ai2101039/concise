package com.gaoyanrong.concise.jtmain;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.gaoyanrong.concise.R;
import com.gaoyanrong.concise.base.BaseActivity;
import com.gaoyanrong.concise.manager.UiManager;
import com.gaoyanrong.concise.manager.ViewMapping;

import butterknife.BindView;

/**
 * @author 延荣
 * 描述：主Activtiy，配合BaseView 使用。
 * 为BaseView 提供 activity 容器，提供context。
 */
public class JtMainActivity extends BaseActivity {

    @BindView(R.id.main)
    FrameLayout main;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        UiManager.getInstance().setMiddleContainer(main);
        UiManager.getInstance().changeView(ViewMapping.GUIDE, new Bundle());
    }


}
