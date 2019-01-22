# concise

## 说明
用于 一个Activity + 多个View展示。（一定是一个Activity ，如 MainActivity）
多View 模拟 Activity栈，实现了 STANDARD SINGLE_TASK SINGLE_TOP 三种启动模式

## BaseActivity
提供BaseView容器、 等待页面 Loading ，错误页面 Error

## BaseView
* 模拟Activity，实现各生命周期  onCreate onResume onPause onDestory onNewIntent
* 返回键管理，软键盘管理

## 其他
* 自定义软引用HashMap，实现了 软引用 View对象，防止View溢出
* 自定义Stack栈，根据启动模式存储启动顺序
* 自定义ViewMapping，实现对BaseView class对象的管理

