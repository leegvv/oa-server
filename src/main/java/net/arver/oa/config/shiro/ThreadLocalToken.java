package net.arver.oa.config.shiro;

import org.springframework.stereotype.Component;

/**
 * ThreadLocalToken.
 * @author leegvv
 */
@Component
public class ThreadLocalToken {

    /**
     * 线程独有token.
     */
    private ThreadLocal<String> local = new ThreadLocal<>();

    /**
     * 设置token.
     * @param token token
     */
    public void setToken(final String token) {
        local.set(token);
    }

    /**
     * 获取token.
     * @return token
     */
    public String getToken() {
        return local.get();
    }

    /**
     * 移除token.
     */
    public void clear() {
        local.remove();
    }
}
