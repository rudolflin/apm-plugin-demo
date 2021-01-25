package com.rudolf.demo;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.StaticMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.NameMatch;

public class StringReplaceInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    public static final String enhanceClassName="org.apache.commons.lang3.StringUtils";

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(enhanceClassName);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[0];
    }


    @Override
    public StaticMethodsInterceptPoint[] getStaticMethodsInterceptPoints() {
        // 指定想要监控的静态方法，每一个方法对应一个StaticMethodsInterceptPoint
        return new StaticMethodsInterceptPoint[]{
                new StaticMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        // 静态方法名称
                        return ElementMatchers.named("replace");
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        // 该静态方法的监控拦截器类名全路径
                        return "com.rudolf.demo.StringReplaceInterceptor";
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }


}
