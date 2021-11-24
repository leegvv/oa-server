package net.arver.oa.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * OAuth2Realm.
 * @author leegvv
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Override
    public boolean supports(final AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // todo 查询用户的权限列表
        // todo 把权限列表添加到info对象中
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
            throws AuthenticationException {
        // todo 从令牌中获取userId, 然后检测该账户是否被冻结
        final SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        // todo 往info对象中添加用户信息、token字符串
        return info;
    }
}
