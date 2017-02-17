package com.sucai.compentent.logs.config;

import com.sucai.compentent.logs.annotation.BizLog;
import com.sucai.compentent.logs.aop.LogMethodInterceptor;
import com.sucai.compentent.logs.api.GetLogUser;
import com.sucai.compentent.logs.api.LogConfig;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Author: koabs
 * 2/15/17.
 */

@Configuration
@ConditionalOnExpression("'${sucai.log.enabled}'=='true'")
@ConditionalOnBean({GetLogUser.class})
public class LogAutoConfiguration extends AbstractPointcutAdvisor {
    private Logger logger = LoggerFactory.getLogger(LogAutoConfiguration.class);

    private Pointcut pointcut;

    private Advice advice;

    @Autowired
    GetLogUser user;

    @PostConstruct
    public void init() {
        logger.info("init LogAutoConfiguration start");
        LogConfig.user = user;
        this.pointcut = new AnnotationMatchingPointcut(null, BizLog.class);
        this.advice = new LogMethodInterceptor();
        logger.info("init LogAutoConfiguration end");
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
