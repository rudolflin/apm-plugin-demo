package com.rudolf.demo;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.StaticMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.StaticMethodsAroundInterceptor;
import org.apache.skywalking.apm.network.trace.component.Component;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;

import java.lang.reflect.Method;

public class StringReplaceInterceptor implements StaticMethodsAroundInterceptor {


    @Override
    public void beforeMethod(Class aClass, Method method, Object[] argumentsTypes, Class<?>[] classes, MethodInterceptResult methodInterceptResult) {
        /*        // 创建span(监控的开始)，本质上是往ThreadLocal对象里面设值
        AbstractSpan span = ContextManager.createLocalSpan("replace");

        *//*
         * 可用ComponentsDefine工具类指定Skywalking官方支持的组件
         * 也可自己new OfficialComponent或者Component
         * 不过在Skywalking的控制台上不会被识别，只会显示N/A
         *//*
        span.setComponent(ComponentsDefine.TOMCAT);

        span.tag(new StringTag(1000, "params"), argumentsTypes[0].toString());
        // 指定该调用的layer，layer是个枚举
        span.setLayer(SpanLayer.CACHE);*/

        AbstractSpan span = ContextManager.createLocalSpan("replace");
        span.setComponent(ComponentsDefine.TOMCAT);
        span.tag(new StringTag(1000,"params"),argumentsTypes[0].toString());
        span.setLayer(SpanLayer.CACHE);
    }

    @Override
    public Object afterMethod(Class aClass, Method method, Object[] objects, Class<?>[] classes, Object o) {
        String retString = (String) o;
        // 激活span，本质上是读取ThreadLocal对象
        AbstractSpan span = ContextManager.activeSpan();
        // 状态码，任意写，Tags也是个Skywalking的工具类，用来比较方便地操作tag
        Tags.STATUS_CODE.set(span, "20000");

        // 停止span(监控的结束)，本质上是清理ThreadLocal对象
        ContextManager.stopSpan();
        return retString;
    }

    @Override
    public void handleMethodException(Class aClass, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        AbstractSpan activeSpan = ContextManager.activeSpan();

        // 记录日志
        activeSpan.log(throwable);
        activeSpan.errorOccurred();
    }
}
