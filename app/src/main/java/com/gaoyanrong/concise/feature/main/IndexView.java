package com.gaoyanrong.concise.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;


import com.gaoyanrong.concise.R;
import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.manager.ViewClassMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 高延荣
 * @date 2018/12/3 14:00
 * 描述:
 */
public class IndexView extends BaseView {

    @BindView(R.id.btn)
    Button btn;

    public IndexView(Context mContext, String mId, Bundle mBundle) {
        super(mContext, mId, mBundle);
    }

    @Override
    protected int initLayoutID() {
        return R.layout.view_index;
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        changeView(ViewClassMap.AUTHOR_LIST,mBundle);
    }
}
