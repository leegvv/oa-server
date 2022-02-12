package net.arver.oa.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * OAuth2Filter.
 * @author leegvv
 */
@Component
@Scope("prototype")
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {

    /**
     * 缓存过期时间.
     */
    @Value("${oa.jwt.cache-expire}")
    private int cacheExpire;

    /**
     * token.
     */
    @Autowired
    private ThreadLocalToken threadLocalToken;

    /**
     * jwt工具类.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * redisTemplate.
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected AuthenticationToken createToken(final ServletRequest request, final ServletResponse response)
            throws Exception {
        // 获取请求token
        final String token = getRequestToken((HttpServletRequest) request);

        if (StrUtil.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(final ServletRequest request, final ServletResponse response,
                                      final Object mappedValue) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        //Ajax提交application/json数据时，会先发Option请求，这里放行Option请求，不需要Shiro处理
        if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
            return true;
        }
        //除Option请求之外，所有请求都需要Shiro处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws Exception {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        threadLocalToken.clear();

        String token = getRequestToken(req);
        if (StrUtil.isBlank(token)) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.getWriter().print("无效的令牌");
            return false;
        }

        try {
            jwtUtil.verifierToken(token);
        } catch (final TokenExpiredException e) {
            if (redisTemplate.hasKey(token)) {
                redisTemplate.delete(token);
                final int userId = jwtUtil.getUserId(token);
                token = jwtUtil.createToken(userId);
                redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
                threadLocalToken.setToken(token);
            } else {
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                resp.getWriter().print("令牌已过期");
                return false;
            }
        } catch (final Exception e) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.getWriter().print("无效的令牌");
            return false;
        }
        final boolean bool = executeLogin(request, response);
        return bool;
    }

    @Override
    protected boolean onLoginFailure(final AuthenticationToken token, final AuthenticationException e,
                                     final ServletRequest request, final ServletResponse response) {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            resp.getWriter().print(e.getMessage());
        } catch (final Exception ex) {
            log.error(ex.getMessage());
        }

        return false;
    }

    /**
     * 获取token.
     * @param request req
     * @return token
     */
    private String getRequestToken(final HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
