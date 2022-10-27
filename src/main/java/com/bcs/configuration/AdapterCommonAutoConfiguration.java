package com.bcs.configuration;

import com.bcs.bean.ApplicationContextHolder;
import com.bcs.cache.GuavaCache;
import com.bcs.executor.AbstractAdapterExecutor;
import com.bcs.interceptor.AsynMethodInterceptor;
import com.bcs.properties.AsynProperties;
import com.bcs.asynconst.AsynConst;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(AsynProperties.class)
@ConditionalOnProperty(name = AsynConst.ASYN_ENABLED_PROP, matchIfMissing = true, havingValue = "true")
public class AdapterCommonAutoConfiguration {
    @Resource
    private AsynMethodInterceptor asynMethodInterceptor;

    @Resource
    private AsynProperties asynProperties;

    @Bean
    public DefaultPointcutAdvisor myAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(String.format("@annotation(%s)", asynProperties.getPointCut()));
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        AbstractAdapterExecutor.setLocleCache(new GuavaCache());
        advisor.setPointcut(pointcut);
        advisor.setAdvice(asynMethodInterceptor);
        return advisor;
    }

    @Bean
    public AsynMethodInterceptor asynMethodInterceptor() {
        AsynMethodInterceptor asynMethodInterceptor = new AsynMethodInterceptor();
        return asynMethodInterceptor;
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        ApplicationContextHolder holder = new ApplicationContextHolder();
        return holder;
    }
}
