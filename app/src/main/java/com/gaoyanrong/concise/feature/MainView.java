package com.gaoyanrong.concise.feature;

import android.content.Context;
import android.os.Bundle;

import com.gaoyanrong.concise.R;
import com.gaoyanrong.concise.base.BaseView;

/**
 * @author 高延荣
 * @date 2018/6/19 14:33
 * 描述:
 */
public class MainView extends BaseView {

    public MainView(Context context, String id, Bundle bundle) {
        super(context, id, bundle);
    }

    @Override
    protected int initLayoutID() {
        return R.layout.view_main;
    }

    @Override
    protected void init() {

    }
}
