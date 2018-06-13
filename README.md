# concise
## 使用说明
用于 一个Activity + 多个View展示。（一定是一个Activity ，如 MainActivity）
好处是页面切换快，移除了Fragment可能带来的问题。
###多View
* 使用自定义栈进行管理进出顺序；
* 继承统一父类BaseView；
* 已为BaseView提供生命周期
* 使用Map集合及软引用管理多View

## BaseActivity
提供View容器及 等待页面，错误页面

## BaseView
提供生命周期，返回键管理，具体View管理，软键盘管理