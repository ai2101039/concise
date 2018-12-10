package com.gaoyanrong.concise.feature.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.gaoyanrong.concise.R;
import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.manager.ViewMapping;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 高延荣
 * @date 2018/12/3 17:42
 * 描述:  作者列表页面
 */
public class AuthorListView extends BaseView {
    @BindView(R.id.btn)
    Button btn;

    public AuthorListView(Context mContext, String mId, Bundle mBundle) {
        super(mContext, mId, mBundle);
    }

    @Override
    protected int initLayoutID() {
        return R.layout.view_author_list;
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        changeView(ViewMapping.GUIDE, mBundle);
    }
}
