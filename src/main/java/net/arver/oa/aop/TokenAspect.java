package net.arver.oa.aop;

import cn.hutool.core.util.StrUtil;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TokenAspect.
 * @author leegvv
 */
@Component
@Aspect
public class TokenAspect {

    /**
     * 用户token.
     */
    @Autowired
    private ThreadLocalToken threadLocalToken;

    /**
     * 切点.
     */
    @Pointcut("execution(public * net.arver.oa.controller.*.*(..)))")
    public void aspect() {

    }

    /**
     * 环绕通知.
     * @param point point
     * @return r
     * @throws Throwable 异常
     */
    @Around("aspect()")
    public Object around(final ProceedingJoinPoint point) throws Throwable {
        final R r = (R) point.proceed();
        final String token = threadLocalToken.getToken();
        if (StrUtil.isNotBlank(token)) {
            r.setAdditionalProperties("token", token);
            threadLocalToken.clear();
        }
        return r;
    }
}
