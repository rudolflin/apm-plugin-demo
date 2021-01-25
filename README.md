# apm-plugin-demo
a skywalking plugin demo 
## 对commons-lang2 的StringUtils replace方法做切面 ,进行增强处理

## 调试 

插件的编写可能不是一步到位的，有时候可能会报点错什么的。如果想要Debug自己的插件，那么需要将你的插件代码和接入Java Agent的项目（也就是你配置了-javaagent启动的项目）扔到同一个工作空间内，可以这么玩：

使用IDEA，打开接入Java Agent的项目
找到File->New->Module from Exisiting Sources…，引入你的插件源码即可。

##  参考资料

skywalking插件开发指南
文章转载自两篇博客：

https://blog.csdn.net/weixin_34262482/article/details/91379642

https://www.imooc.com/article/306199

https://segmentfault.com/a/1190000025172414

http://www.itmuch.com/books/skywalking/guides/Java-Plugin-Development-Guide.html

 