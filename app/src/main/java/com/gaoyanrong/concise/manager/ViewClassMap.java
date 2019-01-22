package com.gaoyanrong.concise.manager;


import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.feature.main.AuthorListView;
import com.gaoyanrong.concise.feature.main.IndexView;

import java.util.HashMap;

/**
 * @author 高延荣
 * @date 2018/12/3 11:54
 * 描述: BaseView 的清单文件
 */
public class ViewClassMap {
    private static ViewClassMap viewMapping = new ViewClassMap();

    /**
     * 启动模式
     */
    public static final int STANDARD = 0;
    public static final int SINGLE_TASK = 1;
    public static final int SINGLE_TOP = 2;

    private HashMap<String, Item> viewMap = new HashMap<>();

    ///////////////////////////////  BaseView 的 标识字符串 Mark  /////////////////////////////


    public static final String GUIDE = "项目主界面";
    public static final String AUTHOR_LIST = "作者列表页面";

    private ViewClassMap() {
        put(GUIDE, IndexView.class);
        put(AUTHOR_LIST, AuthorListView.class);
    }



    ////////////////////////////////////不需要操作//////////////////////////////////

    private void put(String s, Class<? extends BaseView> clazz) {
        put(s, clazz, SINGLE_TASK);
    }

    private void put(String s, Class<? extends BaseView> clazz, int launchMode) {
        viewMap.put(s, new Item(launchMode, clazz));
    }

    /**
     * @return viewMap
     */
    public HashMap<String, Item> getViewMap() {
        return viewMap;
    }

    /**
     * @return viewMapping
     */
    public static ViewClassMap getInstance() {
        return viewMapping;
    }

    /**
     * 包含 class 类及启动模式，默认为 栈内模式
     */
    static class Item {
        private int launchMode;
        private Class<? extends BaseView> clazz;

        public Item(Class<? extends BaseView> clazz) {
            launchMode = SINGLE_TASK;
            this.clazz = clazz;
        }

        public Item(int launchMode, Class<? extends BaseView> clazz) {
            this.launchMode = launchMode;
            this.clazz = clazz;
        }

        public int getLaunchMode() {
            return launchMode;
        }

        public Class<? extends BaseView> getClazz() {
            return clazz;
        }
    }

}
