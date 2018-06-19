package com.gaoyanrong.concise;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.gaoyanrong.concise.base.BaseActivity;
import com.gaoyanrong.concise.manager.UIManager;
import com.gaoyanrong.concise.manager.ViewMapping;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.content_main)
    FrameLayout middleContainer;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        Bundle bundle = new Bundle();
        UIManager.getInstance().setMiddleContainer(middleContainer);
        UIManager.getInstance().changeView(ViewMapping.PROJECT_GUIDE, bundle);

    }


}
