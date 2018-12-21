package com.gaoyanrong.concise.manager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.gaoyanrong.concise.base.BaseView;
import com.gaoyanrong.concise.utils.Loger;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author 高延荣
 */
public class UiManager {

    /**
     * 单例
     */
    private static UiManager uiManager = new UiManager();

    /**
     * baseView 集合
     * Key = BaseView.class.simpleName
     */
    private static HashMap<StackEle, BaseView> viewMap;
    /**
     * 模拟Activity栈
     * String = BaseView 说明标识
     * 说明标识存在于 ViewMapping 中
     */
    private static Stack<StackEle> stack;

    /**
     * num 在每次 changeView 中新建BaseView时进++ 操作
     */
    private int num;

    static {
        if (MemoryManager.hasAmpleMemory()){
            viewMap = new HashMap<>();
        }else {
            viewMap = new SoftHashMap<>();
        }
        stack = new Stack<>();
    }

    /**
     * 当前正在显示的界面
     */
    private BaseView curView;

    /**
     * JtMainActivity 容器，其内部放入 BaseView，以便于展示BaseView
     */
    private FrameLayout middleContainer;

    /**
     * @return UiManager对象
     */
    public static UiManager getInstance() {
        return uiManager;
    }

    /**
     * @param middleContainer JtMainActivity 的容器
     */
    public void setMiddleContainer(FrameLayout middleContainer) {
        this.middleContainer = middleContainer;
    }

    /**
     * 展示指定 id 的View
     *
     * @param id     View的标识，在ViewMapping内
     * @param bundle 数据包
     */
    public void changeView(String id, Bundle bundle) {
        if (middleContainer == null) {
            throw new NullPointerException("缺少MainActivity的容器");
        }
        // 1、获取到类对象，类跳转模式
        ViewMapping.Item item = ViewMapping.getInstance().getViewMap().get(id);
        Class<? extends BaseView> targetClass = item.getClazz();
        int launchMode = item.getLaunchMode();

        // 2、获取目标View的simpleName，并创建 targetView
        String simpleName = targetClass.getSimpleName();
        BaseView targetView = null;

        // 3、查看simpleName是否已经存在
        // 4、如果存在则根据 launchMode，对 stack栈进行操作
        // 4、如果不存在，则初始化 targetClass,并放入ViewMap
        targetView = checkLaunchMode(targetClass, id, bundle, simpleName, launchMode);

        Loger.e("打印", viewMap.size() + "");
        if (curView == targetView) {
            if (curView != null) {
                curView.onNewIntent();
            }
        } else {
            if (curView != null) {
                middleContainer.removeView(curView.getContainer());
                curView.onPause();
            }
            if (targetView != null) {
                middleContainer.addView(targetView.getContainer());
                curView = targetView;
                curView.onResume();
            }
        }
    }

    /**
     * 根据启动模式操作
     */
    private BaseView checkLaunchMode(Class<? extends BaseView> targetClass, String id, Bundle bundle,
                                     String simpleName, int launchMode) {

        //  1、如果是 STANDARD  则直接创建 stackEle，放入 stack栈 和 viewMap

        //  2、如果是 SINGLE_TASK，则从循环 viewMap的 key 即stackEle，然后找到相同的 simpleName

        //  3、如果是 SINGLE_TOP，则查看 stack 中 栈顶的key，其 simpleName 是否与指定目标相同，相同则 调用 onNewIntent，不同则 调用 STANDARD 的操作

        BaseView targetView = null;
        StackEle ele = null;


        switch (launchMode) {
            case ViewMapping.STANDARD:
                targetView = createBaseView(targetClass, id, bundle, simpleName);
                break;
            case ViewMapping.SINGLE_TASK:
                //  使用栈的迭代器，查找 ele
                Iterator<StackEle> iterator = stack.iterator();

                while (iterator.hasNext()) {
                    StackEle next = iterator.next();
                    if (simpleName.equals(next.simpleName)) {
                        ele = next;
                        break;
                    }
                }
                //  如果 ele 存在，则去 viewMap 查找对应的 baseView
                //  如果 ele 存在，则在 stack 中 将 ele之上的删除，并在 viewMap中也删除
                if (ele != null) {
                    targetView = viewMap.get(ele);
                    //  如果因为软引用的问题，导致baseView为空
                    if (targetView == null) {
                        targetView = createBaseView(targetClass, id, bundle, ele);
                    }

                    //  修改 stack栈，及 viewMap 持有
                    while (iterator.hasNext()) {
                        StackEle next = iterator.next();
                        if (!simpleName.equals(next.simpleName)) {
                            StackEle pop = stack.pop();
                            viewMap.remove(pop);
                        }
                    }

                } else {
                    targetView = createBaseView(targetClass, id, bundle, simpleName);
                }
                break;
            case ViewMapping.SINGLE_TOP:
                //  1、看看是不是正处于栈顶
                ele = stack.peek();
                if (simpleName.equals(ele.simpleName)) {
                    //  1.1 处于栈顶，这时候不用判断是否为null，为肯定不会被回收，curView 还在引用这个对象，所以不会被回收
                    targetView = viewMap.get(ele);
                } else {
                    //  1.2 创建新页面
                    targetView = createBaseView(targetClass, id, bundle, simpleName);
                }
                break;
            default:
        }
        return targetView;
    }


    /**
     * @param targetClass
     * @param id
     * @param bundle
     * @param simpleName
     * @return 创建新的BaseView，放入ViewMap 和 Stack
     */
    private BaseView createBaseView(Class<? extends BaseView> targetClass, String id, Bundle bundle, String simpleName) {
        BaseView targetView = null;
        //  这里注意反射参数
        try {
            Constructor<? extends BaseView> constructor = targetClass.getDeclaredConstructor(Context.class, String.class, Bundle.class);
            targetView = constructor.newInstance(middleContainer.getContext(), id, bundle);
        } catch (Exception e) {
            Log.e("错误", "弹出此消息，请检查你的构造器，看看是哪个类在初始化时候出错，比如你自定义的View。");
        }
        if (targetView != null) {
            // 如果对象创建成功，则创建 栈元素
            num++;
            StackEle ele = new StackEle(simpleName, num);
            stack.push(ele);
            viewMap.put(ele, targetView);

        }
        return targetView;
    }

    /**
     * @param targetClass
     * @param id
     * @param bundle
     * @param stackEle
     * @return 指定 stackEle，一般用作于 ViewMap中 存在 stackEle，但是 其对应的 value 因为软引用失效
     */
    private BaseView createBaseView(Class<? extends BaseView> targetClass, String id, Bundle bundle, StackEle stackEle) {
        BaseView targetView = null;
        try {
            Constructor<? extends BaseView> constructor = targetClass.getConstructor(Context.class, String.class, Bundle.class);
            targetView = constructor.newInstance(middleContainer.getContext(), id, bundle);
        } catch (Exception e) {
            Log.e("错误", "弹出此消息，说明UiManager中的反射出错，一般是传入的class类型和实际的形参类型不同");
        }
        if (targetView != null) {
            viewMap.put(stackEle, targetView);
        }
        return targetView;

    }


    static class StackEle {

        private String simpleName;
        private int id;

        public StackEle(String simpleName, int id) {
            this.simpleName = simpleName;
            this.id = id;
        }

        public String getSimpleName() {
            return simpleName;
        }

        public int getId() {
            return id;
        }
    }
}
