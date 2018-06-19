package com.gaoyanrong.concise.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.utils.Logger;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

/**
 * @author 高延荣
 * @date 2018/4/25 16:07
 * 描述:  UI管理器，内部维持了一个 ViewMapping，ViewMapping 内包含的是BaseView的名称及Class
 */

public class UIManager {
    /**
     * 单例
     */
    @SuppressLint("StaticFieldLeak")
    private static UIManager instance = new UIManager();
    /**
     *  容器
     */
    private FrameLayout middleContainer;
    /**
     * 当前显示的界面
     */
    private BaseView currentView;

    /**
     * key为BaseView子类的简单名称
     */
    private static Map<String, BaseView> viewMap;

    static {
        // 判断当前系统内存是否满足应用的需求
        if (MemoryManager.hasAcailMemory()) {
            // 如果满足
            viewMap = new HashMap();
        } else {
            // 如果不满足
            viewMap = new SoftReferenceMap();
        }
    }

    private final Stack<String> stack;


    private UIManager() {
        stack = new Stack<>();
    }

    public static UIManager getInstance() {
        return instance;
    }

    public void setMiddleContainer(FrameLayout middleContainer) {
        this.middleContainer = middleContainer;
    }

    public boolean changeView(String jumpId, Bundle bundle) {
        if (middleContainer == null) {
            return false;
        }
        Class<? extends BaseView> targetViewClass = ViewMapping.getInstance().getViewMap().get(jumpId);
        String simpleName = targetViewClass.getSimpleName();

        //  目标View
        BaseView targetView = null;
        // 判断当前是否含有目标View的实例
        if (viewMap.containsKey(simpleName)) {
            targetView = viewMap.get(simpleName);
            //如果判读到当前的bundle不为空设置一下targetView的bundle信息
            if (bundle != null) {
                targetView.setmBundle(bundle);
            }
        }
        if (targetView == null) {
            // 如果没有目标View的实例，创建一个目标View（BaseView的实例）
            try {
                Constructor<? extends BaseView> constructor = targetViewClass.getConstructor(Context.class, String.class, Bundle.class);
                targetView = constructor.newInstance(middleContainer.getContext(), jumpId, bundle);
                // 将实例添加到容器当中
                viewMap.put(simpleName, targetView);
            } catch (Exception e) {
                // 反射也拿不到对象
            }
        }
        if (currentView != null) {
            //  执行生命周期
            currentView.onPause();
        }

        //TODO 处理View栈，栈的存在只是为了 回退按键
        //  先判断 stack里面能不能search 到，如果不能，直接push 进去
        if (stack.search(jumpId) == -1) {
            stack.push(jumpId);
        } else {
            while (true) {
                if (jumpId.equals(stack.peek())) {
                    break;
                } else {
                    stack.pop();
                }
            }
        }

        //  与下面 try catch 配合，遇到过 remove 还未完成，这时候ADD 的崩溃BUG，增加 try降低崩
        //  同时移除 removeAllView
        if (currentView != null) {
            middleContainer.removeView(currentView.getContainer());
        }

        if (targetView != null) {
            View view = targetView.getContainer();
            // 将当前显示的界面付给currentView对象
            currentView = targetView;
            //  执行生命周期
            targetView.onResume();
            try {
                middleContainer.addView(view);
            } catch (Exception e) {

            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 根据栈的内容进行切换View，主要用于用户按 回退健时候用
     * 如果栈里面只有一个元素，那也只能是 ProjectGuide 了，用户这时候按回退健就执行 super 的回退动作，不然就执行 跳转的动作
     */
    public boolean changeViewByStack() {
        if (stack.size() == 1) {
            return true;
        } else {
            //TODO 跳转
            stack.pop();
            String id = stack.peek();
            Bundle bundle = currentView.getmBundle();

            Class<? extends BaseView> targetViewClass = ViewMapping.getInstance().getViewMap().get(id);
            String simpleName = targetViewClass.getSimpleName();

            // 放置一个容器，存储已经生成过的BaseView子类对象
            BaseView targetView = null;
            // 判断当前是否含有目标Class的实例
            if (viewMap.containsKey(simpleName)) {
                targetView = viewMap.get(simpleName);
                // 把当前界面的bundle 传递给目标View
                if (bundle != null) {
                    targetView.setmBundle(bundle);
                }
            }
            //  TODO 正常来说，进入栈的View都已经被保存在 viewMap里面了，但是有可能会发生 ViewMap 使用软引用，导致 targetView为空。
            if (targetView == null) {
                try {
                    // 如果没有目标Class的实例,创建一个目标Class（BaseView的实例）
                    Constructor<? extends BaseView> constructor = targetViewClass.getConstructor(Context.class, String.class, Bundle.class);
                    targetView = constructor.newInstance(middleContainer.getContext(), simpleName, bundle);
                    // 将实例添加到我的容器当中
                    viewMap.put(simpleName, targetView);
                } catch (Exception e) {
                    // 反射也拿不到对象
                }
            }
            if (currentView != null) {
                currentView.onPause();
            }
            middleContainer.removeAllViews();
            if (targetView != null) {
                View view = targetView.getContainer();
                // 将当前显示的界面付给currentView对象
                currentView = targetView;
                targetView.onResume();
                middleContainer.addView(view);
            }
            return false;
        }
    }

    /**
     * 检查栈是否正常
     * 只用于测试，线上时需取消
     */
    public void checkStack() {
        String tag = "checkStack";
        int size = stack.size();
        Logger.i(tag, "stack 共有 " + size);


        ListIterator<String> stringListIterator = stack.listIterator();
        while (stringListIterator.hasNext()) {
            String next = stringListIterator.next();
            Logger.i(tag, next);
        }

    }


    public BaseView getCurrentView() {
        return currentView;
    }

    /**
     * 清空栈和viewMap
     */
    public void clear(){
        stack.clear();
        viewMap.clear();
    }

}
