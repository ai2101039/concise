package com.gaoyanrong.concise.manager;

import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.feature.MainView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 高延荣
 * @date 2018/4/25 17:33
 * 描述:  记录View 查找信息
 */

public class ViewMapping {


    private static Map<String, Class<? extends BaseView>> viewMap = new HashMap<>();

    /**
     * 单例一个ViewMapping
     */
    private static ViewMapping viewMapping = new ViewMapping();

    ///////////////////////////////  项目相关  /////////////////////////////


    public static final String PROJECT_GUIDE = "项目主界面";
    public static final String PROJECT_QR_CODE = "项目二维码页面";
    public static final String PROJECT_WORK_DIARY = "施工日志";
    public static final String PROJECT_IMG_TEXT_COMMIT = "图片文字上传";
    public static final String PROJECT_SAFE = "安全问题";
    public static final String PROJECT_QUALITY = "质量问题";

    ///////////////////////////////  用户相关  /////////////////////////////


    /**
     * 初始化时，将BaseView清单构造完成
     */
    private ViewMapping() {
        viewMap.put(PROJECT_GUIDE, MainView.class);
//        viewMap.put(PROJECT_QR_CODE, ProjectQRCodeView.class);
//        viewMap.put(PROJECT_WORK_DIARY, ProjectWorkDiaryView.class);
//        viewMap.put(PROJECT_IMG_TEXT_COMMIT, ProjectImageTextCommit.class);
//        viewMap.put(PROJECT_SAFE, ProjectSafe.class);
//        viewMap.put(PROJECT_QUALITY, ProjectQuality.class);

    }

    public static ViewMapping getInstance() {
        return viewMapping;
    }

    public Map<String, Class<? extends BaseView>> getViewMap() {
        return viewMap;
    }


}
